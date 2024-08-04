package org.lotka.xenonx.presentation.screen.edit_note

import androidx.compose.ui.focus.FocusState

sealed class EditNoteEvent {
    data class EnterTitle(val value: String) : EditNoteEvent()
    data class TitleChange(val focusState:FocusState):EditNoteEvent()
    data class EnterContent(val value: String) : EditNoteEvent()
    data class ContentChange(val focusState:FocusState):EditNoteEvent()
    data class ChangeColor(val color: Int) : EditNoteEvent()
    object SaveNote : EditNoteEvent()

}