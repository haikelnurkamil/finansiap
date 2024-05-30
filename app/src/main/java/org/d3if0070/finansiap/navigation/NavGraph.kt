package org.d3if0070.finansiap.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import org.d3if0070.finansiap.screen.auth.LoginViewModel
import org.d3if0070.finansiap.screen.auth.RegisterScreen
import org.d3if0070.finansiap.screen.group.CreateScreen
import org.d3if0070.finansiap.screen.group.JoinScreen
import org.d3if0070.finansiap.screen.group.ListGroupScreen
import org.d3if0070.finansiap.screen.group.MenuScreen
import org.d3if0070.finansiap.screen.group.anggota.FormUploadlScreen
import org.d3if0070.finansiap.screen.group.anggota.MainScreenAnggota
import org.d3if0070.finansiap.screen.group.bendahara.ApprovalScreen
import org.d3if0070.finansiap.screen.group.bendahara.DetailScreen
import org.d3if0070.finansiap.screen.group.bendahara.MainScreenBendahara

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberNavController()
    val loginViewModel: LoginViewModel

    var showBottomNavigation by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            if (showBottomNavigation) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItems ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItems.route } == true,
                            onClick = {
                                navController.navigate(navItems.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItems.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = navItems.label)
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
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screen.Login.route) {
                LoginScreen(loginViewModel = null, navController)
            }
            composable(route = Screen.Register.route) {
                RegisterScreen(navController)
            }
            composable(route = Screen.Dashboard.route) {
                DashboardScreen(navController)
            }
            composable(route = Screen.Group.route) {
                GroupScreen(navController)
            }
            composable(route = Screen.Account.route) {
                AccountScreen(navController)
            }
            composable(route = Screen.CreateGroup.route) {
                CreateScreen(navController)
            }
            composable(route = Screen.JoinGroup.route) {
                JoinScreen(navController)
            }
            composable(route = Screen.ListGroup.route) {
                ListGroupScreen(navController)
            }
            composable(route = Screen.MainScreenAnggota.route) {
                MainScreenAnggota(navController)
            }
            composable(route = Screen.MainScreenBendahara.route) {
                MainScreenBendahara(navController)
            }
            composable(route = Screen.Menu.route) {
                MenuScreen(navController)
            }
            composable(route = Screen.DetailScreen.route) {
                DetailScreen(navController)
            }
            composable(route = Screen.Approval.route) {
                ApprovalScreen(navController)
            }
            composable(route = Screen.FormUpload.route) {
                FormUploadlScreen(navController)
            }
        }
        LaunchedEffect(navController.currentDestination) {
            val currentDestination = navController.currentDestination
            showBottomNavigation = listOf(
                Screen.Dashboard.route,
                Screen.Group.route,
                Screen.Account.route
            ).contains(currentDestination?.route)
        }
    }
}







