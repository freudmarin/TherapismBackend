package com.marindulja.therapismbackend.repositories

import com.marindulja.therapismbackend.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
