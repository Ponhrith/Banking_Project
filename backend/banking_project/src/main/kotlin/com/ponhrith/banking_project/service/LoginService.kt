package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.controller.request.LoginReq
import com.ponhrith.banking_project.controller.response.LoginRes
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LoginService(
    private val profileRepository: ProfileRepository
) {
    fun login(loginReq: LoginReq): LoginRes {
        // Check if email and password are empty
        if (loginReq.email.isEmpty() || loginReq.password.isEmpty()) {
            return LoginRes(success = false, message = "Email and password cannot be empty")
        }

        // Find the user by email
        val profile = profileRepository.findByEmail(loginReq.email)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password")

        // Verify the password
        if (profile.password != loginReq.password) {
            return LoginRes(success = false, message = "Invalid email or password")
        }

        // Return success response with profile object
        return LoginRes(
            success = true,
            message = "Login successful",
            profile = profile)
    }
}
