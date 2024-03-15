package com.ponhrith.banking_project.controller.request


data class TransactionReq(
    val profiileId: Long,
    var sourceAccount: Long,
    val type: String,
    var targetAccount: Long,
    var amount: Double,
)