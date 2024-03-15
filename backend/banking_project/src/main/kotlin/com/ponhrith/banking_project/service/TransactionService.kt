package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.common.isValidTransactionType
import com.ponhrith.banking_project.controller.request.DepositReq
import com.ponhrith.banking_project.controller.response.DepositRes
import com.ponhrith.banking_project.model.Transaction
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.TransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun depositMoney(depositReq: DepositReq): DepositRes {
        // Check if the source account exists
        val sourceAccount = accountRepository.findByAccountNumber(depositReq.sourceAccount)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found")

        // Update the source account balance
        sourceAccount.balance += depositReq.amount

        // Save the updated source account
        val updatedAccount = accountRepository.save(sourceAccount)

        // Create a transaction record
        val transaction = Transaction(
            date = LocalDateTime.now(),
            amount = depositReq.amount,
            type = "Deposit",
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
            account = updatedAccount // Return the updated account object with the response
        )

        return depositRes
    }
}
