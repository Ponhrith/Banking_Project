package com.ponhrith.banking_project.controller.request

data class DepositReq(
    var amount: Double,
    var sourceAccount: String
)
