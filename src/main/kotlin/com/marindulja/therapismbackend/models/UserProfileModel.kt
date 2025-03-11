package com.marindulja.therapismbackend.models

data class ClientProfileRequest(
    val nickname: String?,
    val age: Int?,
    val location: String?,
    val therapyGoals: String?,
    val preferredCommunication: String?,
    val profilePictureUrl: String?
)

data class TherapistProfileRequest(
    val fullName: String,
    val title: String?,
    val bio: String?,
    val experienceYears: Int?,
    val specialties: String?,
    val credentials: String?,
    val languages: String?,
    val profilePictureUrl: String?
)

data class ClientProfileModel(
    val id: Long?,
    val nickname: String?,
    val age: Int?,
    val location: String?,
    val therapyGoals: String?,
    val preferredCommunication: String?,
    val profilePictureUrl: String?,
    val userId: Long
)

data class TherapistProfileModel(
    val id: Long?,
    val fullName: String?,
    val title: String?,
    val bio: String?,
    val experienceYears: Int?,
    val specialties: String?,
    val credentials: String?,
    val profilePictureUrl: String?,
    val userId: Long
)



