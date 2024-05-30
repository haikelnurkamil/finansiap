package org.d3if0070.finansiap.screen.auth.data

data class RegistrationUIState(
    var userName: String = "",
    var email: String = "",
    var password: String = "",

    var userNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false
)