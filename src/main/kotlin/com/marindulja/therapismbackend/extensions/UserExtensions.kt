package com.marindulja.therapismbackend.extensions

import com.marindulja.therapismbackend.entities.User
import com.marindulja.therapismbackend.models.RegisterRequest
import com.marindulja.therapismbackend.models.RoleType
import com.marindulja.therapismbackend.models.UserModel

// Convert User Entity to UserModel
fun User.toModel(): UserModel =
    UserModel(
        id = this.id,
        email = this.email,
        role = this.role
    )

// Convert RegisterRequest to New User Entity
fun RegisterRequest.toEntity(encodedPassword: String) =
     User(
        email = this.email,
        password = encodedPassword,
        role = this.role.toRoleType()
    )

fun String.toRoleType(): RoleType {
    return when (this.lowercase()) {
        "user" -> RoleType.CLIENT
        "therapist" -> RoleType.THERAPIST
        else -> RoleType.valueOf(this.uppercase())
    }
}
