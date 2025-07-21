package com.lyg.conference.repository

import com.lyg.conference.models.ConferenceEvent
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getAllEvents(): Flow<List<ConferenceEvent>>
    suspend fun getEventById(id: String): ConferenceEvent?
    suspend fun createEvent(event: ConferenceEvent): Result<String>
    suspend fun updateEvent(event: ConferenceEvent): Result<Unit>
    suspend fun deleteEvent(id: String): Result<Unit>
    suspend fun uploadEventImage(eventId: String, imageData: ByteArray): Result<String>
}
