package com.example.algoapp

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.algoapp.ui.screens.camera.CameraScreen
import com.example.algoapp.ui.screens.home.HomeScreen
import com.example.algoapp.ui.screens.submission.SubmissionScreen
import java.io.File

object NavRoutes {
    const val homeRoute = "Home"
    const val cameraRoute = "Camera"
    const val submissionRoute = "Submission"
}

@Composable
fun NavigationGraph(outputDir: File) {
    val navController = rememberNavController()
    var permissionStatus by remember { mutableStateOf(false)}
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionStatus = isGranted
    }

    NavHost(navController = navController, startDestination = NavRoutes.homeRoute ) {
        composable(NavRoutes.homeRoute) { HomeScreen(navController) }
        composable("${NavRoutes.cameraRoute}/{language}") { backStackEntry ->
            val language = backStackEntry.arguments?.getString("language") ?: ""
            CameraScreen(language, outputDir, /*launcher, permissionStatus,*/ navController)
        }
        composable("${NavRoutes.submissionRoute}/{setUpCode}/{runnableCode}") { backStackEntry ->
            val setUpCode = backStackEntry.arguments?.getString("setUpCode") ?: ""
            val runnableCode = backStackEntry.arguments?.getString("runnableCode") ?: ""
            SubmissionScreen(setUpCode, runnableCode)
        }
    }
}

