package com.ponhrith.banking_project.controller.response

data class RegisterRes(
    var id: Long = 0,
    val fullname: String,
    val address: String,
    val email: String? = null,
//    var account: AccountRes? = null,
    val password: String? = null,

)
