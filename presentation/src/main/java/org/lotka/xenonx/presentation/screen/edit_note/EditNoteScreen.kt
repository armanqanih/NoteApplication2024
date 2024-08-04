package org.lotka.xenonx.presentation.screen.edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.lotka.xenonx.domain.model.Note
import org.lotka.xenonx.presentation.screen.edit_note.compose.EditHintTextFeild
import org.lotka.xenonx.presentation.util.UIEvent

@Composable
fun EditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: EditNoteViewModel = hiltViewModel(),
) {

    val titleState = viewModel.noteTitle.collectAsState().value
    val contentState = viewModel.noteContent.collectAsState().value


    val scope = rememberCoroutineScope()
    val scafoldSate = rememberScaffoldState()

    val noteBackGroundAnimatable = remember {
        Animatable(Color(if (noteColor != -1) noteColor else viewModel.noteColor.value))
    }


    LaunchedEffect (key1 = true){
        viewModel.eventFlow.collectLatest {event->
            when(event){
                is UIEvent.ShowSnakeBar ->{
                    scafoldSate.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                UIEvent.saveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(EditNoteEvent.SaveNote)

                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scafoldSate,
        content = { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Note.noteColors.onEach { color ->
                        val colorInt = color.toArgb()
                        Box(modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent, shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackGroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(EditNoteEvent.ChangeColor(colorInt))
                            }
                        )


                    }
                }

               Spacer(modifier = Modifier.height(16.dp))
                EditHintTextFeild(
                    value = titleState.text,
                    onValueChange = {
                        viewModel.onEvent(EditNoteEvent.EnterTitle(it))
                    },
                    hint = titleState.hint,
                    onFocusChange = {
                        viewModel.onEvent(EditNoteEvent.TitleChange(it))
                    } ,
                    textStyle = MaterialTheme.typography.h1,
                    singleLine = true,
                    hintVisible = titleState.isHintVisible
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditHintTextFeild(
                    value = contentState.text,
                    onValueChange = {
                        viewModel.onEvent(EditNoteEvent.EnterContent(it))
                    },
                    hint = contentState.hint,
                    onFocusChange = {
                        viewModel.onEvent(EditNoteEvent.ContentChange(it))
                    } ,
                    textStyle = MaterialTheme.typography.h6,
                    singleLine = true,
                    hintVisible = contentState.isHintVisible
                )


            }


        }
    )


}






