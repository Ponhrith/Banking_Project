package com.ponhrith.banking_project.controller.request

data class DepositReq(
    var type: String,
    var amount: Double,
    var sourceAccount: String
)
