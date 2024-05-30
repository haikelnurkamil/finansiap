package org.d3if0070.finansiap.screen.auth.data

data class LoginUIState(
    var email: String = "",
    var password: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false
)