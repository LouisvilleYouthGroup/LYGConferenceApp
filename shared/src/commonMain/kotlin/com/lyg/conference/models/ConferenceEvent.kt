package com.lyg.conference.models

import kotlinx.serialization.Serializable

@Serializable
data class ConferenceEvent(
    val id: String,
    val name: String,
    val description: String = "",
    val startDate: String,
    val endDate: String,
    val location: String,
    val maxCapacity: Int,
    val registeredAttendees: Int = 0,
    val status: String = "Draft", // Draft, Active, Completed, Cancelled
    val organizerId: String? = null,
    val imageUrl: String? = null,
    val registrationDeadline: String? = null,
    val tags: List<String> = emptyList()
)
