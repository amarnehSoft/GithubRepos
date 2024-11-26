package com.github.repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.repos.core.data.util.NetworkMonitor
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.ui.GithubReposApp
import com.github.repos.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            val darkTheme = shouldUseDarkTheme()

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
//            DisposableEffect(darkTheme) {
//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.auto(
//                        android.graphics.Color.TRANSPARENT,
//                        android.graphics.Color.TRANSPARENT,
//                    ) { darkTheme },
//                    navigationBarStyle = SystemBarStyle.auto(
//                        lightScrim,
//                        darkScrim,
//                    ) { darkTheme },
//                )
//                onDispose {}
//            }

            val appState = rememberAppState(
                networkMonitor = networkMonitor,
            )

            NiaTheme {
                GithubReposApp(appState)
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(): Boolean = isSystemInDarkTheme()

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
