package com.ponhrith.banking_project.repository

import com.ponhrith.banking_project.model.Support
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupportRepository: JpaRepository<Support, Long>{
    fun findByProfileId(profileId: Long): List<Support>
}