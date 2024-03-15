package com.ponhrith.banking_project.controller.request


data class TransferReq(
    var sourceAccount: String,
    var targetAccount: String,
    var amount: Double,
)
