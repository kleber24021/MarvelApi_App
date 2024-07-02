package com.drewsoft.marvelapiapp.ui.characterdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    navController: NavController,
    characterId: Int
){
    Text(text = "On development: $characterId")
}