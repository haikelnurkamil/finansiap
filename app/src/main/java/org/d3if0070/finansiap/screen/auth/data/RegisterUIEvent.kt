package org.d3if0070.finansiap.screen.auth.data

sealed class RegisterUIEvent {
    data class UserNameChanged(val userName: String) : RegisterUIEvent()
    data class EmailChanged(val email: String) : RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()

    object RegisterButtonClicked : RegisterUIEvent()
}