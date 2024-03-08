package com.ponhrith.banking_project.repository

import com.ponhrith.banking_project.model.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    override fun findById(id: Long): Optional<Profile>
    fun findByEmail(email: String): Profile?
}