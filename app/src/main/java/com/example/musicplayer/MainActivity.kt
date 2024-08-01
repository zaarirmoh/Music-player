package com.example.musicplayer

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.ui.navigation.GetStartedScreenN
import com.example.musicplayer.ui.navigation.HomeScreenN
import com.example.musicplayer.ui.navigation.MusicPlayerNavigation
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val SHOW_WELCOME_SCREEN = booleanPreferencesKey("show_welcome_screen")

class MainActivity : ComponentActivity() {

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicPlayerViewModel::class.java)) {
                return MusicPlayerViewModel(context = applicationContext) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    private val viewModel by viewModels<MusicPlayerViewModel>{ viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val isAppThemeDark = remember { mutableStateOf(isSystemInDarkTheme) }
            MusicPlayerTheme(darkTheme = isAppThemeDark.value) {
                Surface(modifier = Modifier.fillMaxSize()){

                    val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent, // Set your desired color here
                        darkIcons = isAppThemeDark.value
                    )

                    val startDestination = viewModel.getStartingScreen()
                    viewModel.changeStartingScreen()
                    if(startDestination == "GetStartedScreen"){
                        MusicPlayerNavigation(
                            startDestination = GetStartedScreenN::class,
                            activity = this@MainActivity,
                            isAppThemeDark = isAppThemeDark
                        )
                    }else if(startDestination == "HomeScreen"){
                        MusicPlayerNavigation(
                            startDestination = HomeScreenN::class,
                            activity = this@MainActivity,
                            isAppThemeDark = isAppThemeDark
                        )
                    }

                    /*
                    val intent = Intent(this, PlayBackService::class.java)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            startService(intent)
                        }) {
                            Text(text = "Play")
                        }
                    }
                    startForegroundService(intent)
                     */

                }
            }
        }
    }
}





