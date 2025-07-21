package com.lyg.conference.repository

import com.lyg.conference.models.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun getSessions(eventId: String): Flow<List<Session>>
    suspend fun getSession(sessionId: String): Session?
    suspend fun createSession(session: Session): Result<Session>
    suspend fun updateSession(session: Session): Result<Session>
    suspend fun deleteSession(sessionId: String): Result<Unit>
    suspend fun registerForSession(sessionId: String, userId: String): Result<Unit>
    suspend fun unregisterFromSession(sessionId: String, userId: String): Result<Unit>
    suspend fun getUserSessions(userId: String): Flow<List<Session>>
}
