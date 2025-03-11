package com.marindulja.therapismbackend.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.OneToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.FetchType

@Entity
@Table(name = "client_profiles")
data class ClientProfile(
    override val id: Long? = null,

    val nickname: String?,
    val age: Int?,
    val location: String?,
    val therapyGoals: String?,
    val preferredCommunication: String?,

    override val profilePictureUrl: String?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    override val user: User,

    /*@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val moodEntries: List<MoodEntry> = emptyList(),

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val assignedTasks: List<Task> = emptyList()*/
) : UserProfile(id, profilePictureUrl, user)
