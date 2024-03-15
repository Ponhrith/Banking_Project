package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Profile

data class ListAccountRes(
    val id: Long,
    var type: String,
    var number: String,
    var balance: Double,
    var currency: String,
    val profiles: List<ListAccProfRes>
)
