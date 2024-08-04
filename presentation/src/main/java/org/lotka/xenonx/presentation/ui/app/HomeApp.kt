package org.lotka.xenonx.presentation.ui.app




import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import org.lotka.xenonx.presentation.screen.edit_note.EditNoteScreen
import org.lotka.xenonx.presentation.screen.note.NotesScreen

import org.lotka.xenonx.presentation.ui.navigation.ScreensNavigation


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun HomeApp(
    activity: HomeActivity,
    navController: NavHostController,
    onNavigateToRecipeDetailScreen: (String) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    keyboardController: SoftwareKeyboardController,

    ) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()




    Scaffold(

        content = { _ ->
            NavHost(navController = navController,
                startDestination = ScreensNavigation.NoteScreen.route,
                enterTransition = {
                    // you can change whatever you want transition
                    EnterTransition.None
                },
                exitTransition = {
                    // you can change whatever you want transition
                    ExitTransition.None
                }) {
                composable(
                    route = ScreensNavigation.NoteScreen.route,
                ) {
                    NotesScreen(navController = navController)

                }
                composable(
                    route = ScreensNavigation.EditNoteScreen.route +
                            "?noteId = {noteId} & noteColor{noteColor}" ,
                    arguments = listOf(
                        navArgument(name = "noteId" ){
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(name = "noteColor" ){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )

                ) {

                    val color = it.arguments?.getInt("noteColor")?: -1

                    EditNoteScreen(
                        navController = navController
                        ,noteColor = color
                    )

                }


            }

        },
    )

}



