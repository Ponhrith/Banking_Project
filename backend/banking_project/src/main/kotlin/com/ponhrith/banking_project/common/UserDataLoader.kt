package com.ponhrith.banking_project.common

import com.ponhrith.banking_project.model.Account
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserDataLoader : CommandLineRunner {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Autowired
    private lateinit var accountRepository: AccountRepository

    override fun run(vararg args: String?) {
        seedUsers()
    }

    private fun seedUsers() {
        val passwordEncoder = BCryptPasswordEncoder()

        if (profileRepository.count() == 0L) {
            val defaultProfile = Profile(
                fullname = "Default User",
                address = "123 Default Street",
                email = "defaultuser@gmail.com",
                password = passwordEncoder.encode("defaultpassword")
            )

            val defaultAccount = Account(
                type = "Savings",
                accountNumber = "9999999999",
                balance = 0.0,
                profile = defaultProfile
            )

            defaultProfile.account.add(defaultAccount)
            profileRepository.save(defaultProfile)
        }
    }
}
