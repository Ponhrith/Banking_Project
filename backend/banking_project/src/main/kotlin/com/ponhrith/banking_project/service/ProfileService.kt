package com.ponhrith.banking_project.service

import com.ponhrith.banking_project.controller.request.RegisterReq
import com.ponhrith.banking_project.controller.response.RegisterRes
import com.ponhrith.banking_project.repository.AccountRepository
import com.ponhrith.banking_project.repository.ProfileRepository

class ProfileService(
    private val profileRepository: ProfileRepository,
    private val accountRepository: AccountRepository
) {

    fun registerProfile(registerReq: RegisterReq): RegisterRes {
        validateUserRequest(userReq)

        // Get Department
        val department = departmentRepository.findById(userReq.departmentId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found")
        }

        val passwordEncoder = BCryptPasswordEncoder()
        val generator = PasswordGenerator()
        val generatedPassword = generator.password(8)
        val encryptedPassword = passwordEncoder.encode(generatedPassword)
        log.info("The generated password is: $generatedPassword")

        // Initialize User
        val userEntity = User(
            username = userReq.username,
            gender = userReq.gender,
            role = userReq.role,
            password = encryptedPassword,
            email = userReq.email
        ).apply {
            this.department = department
        }

        // Save user
        val savedUser = userRepository.save(userEntity)
        log.info("$savedUser has been added")


        // Return user response
        return UserRes(
            id = savedUser.id,
            username = savedUser.username,
            gender = savedUser.gender,
            role = savedUser.role,
            department = DepartmentRes(savedUser.department.id, savedUser.department.name),
            password = generatedPassword,
            email = savedUser.email
        )
    }


}