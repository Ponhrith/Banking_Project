package com.ponhrith.banking_project.controller.response

import java.time.LocalDate

data class TransactionRes(
    val profiileId: Long,
    var sourceAccount: Long,
    val type: String,
    var targetAccount: Long,
    var amount: Double,
    var date: LocalDate
)
