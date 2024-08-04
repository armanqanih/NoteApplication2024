package org.lotka.xenonx.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {




    object HomeChatScreen : ScreensNavigation(route = "HomeChatScreen")
    object single_chat_screen : ScreensNavigation(route = "SingleChatScreen")





}