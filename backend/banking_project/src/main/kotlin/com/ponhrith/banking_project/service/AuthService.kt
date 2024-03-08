package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class AuthService : UserDetailsService {
    @Autowired
    var jwtUserRepository: ProfileRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? {
        val user: UserAuth = jwtUserRepository!!.findUserByEmail(email)
        return org.springframework.security.core.userdetails.User(user.email, user.password, ArrayList())
    }
}