package com.ponhrith.banking_project.controller.request

import java.time.LocalDateTime

data class SupportReq(
    val profileId: Long,
    val message: String,
)
