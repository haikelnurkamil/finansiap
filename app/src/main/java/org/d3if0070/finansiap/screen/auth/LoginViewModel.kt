package org.d3if0070.finansiap.screen.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if0070.finansiap.repository.AuthRepository
import java.lang.Exception

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {
    val currentUser = repository.currentUser

    val hasUser:Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onFullNameChange(fullName: String) {
        loginUiState = loginUiState.copy(fullName = fullName)
    }

    fun onEmailChange(email: String) {
        loginUiState = loginUiState.copy(email = email)
    }
    fun onPassword(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }
    fun onFullNameSignUp(fullName: String) {
        loginUiState = loginUiState.copy(fullNameSingUp = fullName)
    }
    fun onEmailChangeSignUp(email: String) {
        loginUiState = loginUiState.copy(emailSignUp = email)
    }
    fun onPasswordChange(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    private fun validationLoginForm() =
        loginUiState.email.isBlank() && loginUiState.password.isNotBlank()

    private fun validationSignUpForm() =
        loginUiState.fullNameSingUp.isNotBlank() && loginUiState.emailSignUp.isNotBlank() && loginUiState.passwordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validationSignUpForm()) {
                throw IllegalArgumentException("email and password can be not be empty")
            }

            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.fullNameSingUp,
                loginUiState.emailSignUp,
                loginUiState.passwordSignUp
            ){ isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        }catch (e:Exception) {
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validationLoginForm()) {
                throw IllegalArgumentException("email and password can be not be empty")
            }

            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.email,
                loginUiState.password
            ){ isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        }catch (e:Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
}

data class LoginUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val fullNameSingUp: String = "",
    val emailSignUp: String = "",
    val passwordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)