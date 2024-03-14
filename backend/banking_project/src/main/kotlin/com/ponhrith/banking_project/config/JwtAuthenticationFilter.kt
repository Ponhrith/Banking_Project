package com.ponhrith.banking_project.config

import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtSecret: String,
    private val jwtExpirationMs: Long
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val token = extractJwtToken(request)
            val username = extractUsername(token)
            val userDetails = userDetailsService.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        } catch (e: Exception) {
            throw BadCredentialsException("Failed to authenticate token", e)
        }
    }

    private fun extractJwtToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            throw BadCredentialsException("JWT token is missing or invalid")
        }
    }

    private fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
        super.unsuccessfulAuthentication(request, response, failed)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}