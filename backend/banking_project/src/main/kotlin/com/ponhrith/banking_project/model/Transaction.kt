package com.ponhrith.banking_project.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "transaction")
data class Transaction(
    @Id @GeneratedValue
    val id: Long,
    @Column(name = "date")
    var date: LocalDate,
    @Column(name = "amount")
    var amount: Double,
    @Column(name = "target_account_id")
    val target_account_id: Long,
    @Column(name ="source_account_id")
    val source_account_id: Long,
){
    @ManyToOne
    @JoinColumn(name = "profile_id")
    lateinit var profile: Profile
}