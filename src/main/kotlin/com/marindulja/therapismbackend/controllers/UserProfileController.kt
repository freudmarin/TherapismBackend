package com.marindulja.therapismbackend.controllers

import com.marindulja.therapismbackend.models.ClientProfileModel
import com.marindulja.therapismbackend.models.ClientProfileRequest
import com.marindulja.therapismbackend.models.TherapistProfileModel
import com.marindulja.therapismbackend.models.TherapistProfileRequest
import com.marindulja.therapismbackend.services.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profiles")
class UserProfileController(
    private val userProfileService: UserProfileService
) {

    /**
     * Create a Therapist Profile
     */
    @PostMapping("/therapists")
    @PreAuthorize("hasRole('THERAPIST')")
    fun createTherapistProfile(
        @RequestBody request: TherapistProfileRequest,
        @RequestParam userId: Long
    ): ResponseEntity<TherapistProfileModel> {
        val profile = userProfileService.createTherapistProfile(request, userId)
        return ResponseEntity.ok(profile)
    }

    /**
     * Create a Client Profile
     */
    @PostMapping("/clients")
    @PreAuthorize("hasRole('CLIENT')")
    fun createClientProfile(
        @RequestBody request: ClientProfileRequest,
        @RequestParam userId: Long
    ): ResponseEntity<ClientProfileModel> {
        val profile = userProfileService.createClientProfile(request, userId)
        return ResponseEntity.ok(profile)
    }

    /**
     * Get Therapist Profile by User ID
     */
    @GetMapping("/therapists/{userId}")
    @PreAuthorize("hasRole('THERAPIST')")
    fun getTherapistProfile(
        @PathVariable userId: Long
    ): ResponseEntity<TherapistProfileModel> {
        val profile = userProfileService.getTherapistProfileByUserId(userId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(profile)
    }

    /**
     * Get Client Profile by User ID
     */
    @GetMapping("/clients/{userId}")
    @PreAuthorize("hasRole('CLIENT')")
    fun getClientProfile(
        @PathVariable userId: Long
    ): ResponseEntity<ClientProfileModel> {
        val profile = userProfileService.getClientProfileByUserId(userId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(profile)
    }

    /**
     * Get All Therapist Profiles
     */
    @GetMapping("/therapists")
    @PreAuthorize("hasRole('CLIENT')")
    fun getAllTherapists(): ResponseEntity<List<TherapistProfileModel>> {
        val profiles = userProfileService.getAllTherapists()
        return ResponseEntity.ok(profiles)
    }

    /**
     * Get All Client Profiles
     */
    @GetMapping("/clients")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllClients(): ResponseEntity<List<ClientProfileModel>> {
        val profiles = userProfileService.getAllClients()
        return ResponseEntity.ok(profiles)
    }

    /**
     * Update Therapist Profile by User ID
     */
    @PutMapping("/therapists/{userId}")
    @PreAuthorize("hasRole('THERAPIST')")
    fun updateTherapistProfile(
        @PathVariable userId: Long,
        @RequestBody request: TherapistProfileRequest
    ): ResponseEntity<TherapistProfileModel> {
        val updatedProfile = userProfileService.updateTherapistProfile(userId, request)
        return ResponseEntity.ok(updatedProfile)
    }

    /**
     * Update Client Profile by User ID
     */
    @PutMapping("/clients/{userId}")
    @PreAuthorize("hasRole('CLIENT')")
    fun updateClientProfile(
        @PathVariable userId: Long,
        @RequestBody request: ClientProfileRequest
    ): ResponseEntity<ClientProfileModel> {
        val updatedProfile = userProfileService.updateClientProfile(userId, request)
        return ResponseEntity.ok(updatedProfile)
    }

    /**
     * Delete a Therapist Profile by User ID
     */
    @DeleteMapping("/therapists/{userId}")
    @PreAuthorize("hasRole('THERAPIST')")
    fun deleteTherapistProfile(
        @PathVariable userId: Long
    ): ResponseEntity<Void> {
        userProfileService.deleteTherapistProfile(userId)
        return ResponseEntity.noContent().build()
    }

    /**
     * Delete a Client Profile by User ID
     */
    @DeleteMapping("/clients/{userId}")
    @PreAuthorize("hasRole('CLIENT')")
    fun deleteClientProfile(
        @PathVariable userId: Long
    ): ResponseEntity<Void> {
        userProfileService.deleteClientProfile(userId)
        return ResponseEntity.noContent().build()
    }
}
