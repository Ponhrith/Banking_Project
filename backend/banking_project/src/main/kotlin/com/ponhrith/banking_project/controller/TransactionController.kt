package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.DepositReq
import com.ponhrith.banking_project.controller.request.TransferReq
import com.ponhrith.banking_project.controller.response.DepositRes
import com.ponhrith.banking_project.controller.response.TransferRes
import com.ponhrith.banking_project.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/transaction")
@CrossOrigin("http://127.0.0.1:5500")
class TransactionController(
    private val transactionService: TransactionService
) {
    @PostMapping("/deposit")
    fun depositMoney(@RequestBody depositReq: DepositReq): DepositRes {
        return try {
            transactionService.depositMoney(depositReq)
        } catch (e: Exception) {
            // Handle any exceptions and return appropriate response
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to deposit money: ${e.message}")
        }
    }

    @PostMapping("/transfer")
    fun transferMoney(@RequestBody transferReq: TransferReq): TransferRes {
        return transactionService.transferMoney(transferReq)
    }

    @DeleteMapping("/{transactionId}")
    fun deleteTransaction(@PathVariable transactionId: Long): ResponseEntity<String> {
        try{
            transactionService.deleteTransaction(transactionId)
            return ResponseEntity.ok("Transaction with ID $transactionId has been deleted")
        }   catch (ex: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found", ex)
        }

    }

}