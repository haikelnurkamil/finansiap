package org.d3if0070.finansiap.navigation

sealed class Screen(val route: String) {
        data object Login: Screen("loginScreen")
}