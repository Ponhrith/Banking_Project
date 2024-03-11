package com.ponhrith.banking_project.controller.request

import com.ponhrith.banking_project.model.Profile

data class AccountReq(
    val profileId: Long,
    val type: String,
    val number: String,
    val balance: Double = 0.00
)
