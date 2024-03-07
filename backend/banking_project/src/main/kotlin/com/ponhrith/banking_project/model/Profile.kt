package com.ponhrith.banking_project.model

import com.nimbusds.openid.connect.sdk.claims.Address
import jakarta.persistence.*

@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_seq")
    @SequenceGenerator(name = "profile_id_seq", sequenceName = "profile_id_seq")
    val id: Long = 0,
    @Column(name = "fullname")
    var fullname: String,
    @Column(name = "address")
    var address: String,
    @Column(name ="email")
    var email: String
){
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    lateinit var account: Account
}
