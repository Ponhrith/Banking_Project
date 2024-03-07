package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.model.Profile
import io.jsonwebtoken.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    private val profileService: ProfileService,
) {
    fun createToken(profile: Profile): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(profile.fullname)
            .claim("userId", profile.id)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String): Profile? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val profileId = jwt.claims["profileId"] as Long
            profileService.findById(profileId)
        } catch (e: Exception) {
            null
        }
    }
}