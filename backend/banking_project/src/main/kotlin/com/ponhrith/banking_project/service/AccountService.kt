package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidAccountType
import com.ponhrith.banking_project.common.isValidCurrencyType
import com.ponhrith.banking_project.controller.request.AccountReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.controller.response.ListAccProfRes
import com.ponhrith.banking_project.controller.response.ListAccountRes
import com.ponhrith.banking_project.model.Account
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository
import org.slf4j.LoggerFactory
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

    private val log = LoggerFactory.getLogger(this::class.java)

    fun listAccounts(): List<ListAccountRes> {
        val accounts = accountRepository.findAll() // Fetch all accounts

        return accounts.map { account ->
            ListAccountRes(
                id = account.id,
                type = account.type,
                number = account.accountNumber,
                balance = account.balance,
                currency = account.currency,
                profiles = account.profile?.let { listOf(
                    ListAccProfRes(
                        id = it.id,
                        fullname = it.fullname,
                        address = it.address,
                        email = it.email
                    )
                ) } ?: emptyList()
            )
        }
    }

    @Transactional
    fun createAccount(accountReq: AccountReq): AccountRes {
        // Check if the field type is valid
        accountReq.type.isValidAccountType()
        accountReq.currency.isValidCurrencyType()

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
            profile = profile,
            currency = accountReq.currency
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
            balance = savedAccount.balance,
            currency = savedAccount.currency
        )
    }

    fun getAccountByAccountNumber(accountNumber: String): ListAccountRes? {
        val account = accountRepository.findByAccountNumber(accountNumber)
        return account?.let {
            ListAccountRes(
                id = it.id,
                type = it.type,
                number = it.accountNumber,
                balance = it.balance,
                currency = it.currency,
                profiles = it.profile?.let { profile ->
                    listOf(ListAccProfRes(
                        id = profile.id,
                        fullname = profile.fullname,
                        address = profile.address,
                        email = profile.email
                    ))
                } ?: emptyList()
            )
        }
    }

    @Transactional
    fun deleteAccount(accountId: Long) {
        // Check if the account exists
        val accountOptional = accountRepository.findById(accountId)
        val account = accountOptional.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        }

        // Delete the account
        accountRepository.delete(account)

        // Optional: Remove the account from the associated profile's account list
        val profile = account.profile
        profile?.let {
            it.account.removeIf { acc -> acc.id == accountId }
            profileRepository.save(it)
        }

        // Log the deletion
        log.info("Account with ID $accountId has been deleted")
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