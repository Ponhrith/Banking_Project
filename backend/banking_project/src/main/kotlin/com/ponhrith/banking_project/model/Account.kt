package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "account")
data class Account(
    @Id @GeneratedValue()
    val id: Long,
    @Column(name = "type")
    var type: Long,
    @Column(name = "number")
    var number: Long,
    @Column(name = "balance")
    var balance: Double,
)
