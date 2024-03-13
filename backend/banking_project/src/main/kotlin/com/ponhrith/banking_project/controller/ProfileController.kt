package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.response.ListProfileRes
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.service.ProfileService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin("http://127.0.0.1:5500")
class ProfileController(private val profileService: ProfileService) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun listProfiles(): List<ListProfileRes> {
        return profileService.listProfiles()
    }

    @GetMapping("/{profileId}")
    fun showProfile(@PathVariable profileId: Long): ResponseEntity<ListProfileRes> {
        val profile = profileService.showProfile(profileId)
        return if (profile != null) {
            ResponseEntity.ok(profile)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun registerProfile(@RequestBody registerReq: RegisterReq): RegisterRes {
        return profileService.registerProfile(registerReq)
    }

    @GetMapping("/check-email")
    fun checkEmailExists(@RequestParam email: String): Boolean {
        return profileService.checkEmailExists(email)
    }
}