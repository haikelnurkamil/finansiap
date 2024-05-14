package org.d3if0070.finansiap.navigation

sealed class Screen(val route: String) {
        data object Login: Screen("loginScreen")
        data object Register: Screen("registerScreen")
        data object  Dashboard: Screen("dashboardScreen")
        data object Group: Screen("groupScreen")
        data object Account: Screen("accountScreen")
}