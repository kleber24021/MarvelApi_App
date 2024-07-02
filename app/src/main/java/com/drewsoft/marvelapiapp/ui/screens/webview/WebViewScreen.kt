package com.drewsoft.marvelapiapp.ui.screens.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.drewsoft.marvelapiapp.ui.screens.characterdetail.CharacterDetailTopBar

@Composable
fun WebViewScreen(url: String, navController: NavController, characterName: String){
    Scaffold(
        topBar = {
            CharacterDetailTopBar(navController, characterName)
        }
    ){paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            val context = LocalContext.current
            AndroidView(factory = {
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            })
        }
    }
}