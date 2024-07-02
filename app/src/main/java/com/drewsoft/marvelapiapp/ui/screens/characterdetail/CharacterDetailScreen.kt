package com.drewsoft.marvelapiapp.ui.screens.characterdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.twotone.Book
import androidx.compose.material.icons.twotone.ChatBubble
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Link
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drewsoft.marvelapiapp.R
import com.drewsoft.marvelapiapp.domain.model.ImageFormat
import com.drewsoft.marvelapiapp.domain.model.Link
import com.drewsoft.marvelapiapp.ui.navigation.Routes
import com.drewsoft.marvelapiapp.domain.model.characterdetail.CharacterDetail
import com.drewsoft.marvelapiapp.ui.screens.characterdetail.state.CharacterDetailUiState

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    navController: NavController,
    characterId: Int,
    characterName: String
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = characterId) {
        viewModel.getCharacterDetail(characterId)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CharacterDetailTopBar(navController, characterName)
    }) { paddingValues ->
        when (uiState) {
            is CharacterDetailUiState.Error -> {
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

            CharacterDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CharacterDetailUiState.Success -> {
                val characterDetail = (uiState as CharacterDetailUiState.Success).characterDetail
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    CharacterHeader(characterDetail)
                    if (characterDetail.description.isNotEmpty()) {
                        CharacterDescription(characterDetail.description)
                    }
                    ComicsSection(characterDetail.comicList)
                    LinksSection(
                        characterDetail.characterName, characterDetail.usefulLinks, navController
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun CharacterHeader(
    characterDetail: CharacterDetail
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight / 3
    Box(
        Modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(characterDetail.thumbnail.getImageUrl(ImageFormat.LANDSCAPE(ImageFormat.ImageSize.AMAZING)))
                .build(),
            placeholder = painterResource(id = R.drawable.logo_marvel),
            error = painterResource(id = R.drawable.logo_marvel),
            contentScale = ContentScale.FillWidth,
            contentDescription = "${characterDetail.characterName} image"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black), startY = 150f
                    )
                )
        )
        Text(
            text = characterDetail.characterName,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun CharacterDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ComicsSection(comics: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Comics",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(comics) { comic ->
                ComicCard(comic)
            }
        }
    }
}

@Composable
fun ComicCard(comic: String) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.logo_marvel),
                contentDescription = comic,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Text(
                text = comic,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun LinksSection(characterName: String, links: List<Link>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Links",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(links) { link ->
                LinkButton(
                    link = link, navController = navController, characterName = characterName
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkButton(
    characterName: String, link: Link, navController: NavController
) {
    val icon = when (link.linkType) {
        Link.LinkType.DETAIL -> Icons.TwoTone.Info
        Link.LinkType.WIKI -> Icons.TwoTone.Book
        Link.LinkType.COMIC -> Icons.TwoTone.ChatBubble
        Link.LinkType.OTHER -> Icons.TwoTone.Link
    }

    val textId = when (link.linkType){
        Link.LinkType.DETAIL -> R.string.detail_button
        Link.LinkType.WIKI -> R.string.wiki_button
        Link.LinkType.COMIC -> R.string.comic_button
        Link.LinkType.OTHER -> R.string.other_button
    }

    val context = LocalContext.current

    Card(modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {
            navController.navigate(Routes.WebViewRoute.createRoute(link.urlEncoded, characterName))
        }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icon,
                contentDescription = "${link.linkType} link"
            )
            Text(
                text = context.getString(textId),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailTopBar(navController: NavController, title: String) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(text = title)
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Arrow Icon")
        }
    })
}