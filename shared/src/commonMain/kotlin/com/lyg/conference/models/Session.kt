package com.lyg.conference.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val id: String,
    val title: String,
    val description: String,
    val startTime: Instant,
    val endTime: Instant,
    val location: String,
    val speakerIds: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val capacity: Int? = null,
    val registeredCount: Int = 0,
    val isWorkshop: Boolean = false,
    val requiresRegistration: Boolean = false
)
