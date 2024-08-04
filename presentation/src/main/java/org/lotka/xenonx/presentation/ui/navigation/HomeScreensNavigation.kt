package org.lotka.xenonx.presentation.ui.navigation

sealed class ScreensNavigation(val route: String) {

    object NoteScreen : ScreensNavigation(route = "note_screen")
    object EditNoteScreen : ScreensNavigation(route = "edit_note_screen")

}