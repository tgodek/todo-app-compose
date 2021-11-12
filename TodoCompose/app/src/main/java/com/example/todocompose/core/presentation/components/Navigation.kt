package com.example.todocompose.core.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todocompose.core.util.Constants.PARAM_ACTION
import com.example.todocompose.core.util.Constants.PARAM_TASK_ID
import com.example.todocompose.core.util.Screen
import com.example.todocompose.feature_task.presentation.task.TaskScreen
import com.example.todocompose.feature_task.presentation.task_list.TaskListScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TaskListScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        //Početni Screen s popisom taskova
        composable(
            route = Screen.TaskListScreen.route
        ) {
            TaskListScreen(navController = navController)
        }

        //Prikaz početnog Screena nakon navigiranja iz ScreenDetail
        //action parametar je akcija koja se proslijedi početnog screenu (Dodan novi task, ažuriran task ...)
        composable(
            route = Screen.TaskListScreen.route + "?$PARAM_ACTION={action}",
            arguments = listOf(
                navArgument(PARAM_ACTION) {
                    defaultValue = ""
                    nullable = true
                    type = NavType.StringType
                })
        ) {
            TaskListScreen(navController = navController)
        }

        //Prikaz Screena s detaljnim informacijama o tasku kada se pritisne na neki task
        composable(
            route = Screen.TaskScreen.route + "?$PARAM_ACTION={action}&$PARAM_TASK_ID={taskId}",
            arguments = listOf(
                navArgument(PARAM_ACTION) {
                    type = NavType.StringType
                },
                navArgument(PARAM_TASK_ID) {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
            TaskScreen(navController = navController)
        }

        //Prikaz Screena s detaljnim informacijama o tasku kada pritisnemo gumb za dodavanje novog taska
        composable(
            route = Screen.TaskScreen.route + "?$PARAM_ACTION={action}",
            arguments = listOf(
                navArgument(PARAM_ACTION) {
                    type = NavType.StringType
                },
            )
        ) {
            TaskScreen(navController = navController)
        }
    }
}