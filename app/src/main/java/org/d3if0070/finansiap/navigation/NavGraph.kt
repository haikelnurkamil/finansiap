package org.d3if0070.finansiap.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.screen.LoginScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
    }
}