package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.LoginReq
import com.ponhrith.banking_project.controller.response.LoginRes
import com.ponhrith.banking_project.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:8081/")
class LoginController(
    private val loginService: LoginService
) {
    @PostMapping("/login")
    fun login(@RequestBody loginReq: LoginReq): ResponseEntity<ResponseEntity<LoginRes>> {
        val loginRes = loginService.login(loginReq)
        return ResponseEntity.ok(loginRes)
    }
}
