package com.ponhrith.banking_project.controller.request

data class RegisterReq(
    var fullname: String,
    var address: String,
    var email: String,
    var password: String,
)
