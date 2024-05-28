package org.d3if0070.finansiap.navigation

sealed class Screen(val route: String) {
        data object Login: Screen("loginScreen")
        data object Register: Screen("registerScreen")
        data object  Dashboard: Screen("dashboardScreen")
        data object Group: Screen("groupScreen")
        data object Account: Screen("accountScreen")
        data object CreateGroup: Screen("createScreen")
        data object JoinGroup: Screen("joinScreen")
        data object ListGroup: Screen("listGroupScreen")
        data object MainScreenAnggota: Screen("mainScreen1")
        data object MainScreenBendahara: Screen("mainScreen2")
        data object DetailScreen: Screen("detailScreen")
        data object Menu: Screen("menuScreen")
        data object Approval: Screen("approvalScreen")
        data object FormUpload: Screen("formUploadScreen")
}