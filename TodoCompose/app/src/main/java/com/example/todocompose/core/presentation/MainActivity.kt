package com.example.todocompose.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.example.todocompose.core.presentation.components.Navigation
import com.example.todocompose.core.presentation.ui.theme.TodoComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Navigation(navController = navController)
                }
            }
        }
    }
}

