package com.ponhrith.banking_project.controller.response

data class ListProfileRes(
    val id: Long,
    val fullname: String,
    val address: String,
    val email: String,
    val accounts: List<ListAccountRes>
)
