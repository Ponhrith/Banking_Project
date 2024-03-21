package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.SupportReq
import com.ponhrith.banking_project.controller.response.SupportRes
import com.ponhrith.banking_project.service.SupportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/support")
@CrossOrigin("http://localhost:8081/")
class SupportController(
    private val supportService: SupportService
) {
    @PostMapping
    fun supportRequest(@RequestBody supportReq: SupportReq): ResponseEntity<SupportRes> {
        val response = supportService.supportRequest(supportReq)
        return ResponseEntity.ok(response)
    }
}