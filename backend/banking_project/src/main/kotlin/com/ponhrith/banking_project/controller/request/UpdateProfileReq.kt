package com.ponhrith.banking_project.controller.request

data class UpdateProfileReq(
    var address: String,
    var email: String,
    var password: String,
)
