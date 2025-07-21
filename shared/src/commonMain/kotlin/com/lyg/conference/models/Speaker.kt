package com.lyg.conference.models

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val id: String,
    val name: String,
    val bio: String,
    val title: String? = null,
    val organization: String? = null,
    val profileImageUrl: String? = null,
    val socialLinks: Map<String, String> = emptyMap(),
    val expertise: List<String> = emptyList()
)
