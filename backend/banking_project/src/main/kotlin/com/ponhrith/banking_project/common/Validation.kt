package com.ponhrith.banking_project.common

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun String.isValidTransactionType() {
    if (!Constants.TRANSACTION_TYPE.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction type")
}

fun String.isValidAccountType() {
    if (!Constants.ACCOUNT_TYPE.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account type")
}

fun String.isValidCurrencyType(){
    if (!Constants.CURRENCY_TYPE.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency type")
}

fun String.isValidEmail(): Boolean {
    if (this.isEmpty())
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address cannot be empty")

    val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
    if (!this.matches(regex)) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email address")
    }
    return true
}

fun String.isValidFullName(): Boolean {
    if (this.isEmpty())
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name cannot be empty")

    val regex = Regex("^[a-zA-Z]+(?:\\s[a-zA-Z]+)+\$")
    val containsNumbersOrSpecialChars = Regex("[^a-zA-Z\\s]").containsMatchIn(this)
    if (!this.matches(regex) || containsNumbersOrSpecialChars) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Invalid full name, must contain at least one space and consist of alphabetical characters only")
    }
    return true
}

fun String.isValidPassword(): Boolean {
    if (this.isEmpty())
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty")

    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
    if (!this.matches(regex)) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password. " +
                "It must be at least 8 characters long, contain at least one uppercase letter, " +
                "one lowercase letter, one number, and one special character.")
    }
    return true
}


