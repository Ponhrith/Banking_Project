package com.ponhrith.banking_project.controller.response

import java.util.Currency

data class ListAccountRes(
    val id: Long,
    var type: String,
    var number: String,
    var balance: Double,
    var currency: String,
)

