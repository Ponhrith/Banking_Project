package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Profile

data class AccountRes(
    val id: Long,
    val profile: Profile,
    var type: String,
    var number: String,
    var balance: Double,
    var currency: String,
)
