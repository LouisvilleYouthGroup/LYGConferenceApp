package com.lyg.conference.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val name: String,
    val role: UserRole,
    val profileImageUrl: String? = null,
    val organization: String? = null,
    val bio: String? = null
)
