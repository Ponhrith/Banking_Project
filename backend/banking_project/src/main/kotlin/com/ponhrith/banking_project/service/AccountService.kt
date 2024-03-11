package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidAccountType
import com.ponhrith.banking_project.controller.request.AccountReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.model.Account
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository
) {
    fun createAccount(accountReq: AccountReq): AccountRes {
        // Check if the account type is valid
        accountReq.type.isValidAccountType()

        // Check if the profile exists
        val profileOptional = profileRepository.findById(accountReq.profileId)
        val profile = profileOptional.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile does not exist")
        }

        // Create the account
        val account = Account(
            type = accountReq.type,
            accountNumber = accountReq.number,
            balance = accountReq.balance,
            profile = profile
        )

        // Save the account
        val savedAccount = accountRepository.save(account)

        // Update the profile's list of accounts
        profile.account.add(savedAccount)

        // Return the account response with the profile object
        return AccountRes(
            id = savedAccount.id,
            profile = profile, // Populate the profile object
            type = savedAccount.type,
            number = savedAccount.accountNumber,
            balance = savedAccount.balance
        )
    }
}
