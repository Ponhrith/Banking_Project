package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.controller.request.DepositReq
import com.ponhrith.banking_project.controller.request.TransferReq
import com.ponhrith.banking_project.controller.response.DepositRes
import com.ponhrith.banking_project.controller.response.ExchangeResponse
import com.ponhrith.banking_project.controller.response.TransferRes
import com.ponhrith.banking_project.model.Transaction
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.TransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val restTemplate: RestTemplate

) {

    private val exchangeRateApiUrl = "https://api.frankfurter.app/latest"

    private fun fetchExchangeRate(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val url = "$exchangeRateApiUrl?amount=$amount&from=$fromCurrency&to=$toCurrency"
        val exchangeResponse: ExchangeResponse? = restTemplate.getForObject(url, ExchangeResponse::class.java)
            ?: throw RuntimeException("Failed to fetch exchange rate")
        return exchangeResponse?.rates?.getOrDefault(toCurrency, 0.0)
            ?: throw RuntimeException("Exchange rate not found")
    }

    fun generateTransactionId(type: String, tableId: Long): String {
        val prefix = when (type) {
            "Withdraw" -> "WD"
            "Transfer" -> "TSF"
            "Credited" -> "CDT"
            "Deposit"  -> "DPS"
            else -> throw IllegalArgumentException("Invalid transaction type")
        }
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return "$prefix$currentDate$tableId"
    }

    @Transactional
    fun depositMoney(depositReq: DepositReq): DepositRes {
        // Check if the source account exists
        val sourceAccount = accountRepository.findByAccountNumber(depositReq.sourceAccount)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found")

        // Update the source account balance
        sourceAccount.balance += depositReq.amount

        // Save the updated source account
        val updatedAccount = accountRepository.save(sourceAccount)

        // Generate transaction ID for deposit
        val transactionId = generateTransactionId("Deposit", sourceAccount.id)

        // Create a transaction record
        val transaction = Transaction(
            date = LocalDateTime.now(),
            amount = depositReq.amount,
            type = "Deposit",
            transactionId = transactionId,  // Assign generated transaction ID
            targetAccount = sourceAccount,
            sourceAccount = sourceAccount // For deposit, target account is the same as the source account
        )

        // Save the transaction record
        val savedTransaction = transactionRepository.save(transaction)

        // Create a response object
        val depositRes = DepositRes(
            id = savedTransaction.id,
            amount = savedTransaction.amount,
            date = savedTransaction.date,
            transactionId = transactionId,
            account = updatedAccount // Return the updated account object with the response
        )

        return depositRes
    }

    @Transactional
    fun transferMoney(transferReq: TransferReq): TransferRes {
        // Check if the source account exists
        val sourceAccount = accountRepository.findByAccountNumber(transferReq.sourceAccount)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found")

        // Check if the target account exists
        val targetAccount = accountRepository.findByAccountNumber(transferReq.targetAccount)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Target account not found")

        // Check if the source account has sufficient balance
        if (sourceAccount.balance < transferReq.amount) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in the source account")
        }

        val sourceCurrency = sourceAccount.currency
        val targetCurrency = targetAccount.currency

        // Fetch the exchange rate and converted amount from the API
        val convertedAmount = fetchExchangeRate(transferReq.amount, sourceCurrency, targetCurrency)

        // Update the balances of source and target accounts
        sourceAccount.balance -= transferReq.amount
        targetAccount.balance += convertedAmount

        // Save the updated source and target accounts
        val updatedSourceAccount = accountRepository.save(sourceAccount)
        val updatedTargetAccount = accountRepository.save(targetAccount)

        // Generate transaction ID for transfer
        val transactionId = generateTransactionId("Transfer", sourceAccount.id)

        // Create a transaction record for the source account
        val sourceTransaction = Transaction(
            date = LocalDateTime.now(),
            amount = -transferReq.amount,
            type = "Transfer",
            transactionId = transactionId,  // Assign generated transaction ID
            targetAccount = targetAccount,
            sourceAccount = sourceAccount
        )

        // Create a transaction record for the target account
        val targetTransaction = Transaction(
            date = LocalDateTime.now(),
            amount = convertedAmount,
            type = "Transfer",
            transactionId = transactionId,  // Assign same transaction ID for both source and target
            targetAccount = targetAccount,
            sourceAccount = sourceAccount
        )

        // Save the transaction records
        val savedSourceTransaction = transactionRepository.save(sourceTransaction)
        val savedTargetTransaction = transactionRepository.save(targetTransaction)

        // Create and return the response object
        return TransferRes(
            id = savedSourceTransaction.id,
            sourceAccount = sourceAccount.accountNumber,
            targetAccount = targetAccount.accountNumber,
            type = "Transfer",
            amount = transferReq.amount,
            date = LocalDateTime.now(),
            transactionId = transactionId,
            account = updatedSourceAccount // Return the updated source account with the response
        )
    }

    @Transactional
    fun deleteTransaction(transactionId: Long) {
        // Check if the transaction exists
        val transactionOptional = transactionRepository.findById(transactionId)
        val transaction = transactionOptional.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        }

        // Delete the transaction
        transactionRepository.delete(transaction)
    }
}
