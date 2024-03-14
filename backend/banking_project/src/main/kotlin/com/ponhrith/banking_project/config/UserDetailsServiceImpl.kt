package com.ponhrith.banking_project.config

import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val profileRepository: ProfileRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        // Load user details from the database based on the email
        val profile = profileRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User with email $email not found")

        return org.springframework.security.core.userdetails.User
            .withUsername(profile.email)
            .password(profile.password)
            .roles("USER")
            .build()
    }
}