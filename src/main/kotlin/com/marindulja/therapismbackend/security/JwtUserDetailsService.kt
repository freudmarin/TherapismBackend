package com.marindulja.therapismbackend.security

import com.marindulja.therapismbackend.entities.User
import com.marindulja.therapismbackend.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User $username not found!")

        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            role = user.role
        )
    }
}
