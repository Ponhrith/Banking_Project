package com.ponhrith.banking_project.model

import javax.persistence.*

@Entity
@Table(name = "support")
data class Support(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "message")
    var message: String,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    lateinit var profile: Profile
}

