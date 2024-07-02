package com.drewsoft.marvelapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.drewsoft.marvelapiapp.ui.characterdetail.CharacterDetailScreen
import com.drewsoft.marvelapiapp.ui.characterdetail.CharacterDetailViewModel
import com.drewsoft.marvelapiapp.ui.characterlist.CharacterListViewModel
import com.drewsoft.marvelapiapp.ui.characterlist.CharactersListScreen
import com.drewsoft.marvelapiapp.ui.navigation.Routes.CharacterListRoute
import com.drewsoft.marvelapiapp.ui.navigation.Routes.CharacterDetailRoute
import com.drewsoft.marvelapiapp.ui.navigation.Routes.Companion.CHAR_ID_PARAM
import com.drewsoft.marvelapiapp.ui.theme.MarvelAPIAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAPIAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CharacterListRoute.route
                ){
                    composable(CharacterListRoute.route){
                        CharactersListScreen(characterListViewModel, navController)
                    }
                    composable(
                        CharacterDetailRoute.route,
                        arguments = listOf(navArgument(CHAR_ID_PARAM){ type = NavType.IntType})
                    ){backstack ->
                        val charId = backstack.arguments?.getInt(CHAR_ID_PARAM)
                        if(charId != null){
                            CharacterDetailScreen(characterDetailViewModel, navController, charId)
                        }
                    }
                }
            }
        }
    }
}