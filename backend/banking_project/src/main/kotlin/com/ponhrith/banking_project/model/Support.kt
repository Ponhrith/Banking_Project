package com.ponhrith.banking_project.model

import jakarta.persistence.*

@Entity
@Table(name = "support")
data class Support(
    @Id @GeneratedValue
    val id: Long,
    @Column(name = "message")
    var message: String,
){
    @ManyToOne
    @JoinColumn(name = "profile_id")
    lateinit var profile: Profile
}
