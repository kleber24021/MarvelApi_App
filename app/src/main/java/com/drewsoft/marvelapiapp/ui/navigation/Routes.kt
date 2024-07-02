package com.drewsoft.marvelapiapp.ui.navigation

sealed class Routes(
    val route: String
) {
    companion object{
        const val LIST_ROUTE = "characterList"
        const val DETAIL_ROUTE = "characterDetail"
        const val CHAR_ID_PARAM = "charId"
    }
    data object CharacterListRoute: Routes(LIST_ROUTE)
    data object CharacterDetailRoute: Routes("$DETAIL_ROUTE/{$CHAR_ID_PARAM}"){
        fun createRoute(charId: Int) = "$DETAIL_ROUTE/$charId"
    }

}