package com.lyg.conference.usecases

import com.lyg.conference.models.Session
import com.lyg.conference.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(eventId: String): Flow<List<Session>> {
        return sessionRepository.getSessions(eventId)
    }
}
