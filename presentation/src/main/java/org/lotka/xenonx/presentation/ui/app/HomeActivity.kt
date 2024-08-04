package org.lotka.xenonx.presentation.ui.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val keyboardController = LocalSoftwareKeyboardController.current



            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                if (keyboardController != null) {
                    HomeApp(
                        activity = this@HomeActivity,
                        navController = navController,
                        onNavigateToRecipeDetailScreen = {
                            },
                        isDarkTheme = false,
                        onToggleTheme = { },
                        keyboardController = keyboardController,

                    )
                }
            }
        }





    }


}
