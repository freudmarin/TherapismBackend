package com.marindulja.therapismbackend.services

import com.marindulja.therapismbackend.entities.User
import com.marindulja.therapismbackend.extensions.toEntity
import com.marindulja.therapismbackend.extensions.toModel
import com.marindulja.therapismbackend.models.AuthenticationRequest
import com.marindulja.therapismbackend.models.AuthenticationResponse
import com.marindulja.therapismbackend.models.RegisterRequest
import com.marindulja.therapismbackend.repositories.RefreshTokenRepository
import com.marindulja.therapismbackend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${jwt.accessTokenExpiration}") private val accessTokenExpiration: Long = 0,
    @Value("\${jwt.refreshTokenExpiration}") private val refreshTokenExpiration: Long = 0
) {

    fun register(request: RegisterRequest): AuthenticationResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw RuntimeException("Email already exists")
        }

        val user = request.toEntity(passwordEncoder.encode(request.password))
        userRepository.save(user)

        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        return AuthenticationResponse(user = user.toModel(), accessToken = accessToken, refreshToken = refreshToken)
    }


    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)

        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(
            user = (user as User).toModel(),
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String): String {
        val username = tokenService.extractUsername(refreshToken)

        return username.let { user ->
            val currentUserDetails = userDetailsService.loadUserByUsername(user)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)

            if (currentUserDetails.username == refreshTokenUserDetails?.username)
                createAccessToken(currentUserDetails)
            else
                throw AuthenticationServiceException("Invalid refresh token")
        }
    }

    private fun createAccessToken(user: UserDetails): String {
        return tokenService.generateToken(
            subject = user.username,
            expiration = Date(System.currentTimeMillis() + accessTokenExpiration)
        )
    }

    private fun createRefreshToken(user: UserDetails) = tokenService.generateToken(
        subject = user.username,
        expiration = Date(System.currentTimeMillis() + refreshTokenExpiration)
    )
}
