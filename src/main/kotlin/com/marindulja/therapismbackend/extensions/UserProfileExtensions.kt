package com.marindulja.therapismbackend.extensions

import com.marindulja.therapismbackend.entities.ClientProfile
import com.marindulja.therapismbackend.entities.TherapistProfile
import com.marindulja.therapismbackend.entities.User
import com.marindulja.therapismbackend.models.ClientProfileModel
import com.marindulja.therapismbackend.models.ClientProfileRequest
import com.marindulja.therapismbackend.models.TherapistProfileModel
import com.marindulja.therapismbackend.models.TherapistProfileRequest

fun ClientProfile.toModel(): ClientProfileModel =
    ClientProfileModel(
        id = id,
        nickname = nickname,
        age = age,
        location = location,
        therapyGoals = therapyGoals,
        preferredCommunication = preferredCommunication,
        profilePictureUrl = profilePictureUrl,
        userId = user.id
    )

// Convert ClientProfileRequest to ClientProfile Entity
fun ClientProfileRequest.toEntity(user: User): ClientProfile =
    ClientProfile(
        id = null,
        nickname = nickname,
        age = age,
        location = location,
        therapyGoals = therapyGoals,
        preferredCommunication = preferredCommunication,
        profilePictureUrl = profilePictureUrl,
        user = user
    )

fun TherapistProfile.toModel(user: User): TherapistProfileModel =
    TherapistProfileModel(
        id = id,
        fullName = fullName,
        title = title,
        bio = bio,
        experienceYears = experienceYears,
        specialties = specialties,
        credentials = credentials,
        profilePictureUrl = profilePictureUrl,
        userId = user.id
    )

// Convert TherapistProfileRequest to TherapistProfile Entity
fun TherapistProfileRequest.toEntity(user: User): TherapistProfile =
    TherapistProfile(
        fullName = fullName,
        title = title,
        bio = bio,
        experienceYears = experienceYears,
        specialties = specialties,
        credentials = credentials,
        profilePictureUrl = profilePictureUrl,
        user = user
    )

