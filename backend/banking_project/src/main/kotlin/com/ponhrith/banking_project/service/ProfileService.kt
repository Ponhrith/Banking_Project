package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidEmail
import com.ponhrith.banking_project.common.isValidFullName
import com.ponhrith.banking_project.common.isValidPassword
import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.controller.response.ListAccountRes
import com.ponhrith.banking_project.controller.response.ListProfileRes
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val accountRepository: AccountRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)


    fun listProfiles(): List<ListProfileRes> {
        val profiles = profileRepository.findAll()
        return profiles.map { profile ->
            val accounts = accountRepository.findByProfileId(profile.id)
            ListProfileRes(
                id = profile.id,
                fullname = profile.fullname,
                address = profile.address,
                email = profile.email,
                accounts = accounts.map { account ->
                    ListAccountRes(
                        id = account.id,
                        type = account.type,
                        number = account.accountNumber,
                        balance = account.balance
                    )
                }
            )
        }
    }

    fun registerProfile(registerReq: RegisterReq): RegisterRes {
        validateRegisterRequest(registerReq)

        // Check if email already exists
        if (profileRepository.findByEmail(registerReq.email) != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists")
        }

        val passwordEncoder = BCryptPasswordEncoder()
        val encryptedPassword = passwordEncoder.encode(registerReq.password) // Encrypting the password

        // Initialize User
        val profileEntity = Profile(
            fullname = registerReq.fullname,
            address = registerReq.address,
            email = registerReq.email,
            password = encryptedPassword // Assigning encrypted password to the profile
        )

        try {
            // Save user
            val savedProfile = profileRepository.save(profileEntity)
            log.info("$savedProfile has been added")

            // Return user response
            return RegisterRes(
                id = savedProfile.id,
                fullname = savedProfile.fullname,
                address = savedProfile.address,
                email = savedProfile.email,
                password = savedProfile.password // Testing purpose
            )
        } catch (e: DataIntegrityViolationException) {
            // Handle any database integrity violation exceptions
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while registering profile",
                e
            )
        }
    }

    fun checkEmailExists(email: String): Boolean {
        return profileRepository.findByEmail(email) != null
    }

    private fun validateRegisterRequest(registerReq: RegisterReq) {
        registerReq.fullname.isValidFullName()
        registerReq.email.isValidEmail()
        registerReq.password.isValidPassword()
    }
}
