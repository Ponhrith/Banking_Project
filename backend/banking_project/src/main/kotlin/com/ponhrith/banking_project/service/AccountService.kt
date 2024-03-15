package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidAccountType
import com.ponhrith.banking_project.controller.request.AccountReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.model.Account
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository
) {
    @Transactional
    fun createAccount(accountReq: AccountReq): AccountRes {
        // Check if the account type is valid
        accountReq.type.isValidAccountType()

        // Check if the profile exists
        val profileOptional = profileRepository.findById(accountReq.profileId)
        val profile = profileOptional.orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile does not exist")
        }

        // Generate account number based on account type
        val accountNumber = generateAccountNumber(accountReq.type)

        // Create the account with default balance of 0.00
        val account = Account(
            type = accountReq.type,
            accountNumber = accountNumber,
            balance = 0.00, // Default balance
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

    private fun generateAccountNumber(accountType: String): String {
        // Define constants for account numbers
        val savingsPrefix = "0000"
        val jointPrefix = "1111"
        val depositsPrefix = "2222"

        // Generate random number suffix
        var randomNumberSuffix = Random().nextInt(1000000000).toString().padStart(9, '0')

        // Combine prefix and suffix based on account type
        val accountNumber = when (accountType) {
            "Savings" -> savingsPrefix + randomNumberSuffix
            "Joint" -> jointPrefix + randomNumberSuffix
            "Deposits" -> depositsPrefix + randomNumberSuffix
            else -> throw IllegalArgumentException("Invalid account type")
        }
        // Check if the generated account number already exists in the database
        val existingAccount = accountRepository.findByAccountNumber(accountNumber)
        if (existingAccount != null) {
            // If the generated account number already exists, recursively generate a new one
            return generateAccountNumber(accountType)
        }
        return accountNumber
    }

}