package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "account")
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "type")
    var type: String,
    @Column(name = "number")
    var accountNumber: String,
    @Column(name = "balance")
    var balance: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id") // Foreign key referencing Profile
    var profile: Profile? = null
)



