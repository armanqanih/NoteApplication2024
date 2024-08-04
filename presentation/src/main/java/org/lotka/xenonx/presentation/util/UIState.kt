package org.lotka.xenonx.presentation.util

sealed class UIEvent {

    object saveNote : UIEvent()
    data class ShowSnakeBar(val message: String) : UIEvent()

}