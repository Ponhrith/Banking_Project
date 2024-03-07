package com.ponhrith.banking_project.common

import com.ponhrith.banking_project.common.Constants.TYPE
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

fun String.isValidType() {
    if (!TYPE.contains(this))
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid type")
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
