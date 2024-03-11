package com.ponhrith.banking_project.model

import javax.persistence.*
import java.time.LocalDate



@Entity
@Table(name = "transaction")
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "date")
    var date: LocalDate,
    @Column(name = "amount")
    var amount: Double,
    @Column(name = "type")
    var type: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    var targetAccount: Account,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    var sourceAccount: Account
)

