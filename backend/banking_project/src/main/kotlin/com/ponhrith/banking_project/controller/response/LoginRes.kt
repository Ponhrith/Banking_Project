package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Profile

data class LoginRes(
    val success: Boolean,
    val message: String,
    val profile: Profile? = null,
    val token: String? = null
)
