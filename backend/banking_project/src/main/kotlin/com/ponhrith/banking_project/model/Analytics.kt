package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "analytics")
data class Analytics(
    @Id @GeneratedValue
    val id: Long,
){
    @OneToMany
    @JoinColumn(name = "account_id")
    lateinit var account: Account

    @OneToMany
    @JoinColumn(name = "transaction_id")
    lateinit var transaction: Transaction
}
