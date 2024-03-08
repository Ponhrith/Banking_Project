package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.service.ProfileService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin("http://localhost:8080/")
class ProfileController(private val profileService: ProfileService) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @CrossOrigin("http://localhost:8084/")
    @PostMapping
    fun registerProfile(@RequestBody registerReq: RegisterReq): RegisterRes {
        return profileService.registerProfile(registerReq)
    }
}