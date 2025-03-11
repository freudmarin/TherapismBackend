package com.marindulja.therapismbackend.services

import com.marindulja.therapismbackend.entities.User
import com.marindulja.therapismbackend.exceptions.EntityNotFoundException
import com.marindulja.therapismbackend.exceptions.UnauthorizedException
import com.marindulja.therapismbackend.extensions.toEntity
import com.marindulja.therapismbackend.extensions.toModel
import com.marindulja.therapismbackend.models.*
import com.marindulja.therapismbackend.repositories.UserProfileRepository
import com.marindulja.therapismbackend.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserProfileService(
    private val profileRepository: UserProfileRepository,
    private val userRepository: UserRepository
) {
    /**
     * Create a Therapist Profile
     */
    @Transactional
    fun createTherapistProfile(request: TherapistProfileRequest, userId: Long): TherapistProfileModel {
        val existingUser = findExistingUser(userId)
        validateUserIsClient(existingUser)
        val therapistProfile = request.toEntity(existingUser) // Convert the request to an entity
        val savedProfile = profileRepository.save(therapistProfile) // Save it
        return savedProfile.toModel(existingUser) // Return the saved profile as a model
    }

    /**
     * Create a Client Profile
     */
    @Transactional
    fun createClientProfile(request: ClientProfileRequest, userId: Long): ClientProfileModel {
        val existingUser = findExistingUser(userId)
        validateUserIsClient(existingUser)
        val clientProfile = request.toEntity(existingUser) // Convert the request to an entity
        val savedProfile = profileRepository.save(clientProfile) // Save it
        return savedProfile.toModel()
    }

    /**
     * Fetch a Therapist Profile by User ID
     */
    @Transactional(readOnly = true)
    fun getTherapistProfileByUserId(userId: Long): TherapistProfileModel? {
        val profile = profileRepository.findTherapistByUserId(userId)
        return profile?.toModel(profile.user) // Convert to model if profile exists
    }

    /**
     * Fetch a Client Profile by User ID
     */
    @Transactional(readOnly = true)
    fun getClientProfileByUserId(userId: Long): ClientProfileModel? {
        val profile = profileRepository.findClientByUserId(userId)
        return profile?.toModel() // Convert to model if profile exists
    }

    /**
     * Fetch All Therapist Profiles
     */
    @Transactional(readOnly = true)
    fun getAllTherapists(): List<TherapistProfileModel> {
        val therapists = profileRepository.findAllTherapists()
        return therapists.map { it.toModel(it.user) } // Map all entities to models
    }

    /**
     * Fetch All Client Profiles
     */
    @Transactional(readOnly = true)
    fun getAllClients(): List<ClientProfileModel> {
        val clients = profileRepository.findAllClients()
        return clients.map { it.toModel() } // Map all entities to models
    }

    /**
     * Update Therapist Profile
     */
    @Transactional
    fun updateTherapistProfile(userId: Long, request: TherapistProfileRequest): TherapistProfileModel {
        // Locate the existing profile by User ID
        val existingProfile = profileRepository.findTherapistByUserId(userId)
            ?: throw EntityNotFoundException("Therapist profile not found for user ID $userId")

        validateUserIsTherapist(existingProfile.user)
        // Update the profile entity
        val updatedProfile = existingProfile.copy(
            fullName = request.fullName,
            title = request.title,
            bio = request.bio,
            experienceYears = request.experienceYears,
            specialties = request.specialties,
            credentials = request.credentials,
            profilePictureUrl = request.profilePictureUrl
        )

        val savedProfile = profileRepository.save(updatedProfile)
        return savedProfile.toModel(savedProfile.user)
    }

    /**
     * Update Client Profile
     */
    @Transactional
    fun updateClientProfile(userId: Long, request: ClientProfileRequest): ClientProfileModel {
        // Locate the existing profile by User ID
        val existingProfile = profileRepository.findClientByUserId(userId)
            ?: throw EntityNotFoundException("Client profile not found for user ID $userId")

        validateUserIsClient(existingProfile.user)
        // Update the profile entity
        val updatedProfile = existingProfile.copy(
            nickname = request.nickname,
            age = request.age,
            location = request.location,
            therapyGoals = request.therapyGoals,
            preferredCommunication = request.preferredCommunication,
            profilePictureUrl = request.profilePictureUrl
        )

        val savedProfile = profileRepository.save(updatedProfile)
        return savedProfile.toModel()
    }

    /**
     * Delete a Therapist Profile by User ID
     */
    @Transactional
    fun deleteTherapistProfile(userId: Long) {
        val profile = profileRepository.findTherapistByUserId(userId)
            ?: throw EntityNotFoundException("Therapist profile not found for user ID $userId")
        profileRepository.delete(profile)
    }

    /**
     * Delete a Client Profile by User ID
     */
    @Transactional
    fun deleteClientProfile(userId: Long) {
        val profile = profileRepository.findClientByUserId(userId)
            ?: throw EntityNotFoundException("Client profile not found for user ID $userId")
        profileRepository.delete(profile)
    }

    private fun findExistingUser(userId: Long): User =
        userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found") }

    private fun validateUserIsClient(user: User) {
        if (user.role != RoleType.CLIENT) {
            throw UnauthorizedException("User is not a client")
        }
    }

    private fun validateUserIsTherapist(user: User) {
        if (user.role != RoleType.THERAPIST) {
            throw UnauthorizedException("User is not a client")
        }
    }

}
