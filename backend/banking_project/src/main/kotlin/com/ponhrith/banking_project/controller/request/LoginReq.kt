package com.ponhrith.banking_project.controller.request

data class LoginReq(
    val accountNumber: String,
    var password: String
)
