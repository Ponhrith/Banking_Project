package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Account
import java.time.LocalDateTime

data class DepositRes(
    val id: Long,
    var amount: Double,
    var date: LocalDateTime,
    var type: String,
    var account: Account? = null,
)
