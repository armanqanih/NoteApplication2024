package org.lotka.xenonx.presentation.screen.edit_note

import android.provider.ContactsContract.CommonDataKinds.Note

data class EditNoteState (
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val notes : Note? = null
)