package org.d3if0070.finansiap.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.d3if0070.finansiap.navigation.Screen
import org.d3if0070.finansiap.navigation.listOfNavItems
import org.d3if0070.finansiap.ui.theme.BackgroundBar
import org.d3if0070.finansiap.ui.theme.Outline

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String) {
    NavigationBar(modifier = Modifier.clip(shape = RoundedCornerShape(32.dp, 32.dp)), containerColor = BackgroundBar) {
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
                        contentDescription = null,
                        tint = if (navItems.route == currentRoute) Outline else Color.White
                    )
                },
                label = {
                    Text(text = navItems.label, color = if (navItems.route == currentRoute) Outline else Color.White)
                }
            )
        }
    }
}


@Preview
@Composable
fun BotNavPrev() {
    BottomNavBar(navController = rememberNavController(), Screen.Dashboard.route)
}