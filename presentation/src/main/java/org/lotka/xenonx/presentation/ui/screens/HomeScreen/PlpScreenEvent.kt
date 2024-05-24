package org.lotka.xenonx.presentation.ui.screens.HomeScreen

sealed class PlpScreenEvent {

    object NavigateToSignUp : PlpScreenEvent()
    class ShowSnackBar(val message: String?) : PlpScreenEvent()
    object NewSearchEvent : PlpScreenEvent()
    object NextPageEvent : PlpScreenEvent()
    object SearchLocationPhrase : PlpScreenEvent()
    object RestoreStateEvent : PlpScreenEvent()
}