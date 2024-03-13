package com.ponhrith.banking_project.controller.response

data class ListAccountRes(
    val id: Long,
    var type: String,
    var number: String,
    var balance: Double
)

