package com.drewsoft.marvelapiapp.ui.characterlist

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drewsoft.marvelapiapp.R
import com.drewsoft.marvelapiapp.domain.model.Character
import com.drewsoft.marvelapiapp.domain.model.ImageFormat

@Composable
fun CharactersListScreen(
    viewModel: CharacterListViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<CharacterListUiState>(
        initialValue = CharacterListUiState.Loading, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is CharacterListUiState.Error -> {
            //TODO: ERROR State
        }
        CharacterListUiState.Loading -> {
            CircularProgressIndicator()
        }

        is CharacterListUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CharactersList(
                    (uiState as CharacterListUiState.Success).characters.characters, viewModel
                )
            }
        }
    }
}

@Composable
fun CharactersList(
    characters: List<Character>, viewModel: CharacterListViewModel
) {
    LazyColumn {
        items(characters, key = { it.id }) {
            CharacterCard(it, viewModel)
        }
    }
}

@Composable
fun CharacterCard(
    character: Character, viewModel: CharacterListViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(128.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.getImageUrl(ImageFormat.LANDSCAPE(ImageFormat.ImageSize.XLARGE)))
                    .build(),
                placeholder = painterResource(id = R.drawable.logo_marvel),
                error = painterResource(id = R.drawable.logo_marvel),
                contentScale = ContentScale.Crop,
                contentDescription = "${character.name} image"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
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