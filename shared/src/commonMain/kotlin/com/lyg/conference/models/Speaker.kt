package com.lyg.conference.models

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val id: String,
    val name: String,
    val bio: String = "",
    val title: String = "",
    val organization: String = "",
    val email: String = "",
    val profileImageUrl: String? = null,
    val socialLinks: Map<String, String> = emptyMap(),
    val expertise: List<String> = emptyList()
)
