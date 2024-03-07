package com.ponhrith.banking_project.repository

import com.ponhrith.banking_project.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByProfileId(profileId: Long): List<Transaction>
}