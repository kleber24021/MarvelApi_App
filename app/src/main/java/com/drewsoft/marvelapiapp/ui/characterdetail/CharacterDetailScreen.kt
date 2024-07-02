package com.drewsoft.marvelapiapp.ui.characterdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    navController: NavController,
    characterId: Int,
    characterName: String
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CharacterDetailTopBar(navController, characterName)
        }
    ) {

    }
}

@Composable
fun CharacterDetail(viewModel: CharacterDetailViewModel){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailTopBar(navController: NavController, title: String){
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(text = title)
    }, navigationIcon ={
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Arrow Icon")
        }
    })
}