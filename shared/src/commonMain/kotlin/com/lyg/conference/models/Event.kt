package com.lyg.conference.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Instant,
    val endDate: Instant,
    val location: String,
    val venue: String? = null,
    val organizerId: String,
    val maxAttendees: Int? = null,
    val registeredAttendees: Int = 0,
    val isPublic: Boolean = true,
    val registrationDeadline: Instant? = null,
    val tags: List<String> = emptyList(),
    val imageUrl: String? = null
)
