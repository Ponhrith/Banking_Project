package com.ponhrith.banking_project.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class JwtConfig {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expirationMs}")
    private var jwtExpirationMs: Long = 0

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(userDetailsService, jwtSecret, jwtExpirationMs)
    }
}