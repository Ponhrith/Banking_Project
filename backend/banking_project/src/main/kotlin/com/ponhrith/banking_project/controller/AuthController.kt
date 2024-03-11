package com.ponhrith.banking_project.controller

import com.ponhrith.banking_project.controller.request.LoginReq
import com.ponhrith.banking_project.controller.response.AccountRes
import com.ponhrith.banking_project.controller.response.LoginRes
import com.ponhrith.banking_project.model.Profile
import com.ponhrith.banking_project.repository.ProfileRepository
import com.ponhrith.banking_project.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


//@RestController
//@RequestMapping("/api/v1/auth")
//@CrossOrigin("http://localhost:8080/")
//class AuthController(
//    private val profileRepository: ProfileRepository
//) {
//
//    @Autowired
//    private lateinit var authenticationManager: AuthenticationManager
//
//    @Autowired
//    private lateinit var passwordEncoder: PasswordEncoder
//
//    @Autowired
//    private lateinit var userDetailsService: AuthService
//
//    @Autowired
//    private lateinit var jwtUtil: JwtUtil
//
//
//    @CrossOrigin("http://localhost:8080/")
//    @PostMapping("/login")
//    fun authenticateProfile(@RequestBody loginReq: LoginReq): ResponseEntity<*> {
//        val authentication = authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(
//                loginReq.email,
//                loginReq.password
//            )
//        )
//        SecurityContextHolder.getContext().authentication = authentication
//        val userDetails: UserDetails = userDetailsService.loadUserByUsername(loginReq.email)!!
//        val jwt = jwtUtil.generateToken(userDetails)
//
//        // Retrieve the profile associated with the authenticated user
//        val profile: Profile = profileRepository.findByEmail(loginReq.email)
//            ?: throw UsernameNotFoundException("User not found with email: ${loginReq.email}")
//
//        // Access the account associated with the profile
//        val accountRes: AccountRes? = profile.account?.let { AccountRes(
//            it.first().id,
//            it.first().type,
//            it.first().accountNumber,
//            it.first().balance)
//        }
//
//        // Construct the login response object
//        val responseBody = LoginRes(account = accountRes)
//
//        val responseHeaders = HttpHeaders()
//        responseHeaders.add("Authorization", jwt)
//
//        return ResponseEntity.ok().headers(responseHeaders).body(responseBody)
//    }
//}