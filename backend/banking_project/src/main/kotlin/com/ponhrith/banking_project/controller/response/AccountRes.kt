package com.ponhrith.banking_project.controller.response

data class AccountRes(
    val id: Long,
    val profileId: Long,
    var type: String,
    var number: String,
    var balance: Double
)
