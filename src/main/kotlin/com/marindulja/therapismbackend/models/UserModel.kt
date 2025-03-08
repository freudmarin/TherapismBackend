package com.marindulja.therapismbackend.models

data class UserModel(
    val id: Long,
    val email: String,
    val role: RoleType
)
