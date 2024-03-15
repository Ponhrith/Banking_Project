package com.ponhrith.banking_project.controller.request


data class AccountReq(
    val profileId: Long,
    val type: String,
    var currency: String,
)
