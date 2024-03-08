package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "profile")
data class Profile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "fullname")
    var fullname: String,
    @Column(name = "address")
    var address: String,
    @Column(name ="email")
    var email: String,
    @Column(name ="password")
    var password: String,

    // One-to-many relationship with Account
    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var account: MutableList<Account> = mutableListOf()
)