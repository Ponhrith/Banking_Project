package com.ponhrith.banking_project.controller.response

import com.ponhrith.banking_project.model.Profile
import java.time.LocalDateTime

data class SupportRes(
    val id: Long,
    val profile: Profile? = null,
    var message: String,
    var date: LocalDateTime,
)
