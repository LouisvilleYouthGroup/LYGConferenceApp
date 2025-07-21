package com.lyg.conference.repository

import com.lyg.conference.models.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun getAllSessions(): Flow<List<Session>>
    suspend fun getSessionById(id: String): Session?
    suspend fun createSession(session: Session): Result<String>
    suspend fun updateSession(session: Session): Result<Unit>
    suspend fun deleteSession(id: String): Result<Unit>
    suspend fun getSessionsByEventId(eventId: String): Flow<List<Session>>
    suspend fun getSessionsBySpeakerId(speakerId: String): Flow<List<Session>>
}
