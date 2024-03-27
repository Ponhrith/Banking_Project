package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.controller.request.LoginReq
import com.ponhrith.banking_project.controller.response.LoginRes
import com.ponhrith.banking_project.repository.ProfileRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class LoginService(
    private val profileRepository: ProfileRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long
) {
    // Generate a secure key for HS512 algorithm
    private val jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    fun login(loginReq: LoginReq): ResponseEntity<LoginRes> {
        // Check if email and password are empty
        if (loginReq.email.isEmpty() || loginReq.password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginRes(success = false, message = "Email and password cannot be empty"))
        }
        // Find the user by email
        val profile = profileRepository.findByEmail(loginReq.email)
        if (profile == null || !passwordEncoder.matches(loginReq.password, profile.password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginRes(success = false, message = "Invalid email or password"))
        }


        val token = generateToken(profile.id.toString())


        // Return success response with profile object
        return ResponseEntity.ok(LoginRes(
            success = true,
            message = "Login successful",
            profile = profile,
            token = token
        ))
    }





    private fun generateToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(jwtSecretKey)
            .compact()
    }
}
