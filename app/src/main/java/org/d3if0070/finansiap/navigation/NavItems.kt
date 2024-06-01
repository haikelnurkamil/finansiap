package org.d3if0070.finansiap.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItems(
        label = "Beranda",
        icon = Icons.Default.Home,
        route = Screen.Dashboard.route
    ),
    NavItems(
        label = "Grup",
        icon = Icons.Default.Person,
        route = Screen.ListGroup.route
    ),
    NavItems(
        label = "Akun",
        icon = Icons.Default.AccountCircle,
        route = Screen.Account.route
    )
)
