package org.lotka.xenonx.presentation.screen.edit_note.compose

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun EditHintTextFeild(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    singleLine:Boolean = false,
    hintVisible : Boolean = true
    ,onFocusChange: (FocusState) -> Unit,
    textStyle: TextStyle
){
    Box(modifier = modifier){

    BasicTextField(
        value = value ,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = textStyle,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                onFocusChange(it)
            }
    )
        if (hintVisible && value.isEmpty()){
        Text(text = hint, style = textStyle,color = Color.DarkGray)
        }

    }

}