package com.marindulja.therapismbackend.entities

import jakarta.persistence.*

@Entity
@Table(name = "therapist_profiles")
data class TherapistProfile(
    override val id: Long? = null,

    val fullName: String,
    val title: String?,
    val bio: String?,
    val experienceYears: Int?,
    val credentials: String?,
    val specialties: String?,

    override val profilePictureUrl: String?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    override val user: User,

    /*@OneToMany(mappedBy = "therapist", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val availabilitySlots: List<AvailabilitySlot> = emptyList(),

    @OneToMany(mappedBy = "therapist", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val reviews: List<Review> = emptyList()*/
) : UserProfile(id, profilePictureUrl, user)

