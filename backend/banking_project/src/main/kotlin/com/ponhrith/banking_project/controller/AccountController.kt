package com.ponhrith.banking_project.controller


import com.ponhrith.banking_project.controller.request.AccountReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.controller.response.ListAccountRes
import com.ponhrith.banking_project.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("http://localhost:8080/")
class AccountController(private val accountService: AccountService) {

    @GetMapping
    fun listAccounts(): List<ListAccountRes> {
        return accountService.listAccounts()
    }
    @GetMapping("/{accountNumber}")
    fun getAccountByAccountNumber(@PathVariable accountNumber: String): ListAccountRes? {
        return accountService.getAccountByAccountNumber(accountNumber)
    }
    @PostMapping
    fun createAccount(@RequestBody accountReq: AccountReq): AccountRes {
        return accountService.createAccount(accountReq)
    }
    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: Long): ResponseEntity<String> {
        try {
            accountService.deleteAccount(accountId)
            return ResponseEntity.ok("Account with ID $accountId has been deleted")
        } catch (ex: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found", ex)
        }
    }

}
