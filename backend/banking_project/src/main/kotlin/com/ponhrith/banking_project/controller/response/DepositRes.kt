package com.ponhrith.banking_project.controller.response

import java.time.LocalDateTime

data class DepositRes(
    val id: Long,
    var amount: Double,
    var date: LocalDateTime,

)
