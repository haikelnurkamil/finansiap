package org.d3if0070.finansiap.screen.auth.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projecttingkat2.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    var isLoginSuccessful = mutableStateOf(false)

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
                checkCredentialsAndLogin()
            }
        }
        validateDataWithRules()
    }

    private fun validateDataWithRules() {
        val emailResult = Validator.validateEmail(email = loginUIState.value.email)
        val passwordResult = Validator.validatePassword(password = loginUIState.value.password)

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "emailResult: $emailResult")
        Log.d(TAG, "passwordResult: $passwordResult")

        loginUIState.value = loginUIState.value.copy(
            emailError = !emailResult.status,
            passwordError = !passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun checkCredentialsAndLogin() {
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        loginInProgress.value = true // Indikator bahwa proses login sedang berjalan

        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            val userRef = firestore.collection("users").whereEqualTo("email", email).get()

            userRef.addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Email ditemukan di Firestore, lanjutkan dengan login Firebase
                    loginWithFirebase(email, password)
                } else {
                    // Email tidak ditemukan di Firestore
                    loginInProgress.value = false
                    Log.d(TAG, "Login gagal: Email tidak ditemukan di database")
                }
            }.addOnFailureListener { exception ->
                loginInProgress.value = false
                Log.d(TAG, "Login gagal: ${exception.message}")
            }
        }
    }

    private fun loginWithFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loginInProgress.value = false // Proses login selesai

                if (task.isSuccessful) {
                    Log.d(TAG, "Login berhasil")
                    isLoginSuccessful.value = true // Login berhasil
                } else {
                    Log.d(TAG, "Login gagal: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                loginInProgress.value = false // Proses login selesai
                Log.d(TAG, "Login gagal: ${exception.message}")
            }
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, loginUIState.value.toString())
    }

    private fun createUserInFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
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
                Log.d(TAG, "Inside sign out success")
            } else {
                Log.d(TAG, "Inside sign out is not success")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}
