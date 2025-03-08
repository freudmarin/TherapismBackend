package com.marindulja.therapismbackend.controllers

import com.marindulja.therapismbackend.models.*
import com.marindulja.therapismbackend.services.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody authRequest: RegisterRequest
    ): AuthenticationResponse =
        authenticationService.register(authRequest)

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse = TokenResponse(token = authenticationService.refreshAccessToken(request.token))
}
