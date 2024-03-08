package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AuthService : UserDetailsService {
    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val profile: Profile? = profileRepository.findByEmail(email)
        if (profile == null) {
            throw UsernameNotFoundException("User not found with email: $email")
        }
        return org.springframework.security.core.userdetails.User(profile.email, profile.password, ArrayList())
    }
}
