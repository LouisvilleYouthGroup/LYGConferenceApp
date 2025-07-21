package com.lyg.conference.repository

import com.lyg.conference.models.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Instant

class MockSessionRepository : SessionRepository {
    
    private val _sessions = MutableStateFlow<List<Session>>(getSampleSessions())
    
    override suspend fun getAllSessions(): Flow<List<Session>> {
        return _sessions.asStateFlow()
    }
    
    override suspend fun getSessionById(id: String): Session? {
        return _sessions.value.find { it.id == id }
    }
    
    override suspend fun createSession(session: Session): Result<String> {
        return try {
            val currentSessions = _sessions.value.toMutableList()
            currentSessions.add(session)
            _sessions.value = currentSessions.sortedBy { it.startTime }
            Result.success(session.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateSession(session: Session): Result<Unit> {
        return try {
            val currentSessions = _sessions.value.toMutableList()
            val index = currentSessions.indexOfFirst { it.id == session.id }
            if (index != -1) {
                currentSessions[index] = session
                _sessions.value = currentSessions.sortedBy { it.startTime }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Session not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteSession(id: String): Result<Unit> {
        return try {
            val currentSessions = _sessions.value.toMutableList()
            val removed = currentSessions.removeAll { it.id == id }
            if (removed) {
                _sessions.value = currentSessions
                Result.success(Unit)
            } else {
                Result.failure(Exception("Session not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getSessionsByEventId(eventId: String): Flow<List<Session>> {
        return _sessions.asStateFlow()
    }
    
    override suspend fun getSessionsBySpeakerId(speakerId: String): Flow<List<Session>> {
        return _sessions.asStateFlow()
    }
    
    private fun getSampleSessions(): List<Session> {
        return listOf(
            Session(
                id = "session_1",
                title = "Opening Ceremony",
                description = "Welcome to the Louisville Youth Group Conference 2024! Join us for an inspiring start to our weekend together.",
                startTime = Instant.parse("2024-07-20T09:00:00.000Z"),
                endTime = Instant.parse("2024-07-20T10:00:00.000Z"),
                location = "Main Auditorium",
                speakerIds = listOf("speaker_1"),
                capacity = 500,
                registeredCount = 342
            ),
            Session(
                id = "session_2",
                title = "Youth Leadership Workshop",
                description = "Learn essential leadership skills for young Christians. Interactive workshop with practical exercises.",
                startTime = Instant.parse("2024-07-20T10:30:00.000Z"),
                endTime = Instant.parse("2024-07-20T12:00:00.000Z"),
                location = "Conference Room A",
                speakerIds = listOf("speaker_2"),
                capacity = 100,
                registeredCount = 89,
                isWorkshop = true,
                requiresRegistration = true
            ),
            Session(
                id = "session_3",
                title = "Music & Worship",
                description = "Interactive worship session with contemporary Christian music and praise.",
                startTime = Instant.parse("2024-07-20T14:00:00.000Z"),
                endTime = Instant.parse("2024-07-20T15:30:00.000Z"),
                location = "Chapel",
                speakerIds = listOf("speaker_3"),
                capacity = 300,
                registeredCount = 267
            ),
            Session(
                id = "session_4",
                title = "Life Skills for Teens",
                description = "Practical life skills every teenager needs to know for success in adulthood.",
                startTime = Instant.parse("2024-07-20T16:00:00.000Z"),
                endTime = Instant.parse("2024-07-20T17:30:00.000Z"),
                location = "Conference Room B",
                speakerIds = listOf("speaker_4"),
                capacity = 80,
                registeredCount = 67,
                isWorkshop = true,
                requiresRegistration = true
            )
        )
    }
}
