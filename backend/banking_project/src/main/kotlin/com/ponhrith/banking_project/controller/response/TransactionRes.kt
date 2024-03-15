package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Account
import java.time.LocalDate

data class TransactionRes(
    val id: Long,
    var sourceAccount: Long,
    val type: String,
    var targetAccount: Long,
    var amount: Double,
    var date: LocalDate,
    val account: Account? = null,
)
