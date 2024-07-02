package com.drewsoft.marvelapiapp.ui.navigation

sealed class Routes(
    val route: String
) {
    companion object{
        const val LIST_ROUTE = "characterList"
        const val DETAIL_ROUTE = "characterDetail"
        const val CHAR_ID_PARAM = "charId"
        const val CHAR_NAME_PARAM = "charName"
    }
    data object CharacterListRoute: Routes(LIST_ROUTE)
    data object CharacterDetailRoute: Routes("$DETAIL_ROUTE/{$CHAR_ID_PARAM}/{$CHAR_NAME_PARAM}"){
        fun createRoute(charId: Int, charName: String) = "$DETAIL_ROUTE/$charId/$charName"
    }

}