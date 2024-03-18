package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.controller.request.SupportReq
import com.ponhrith.banking_project.controller.response.SupportRes
import com.ponhrith.banking_project.model.Support
import com.ponhrith.banking_project.repository.ProfileRepository
import com.ponhrith.banking_project.repository.SupportRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class SupportService(
    private val supportRepository: SupportRepository,
    private val profileRepository: ProfileRepository
) {
    fun supportRequest(supportReq: SupportReq): SupportRes {
        val profile = profileRepository.findById(supportReq.profileId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found") }

        val currentTime = LocalDateTime.now()

        val support = Support(
            message = supportReq.message,
            date = currentTime,
            profile = profile,
        )

        val savedSupport = supportRepository.save(support)

        return SupportRes(
            id = savedSupport.id,
            profile = profile,
            message = savedSupport.message,
            date = currentTime
        )
    }
}

