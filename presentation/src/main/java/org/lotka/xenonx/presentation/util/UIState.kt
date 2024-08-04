package org.lotka.xenonx.presentation.util

import org.lotka.xenonx.presentation.screen.edit_note.EditNoteEvent

sealed class UIEvent {

    object saveNote : UIEvent()
    data class ShowSnakeBar(val message: String) : UIEvent()

}