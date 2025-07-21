package com.lyg.conference.repository

import com.lyg.conference.models.Speaker
import kotlinx.coroutines.flow.Flow

interface SpeakerRepository {
    suspend fun getAllSpeakers(): Flow<List<Speaker>>
    suspend fun getSpeakerById(id: String): Speaker?
    suspend fun createSpeaker(speaker: Speaker): Result<String>
    suspend fun updateSpeaker(speaker: Speaker): Result<Unit>
    suspend fun deleteSpeaker(id: String): Result<Unit>
    suspend fun uploadSpeakerImage(speakerId: String, imageData: ByteArray): Result<String>
}
