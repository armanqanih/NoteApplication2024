package org.lotka.xenonx.presentation.screen.edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.model.InvalidNoteException
import org.lotka.xenonx.domain.model.Note
import org.lotka.xenonx.domain.usecase.AllNotesUseCases
import org.lotka.xenonx.presentation.util.UIEvent
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val useCases: AllNotesUseCases,
    val savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _noteTitle = MutableStateFlow(EditNoteState( text = "", hint = "Enter Note title"))
    val noteTitle = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow(EditNoteState( text = "", hint = "Enter Note Content"))
    val noteContent = _noteTitle.asStateFlow()

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val currentNoteId :Int? = null

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {

        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    useCases.getNoteById(noteId)?.also { note ->
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.intValue = note.color
                    }
                }
            }
        }

    }



    fun onEvent(event: EditNoteEvent){

        when(event){
            is EditNoteEvent.ChangeColor ->{
                _noteColor.intValue = event.color
            }
            is EditNoteEvent.ContentChange -> {
              _noteContent.value = _noteContent.value.copy(
                  isHintVisible = event.focusState.isFocused && _noteContent.value.text.isEmpty()
              )

            }
            is EditNoteEvent.EnterContent -> {
               _noteContent.value = _noteContent.value.copy(
                   text = event.value
               )
            }
            is EditNoteEvent.EnterTitle -> {
             _noteTitle.value = _noteTitle.value.copy(
                 text = event.value
             )
            }
            is EditNoteEvent.TitleChange -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible =! event.focusState.isFocused && _noteTitle.value.text.isEmpty()
                )
            }

            EditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                    useCases.addNote(
                        Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                            id = currentNoteId
                        )
                    )
                        _eventFlow.emit(UIEvent.saveNote)
                    }catch (e: InvalidNoteException) {
                    _eventFlow.emit(UIEvent.ShowSnakeBar(message = e.message?:"Couldn't save note"))
                    }
                }

            }

        }}

        }
















