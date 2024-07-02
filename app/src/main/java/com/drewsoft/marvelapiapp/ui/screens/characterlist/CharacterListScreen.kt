package com.drewsoft.marvelapiapp.ui.screens.characterlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drewsoft.marvelapiapp.R
import com.drewsoft.marvelapiapp.domain.model.characterlist.CharacterListItem
import com.drewsoft.marvelapiapp.domain.model.ImageFormat
import com.drewsoft.marvelapiapp.domain.model.characterlist.OrderBy
import com.drewsoft.marvelapiapp.ui.screens.characterlist.state.CharacterListUiState
import com.drewsoft.marvelapiapp.ui.navigation.Routes

@Composable
fun CharactersListScreen(
    viewModel: CharacterListViewModel, navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CharacterListTopBar(viewModel)
    }) { paddingValues ->
        when (uiState) {
            is CharacterListUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        tint = Color.Red,
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error Icon"
                    )
                }
            }
            CharacterListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                            .padding(paddingValues)
                    )
                }
            }
            is CharacterListUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    CharactersList(
                        (uiState as CharacterListUiState.Success).result.characters,
                        viewModel,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun CharactersList(
    characters: List<CharacterListItem>, viewModel: CharacterListViewModel, navController: NavController
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ) {
        items(characters, key = { it.id }) {
            CharacterCard(it, navController)
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItemsCount - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            viewModel.loadMoreCharacters()
        }
    }
}

@Composable
fun CharacterCard(
    character: CharacterListItem, navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(
                    Routes.CharacterDetailRoute.createRoute(
                        character.id,
                        character.name
                    )
                )
            }, shape = RoundedCornerShape(8.dp), elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(128.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.getImageUrl(ImageFormat.SQUARE(ImageFormat.ImageSize.LARGE)))
                    .build(),
                placeholder = painterResource(id = R.drawable.logo_marvel),
                error = painterResource(id = R.drawable.logo_marvel),
                contentScale = ContentScale.FillHeight,
                contentDescription = "${character.name} image"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = character.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = character.description,
                    fontSize = 12.sp,
                    minLines = 4,
                    maxLines = 4,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListTopBar(viewModel: CharacterListViewModel) {
    var isSearchDisplayed by rememberSaveable {
        mutableStateOf(false)
    }
    var isOrderByMenuDisplayed by rememberSaveable {
        mutableStateOf(false)
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val context = LocalContext.current

    val searchTerm by produceState(
        initialValue = "", key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.filterCriteria.collect { value = it.name ?: "" }
        }
    }

    val orderBy by produceState<OrderBy?>(
        initialValue = null, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.filterCriteria.collect { value = it.order }
        }
    }

    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        if (!isSearchDisplayed) {
            Text(text = context.getString(R.string.app_name))
        }
    }, actions = {
        IconButton(onClick = { isSearchDisplayed = !isSearchDisplayed }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        }
        if (isSearchDisplayed) {
            TextField(value = searchTerm, onValueChange = {
                viewModel.setFilter(it, orderBy)
            }, maxLines = 1, singleLine = true, trailingIcon = {
                if (searchTerm.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setFilter("", orderBy) }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear text icon"
                        )
                    }
                }
            })
        }
        IconButton(onClick = { isOrderByMenuDisplayed = !isOrderByMenuDisplayed }) {
            Icon(
                imageVector = Icons.Filled.MoreVert, contentDescription = "Menu Icon"
            )
        }
        TopBarDropdownMenu(
            isExpanded = isOrderByMenuDisplayed,
            selectedOption = orderBy,
            onDismissRequest = {
                isOrderByMenuDisplayed = false
            }) {
            viewModel.setFilter(searchTerm, it)
        }
    })
}

@Composable
fun TopBarDropdownMenu(
    isExpanded: Boolean,
    selectedOption: OrderBy?,
    onDismissRequest: () -> Unit,
    doOnOptionSelected: (OrderBy?) -> Unit,
) {
    val context = LocalContext.current
    DropdownMenu(expanded = isExpanded, onDismissRequest = onDismissRequest) {
        OrderBy.entries.forEach { orderBy ->
            DropdownMenuItem(text = {
                Text(
                    text = context.getString(orderBy.textToShow),
                    fontWeight = if (selectedOption == orderBy) FontWeight.ExtraBold else FontWeight.Normal
                )
            }, onClick = {
                onDismissRequest()
                if (orderBy == selectedOption){
                    doOnOptionSelected(null)
                }else{
                    doOnOptionSelected(orderBy)
                }
            })
        }
    }
}