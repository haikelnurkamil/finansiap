package org.d3if0070.finansiap.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.screen.account.AccountScreen
import org.d3if0070.finansiap.screen.dashboard.DashboardScreen
import org.d3if0070.finansiap.screen.group.GroupScreen
import org.d3if0070.finansiap.screen.auth.LoginScreen

import org.d3if0070.finansiap.screen.auth.RegisterScreen
import org.d3if0070.finansiap.screen.group.CreateScreen
import org.d3if0070.finansiap.screen.group.JoinScreen
import org.d3if0070.finansiap.screen.group.ListGroupScreen
import org.d3if0070.finansiap.screen.group.MenuScreen
import org.d3if0070.finansiap.screen.group.anggota.FormUploadlScreen
import org.d3if0070.finansiap.screen.group.anggota.MainScreenAnggota
import org.d3if0070.finansiap.screen.group.anggota.SuccessUploadScreen
import org.d3if0070.finansiap.screen.group.bendahara.ApprovalScreen
import org.d3if0070.finansiap.screen.group.bendahara.DetailScreen
import org.d3if0070.finansiap.screen.group.bendahara.MainScreenBendahara

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberNavController()

    var showBottomNavigation by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            if (showBottomNavigation) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Register.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.Login.route) {
                LoginScreen(navController = navController,viewModel())
            }
            composable(route = Screen.Register.route) {
                RegisterScreen(navController = navController, viewModel())
            }
            composable(route = Screen.Dashboard.route) {
                DashboardScreen(navController = navController)
            }
            composable(route = Screen.Group.route) {
                GroupScreen(navController = navController)
            }
            composable(route = Screen.Account.route) {
                AccountScreen(navController = navController)
            }
            composable(route = Screen.CreateGroup.route) {
                CreateScreen(navController = navController)
            }
            composable(route = Screen.JoinGroup.route) {
                JoinScreen(navController = navController)
            }
            composable(route = Screen.ListGroup.route) {
                ListGroupScreen(navController = navController)
            }
            composable(route = Screen.MainScreenAnggota.route) {
                MainScreenAnggota(navController = navController)
            }
            composable(route = Screen.MainScreenBendahara.route) {
                MainScreenBendahara(navController = navController)
            }
            composable(route = Screen.Menu.route) {
                MenuScreen(navController = navController)
            }
            composable(route = Screen.DetailScreen.route) {
                DetailScreen(navController = navController)
            }
            composable(route = Screen.Approval.route) {
                ApprovalScreen(navController = navController)
            }
            composable(route = Screen.FormUpload.route) {
                FormUploadlScreen(navController = navController)
            }
            composable(route = Screen.SuccessUpload.route) {
                SuccessUploadScreen(navController = navController)
            }
        }

        LaunchedEffect(navController.currentBackStackEntryAsState()) {
            val currentDestination = navController.currentBackStackEntry?.destination
            showBottomNavigation = listOf(
                Screen.Dashboard.route,
                Screen.Group.route,
                Screen.Account.route
            ).contains(currentDestination?.route)
        }
    }
}
