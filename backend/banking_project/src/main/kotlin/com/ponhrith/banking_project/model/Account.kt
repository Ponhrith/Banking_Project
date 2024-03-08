package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "account")
data class Account(
    @Id @GeneratedValue()
    val id: Long,
    @Column(name = "type")
    var type: String,
    @Column(name = "number")
    var accountNumber: String,
    @Column(name = "balance")
    var balance: Double,
)
