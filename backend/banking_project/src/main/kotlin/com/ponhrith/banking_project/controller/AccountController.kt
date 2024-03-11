package com.ponhrith.banking_project.controller


import com.ponhrith.banking_project.controller.request.AccountReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("http://localhost:8080/")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody accountReq: AccountReq): AccountRes {
        return accountService.createAccount(accountReq)
    }

}
