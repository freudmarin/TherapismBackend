package com.marindulja.therapismbackend.models


data class RegisterRequest(
    val role: String,
    val email: String,
    val password: String,
)
data class AuthenticationRequest(
    val email: String,
    val password: String,
)

data class AuthenticationResponse(
    val user: UserModel,
    val accessToken: String,
    val refreshToken: String,
)

data class RefreshTokenRequest(
    val token: String
)

data class TokenResponse(
    val token: String
)
