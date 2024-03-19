package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.request.UpdateProfileReq
import com.ponhrith.banking_project.controller.response.ListProfileRes
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.service.ProfileService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin("http://localhost:8080/")
class ProfileController(private val profileService: ProfileService) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun registerProfile(@RequestBody registerReq: RegisterReq): RegisterRes {
        return profileService.registerProfile(registerReq)
    }
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

    @PutMapping("/{profileId}")
    fun updateProfile(@PathVariable profileId: Long, @RequestBody updateProfileReq: UpdateProfileReq)
    : ResponseEntity<RegisterRes> {
        val updatedProfile = profileService.updateProfile(profileId, updateProfileReq)
        return ResponseEntity.ok(updatedProfile)
    }

    @DeleteMapping("/{profileId}")
    fun deleteProfile(@PathVariable profileId: Long): ResponseEntity<String> {
        try {
            profileService.deleteProfile(profileId)
            return ResponseEntity.ok("Profile with ID $profileId has been deleted")
        } catch (ex: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete profile")
        }
    }

    @GetMapping("/check-email")
    fun checkEmailExists(@RequestParam email: String): Boolean {
        return profileService.checkEmailExists(email)
    }
}