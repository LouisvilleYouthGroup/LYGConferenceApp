package com.lyg.conference.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val email: String,
    val name: String,
    val role: UserRole = UserRole.ATTENDEE,
    val phone: String? = null,
    val organization: String? = null,
    val photoUrl: String? = null,
    val onboardingComplete: Boolean = false
)
