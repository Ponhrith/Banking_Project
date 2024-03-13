package com.ponhrith.banking_project.repository

import com.ponhrith.banking_project.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByProfileId(profileId: Long): List<Account>
    fun deleteByProfileId(profileId: Long)
}