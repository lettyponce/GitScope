package com.lfp.gitscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.lfp.gitscope.presentation.navigation.GitScopeNavGraph
import com.lfp.gitscope.presentation.theme.GitScopeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitScopeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    GitScopeNavGraph()
                }
            }
        }
    }
}