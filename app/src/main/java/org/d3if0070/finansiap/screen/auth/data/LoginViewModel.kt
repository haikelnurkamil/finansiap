package org.d3if0070.finansiap.screen.auth.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projecttingkat2.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent) {
        validateDataWithRules()
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }

            else -> {}
        }
    }

    private fun login() {

    }

    private fun validateDataWithRules() {

        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "emailResult: $emailResult")
        Log.d(TAG, "passwordResult: $passwordResult")

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, loginUIState.value.toString())
    }

    private fun createUserInFirebase(email: String, password: String) {

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Excetion = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }
    }

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener = AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign outsuccess")
            } else {
                Log.d(TAG, "Inside sign out is not success")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}