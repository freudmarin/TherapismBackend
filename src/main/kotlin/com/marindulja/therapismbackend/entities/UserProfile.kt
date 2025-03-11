package com.marindulja.therapismbackend.entities

import jakarta.persistence.*

@MappedSuperclass
abstract class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    open val profilePictureUrl: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    open val user: User
)

