package com.drewsoft.marvelapiapp.ui.navigation

sealed class Routes(
    val route: String
) {
    companion object{
        const val LIST_ROUTE = "characterList"
        const val DETAIL_ROUTE = "characterDetail"
        const val WEBVIEW_ROUTE = "webview"
        const val CHAR_ID_PARAM = "charId"
        const val CHAR_NAME_PARAM = "charName"
        const val ENCODED_URL = "urlEncoded"
    }
    data object CharacterListRoute: Routes(LIST_ROUTE)
    data object CharacterDetailRoute: Routes("$DETAIL_ROUTE/{$CHAR_ID_PARAM}/{$CHAR_NAME_PARAM}"){
        fun createRoute(charId: Int, charName: String) = "$DETAIL_ROUTE/$charId/$charName"
    }
    data object WebViewRoute: Routes("$WEBVIEW_ROUTE/{$ENCODED_URL}/{$CHAR_NAME_PARAM}"){
        fun createRoute(url: String, characterName: String) = "$WEBVIEW_ROUTE/$url/$characterName"
    }
}