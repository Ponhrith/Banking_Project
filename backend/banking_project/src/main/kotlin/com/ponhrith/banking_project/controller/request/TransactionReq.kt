package com.ponhrith.banking_project.controller.request

import java.time.LocalDate

data class TransactionReq(
    val profiileId: Long,
    var sourceAccount: Long,
    val type: String,
    var targetAccount: Long,
    var amount: Double,
)
