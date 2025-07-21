package com.lyg.conference.repository

import com.lyg.conference.models.ConferenceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay

class MockEventRepository : EventRepository {
    
    private val _events = MutableStateFlow<List<ConferenceEvent>>(getSampleEvents())
    
    override suspend fun getAllEvents(): Flow<List<ConferenceEvent>> {
        return _events.asStateFlow()
    }
    
    override suspend fun getEventById(id: String): ConferenceEvent? {
        return _events.value.find { it.id == id }
    }
    
    override suspend fun createEvent(event: ConferenceEvent): Result<String> {
        return try {
            // Simulate network delay
            delay(1200)
            
            val currentEvents = _events.value.toMutableList()
            currentEvents.add(event)
            _events.value = currentEvents.sortedBy { it.startDate }
            Result.success(event.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateEvent(event: ConferenceEvent): Result<Unit> {
        return try {
            delay(900)
            
            val currentEvents = _events.value.toMutableList()
            val index = currentEvents.indexOfFirst { it.id == event.id }
            if (index != -1) {
                currentEvents[index] = event
                _events.value = currentEvents.sortedBy { it.startDate }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Event not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteEvent(id: String): Result<Unit> {
        return try {
            delay(600)
            
            val currentEvents = _events.value.toMutableList()
            val removed = currentEvents.removeAll { it.id == id }
            if (removed) {
                _events.value = currentEvents
                Result.success(Unit)
            } else {
                Result.failure(Exception("Event not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun uploadEventImage(eventId: String, imageData: ByteArray): Result<String> {
        return try {
            // Simulate image upload with progress
            delay(2500)
            
            val imageUrl = "https://storage.googleapis.com/lyg-conference/events/$eventId.jpg"
            Result.success(imageUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun getSampleEvents(): List<ConferenceEvent> {
        return listOf(
            ConferenceEvent(
                id = "event_1",
                name = "Louisville Youth Group Conference 2024",
                description = "Annual conference bringing together youth from across Louisville for worship, learning, and fellowship. Join us for an inspiring weekend of spiritual growth, community building, and life-changing experiences.",
                startDate = "2024-07-20",
                endDate = "2024-07-22",
                location = "Louisville Convention Center",
                maxCapacity = 500,
                registeredAttendees = 342,
                status = "Active",
                organizerId = "org_1",
                imageUrl = "https://images.unsplash.com/photo-1511632765486-a01980e01a18?w=800&h=400&fit=crop",
                registrationDeadline = "2024-07-15",
                tags = listOf("youth", "conference", "worship", "fellowship", "louisville")
            ),
            ConferenceEvent(
                id = "event_2",
                name = "Summer Youth Retreat",
                description = "3-day retreat focused on spiritual growth and community building. Escape the city and connect with God and fellow believers in a beautiful natural setting.",
                startDate = "2024-08-15",
                endDate = "2024-08-17",
                location = "Camp Crossroads",
                maxCapacity = 150,
                registeredAttendees = 89,
                status = "Active",
                organizerId = "org_2",
                imageUrl = "https://images.unsplash.com/photo-1504851149312-7a075b496cc7?w=800&h=400&fit=crop",
                registrationDeadline = "2024-08-10",
                tags = listOf("retreat", "summer", "camp", "spiritual-growth", "nature")
            ),
            ConferenceEvent(
                id = "event_3",
                name = "Fall Leadership Summit",
                description = "Leadership development conference for youth leaders and mentors. Learn essential skills for guiding and inspiring the next generation of Christian leaders.",
                startDate = "2024-10-12",
                endDate = "2024-10-13",
                location = "Faith Community Center",
                maxCapacity = 100,
                registeredAttendees = 23,
                status = "Active",
                organizerId = "org_1",
                imageUrl = "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800&h=400&fit=crop",
                registrationDeadline = "2024-10-05",
                tags = listOf("leadership", "training", "mentorship", "development", "fall")
            ),
            ConferenceEvent(
                id = "event_4",
                name = "Christmas Youth Celebration",
                description = "Special Christmas celebration for youth with worship, games, and fellowship. Celebrate the birth of Jesus with your youth group family.",
                startDate = "2024-12-21",
                endDate = "2024-12-21",
                location = "Grace Fellowship Hall",
                maxCapacity = 200,
                registeredAttendees = 0,
                status = "Draft",
                organizerId = "org_3",
                imageUrl = "https://images.unsplash.com/photo-1512389142860-9c449e58a543?w=800&h=400&fit=crop",
                registrationDeadline = "2024-12-15",
                tags = listOf("christmas", "celebration", "worship", "fellowship", "holiday")
            ),
            ConferenceEvent(
                id = "event_5",
                name = "Spring Mission Trip Prep",
                description = "Preparation workshop for upcoming spring mission trips. Learn about cross-cultural ministry, team building, and practical mission skills.",
                startDate = "2025-02-28",
                endDate = "2025-03-01",
                location = "Crossroads Community Church",
                maxCapacity = 75,
                registeredAttendees = 0,
                status = "Draft",
                organizerId = "org_2",
                imageUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800&h=400&fit=crop",
                registrationDeadline = "2025-02-20",
                tags = listOf("mission", "preparation", "spring", "cross-cultural", "service")
            )
        )
    }
}
