package com.ponhrith.banking_project.model

import jakarta.persistence.*
@Entity
@Table(name = "analytics")
data class Analytics(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {
    @ManyToMany
    @JoinTable(
        name = "analytics_account",
        joinColumns = [JoinColumn(name = "analytics_id")],
        inverseJoinColumns = [JoinColumn(name = "account_id")]
    )
    var account: List<Account> = ArrayList()

    @ManyToMany
    @JoinTable(
        name = "analytics_transaction",
        joinColumns = [JoinColumn(name = "analytics_id")],
        inverseJoinColumns = [JoinColumn(name = "transaction_id")]
    )
    var transaction: List<Transaction> = ArrayList()
}

