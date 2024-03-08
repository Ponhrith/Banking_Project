package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidEmail
import com.ponhrith.banking_project.common.isValidFullName
import com.ponhrith.banking_project.common.isValidPassword
import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.server.ResponseStatusException

class ProfileService(
    private val profileRepository: ProfileRepository,
    private val accountRepository: AccountRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun registerProfile(registerReq: RegisterReq): RegisterRes {
        validateRegisterRequest(registerReq)

        val account = accountRepository.findByProfileId(registerReq.accountId).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        }

        val passwordEncoder = BCryptPasswordEncoder()
        val encryptedPassword = passwordEncoder.encode(registerReq.password) // Encrypting the password

        // Initialize User
        val profileEntity = Profile(
            fullname = registerReq.fullname,
            address = registerReq.address,
            email = registerReq.email,
            password = encryptedPassword // Assigning encrypted password to the profile

        ).apply {
            this.account = account
        }

        // Save user
        val savedProfile = profileRepository.save(profileEntity)
        log.info("$savedProfile has been added")

        // Return user response
        return RegisterRes(
            id = savedProfile.id,
            fullname = savedProfile.fullname,
            address = savedProfile.address,
            email = savedProfile.email,
            account = AccountRes(savedProfile.account.id, savedProfile.account.accountNumber),
            password = savedProfile.password // Testing purpose
        )
    }
    private fun validateRegisterRequest(registerReq: RegisterReq) {
        registerReq.fullname.isValidFullName()
        registerReq.email.isValidEmail()
        registerReq.password.isValidPassword()
    }
}
