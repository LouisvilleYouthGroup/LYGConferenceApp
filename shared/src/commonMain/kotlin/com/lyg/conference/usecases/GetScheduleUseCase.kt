package com.lyg.conference.usecases

import com.lyg.conference.models.Session
import com.lyg.conference.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend fun getAllSessions(): Flow<List<Session>> {
        return sessionRepository.getAllSessions()
    }
    
    suspend fun getSessionsByEvent(eventId: String): Flow<List<Session>> {
        return sessionRepository.getSessionsByEventId(eventId)
    }
    
    suspend fun getSessionsBySpeaker(speakerId: String): Flow<List<Session>> {
        return sessionRepository.getSessionsBySpeakerId(speakerId)
    }
}
