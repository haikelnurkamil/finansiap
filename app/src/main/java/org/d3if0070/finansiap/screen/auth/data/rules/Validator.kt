package com.example.projecttingkat2.data.rules

object Validator {

    fun validateLastName(uName: String): ValidationResult {
        return ValidationResult(
            (!uName.isNullOrEmpty() && uName.length >= 4)
        )
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty())
        )
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 4)
        )
    }

}

data class ValidationResult(
    val status: Boolean = false
)