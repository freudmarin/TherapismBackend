package com.marindulja.therapismbackend.repositories

import com.marindulja.therapismbackend.entities.ClientProfile
import com.marindulja.therapismbackend.entities.TherapistProfile
import com.marindulja.therapismbackend.entities.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserProfileRepository : JpaRepository<UserProfile, Long> {

    // Find a Therapist Profile by User ID
    @Query("SELECT t FROM TherapistProfile t WHERE t.user.id = :userId")
    fun findTherapistByUserId(userId: Long): TherapistProfile?

    // Find a Client Profile by User ID
    @Query("SELECT c FROM ClientProfile c WHERE c.user.id = :userId")
    fun findClientByUserId(userId: Long): ClientProfile?

    // Find all Therapist Profiles
    @Query("SELECT t FROM TherapistProfile t")
    fun findAllTherapists(): List<TherapistProfile>

    // Find all Client Profiles
    @Query("SELECT c FROM ClientProfile c")
    fun findAllClients(): List<ClientProfile>
}
