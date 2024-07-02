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
import com.drewsoft.marvelapiapp.ui.navigation.Routes
import com.drewsoft.marvelapiapp.ui.screens.characterdetail.CharacterDetailScreen
import com.drewsoft.marvelapiapp.ui.screens.characterdetail.CharacterDetailViewModel
import com.drewsoft.marvelapiapp.ui.screens.characterlist.CharacterListViewModel
import com.drewsoft.marvelapiapp.ui.screens.characterlist.CharactersListScreen
import com.drewsoft.marvelapiapp.ui.navigation.Routes.CharacterListRoute
import com.drewsoft.marvelapiapp.ui.navigation.Routes.CharacterDetailRoute
import com.drewsoft.marvelapiapp.ui.navigation.Routes.Companion.CHAR_ID_PARAM
import com.drewsoft.marvelapiapp.ui.navigation.Routes.Companion.CHAR_NAME_PARAM
import com.drewsoft.marvelapiapp.ui.navigation.Routes.Companion.ENCODED_URL
import com.drewsoft.marvelapiapp.ui.screens.webview.WebViewScreen
import com.drewsoft.marvelapiapp.ui.theme.MarvelAPIAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
                        arguments = listOf(
                            navArgument(CHAR_ID_PARAM){ type = NavType.IntType},
                            navArgument(CHAR_NAME_PARAM){ type = NavType.StringType}
                        )
                    ){backstack ->
                        val charId = backstack.arguments?.getInt(CHAR_ID_PARAM)
                        val charName = backstack.arguments?.getString(CHAR_NAME_PARAM)
                        if(charId != null){
                            CharacterDetailScreen(characterDetailViewModel, navController, charId, charName ?: "")
                        }
                    }
                    composable(
                        Routes.WebViewRoute.route,
                        arguments = listOf(
                            navArgument(ENCODED_URL){ type = NavType.StringType},
                            navArgument(CHAR_NAME_PARAM) {type = NavType.StringType }
                        )
                    ){backstack ->
                        val encodedUrl = backstack.arguments?.getString(ENCODED_URL)
                        val charName = backstack.arguments?.getString(CHAR_NAME_PARAM)
                        if (encodedUrl != null && charName != null){
                            val decodedUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
                            WebViewScreen(decodedUrl, navController, charName)
                        }
                    }
                }
            }
        }
    }
}