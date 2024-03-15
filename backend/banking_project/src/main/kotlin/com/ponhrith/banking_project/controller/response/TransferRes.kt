package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Account
import java.time.LocalDateTime

data class TransferRes(
    val id: Long,
    var sourceAccount: String,
    var targetAccount: String,
    val type: String = "Transfer",
    var amount: Double,
    var date: LocalDateTime,
    val account: Account? = null,
)
