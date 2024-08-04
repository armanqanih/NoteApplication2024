package org.lotka.xenonx.presentation.screen.note

import org.lotka.xenonx.domain.model.Note
import org.lotka.xenonx.domain.util.NoteOrder


sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}