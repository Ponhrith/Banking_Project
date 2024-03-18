package com.ponhrith.banking_project.controller.response

data class ExchangeResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

