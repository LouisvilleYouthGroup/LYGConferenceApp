package com.lyg.conference.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyg.conference.models.ConferenceEvent
import com.lyg.conference.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EventManagementViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {
    
    private val _events = MutableStateFlow<List<ConferenceEvent>>(emptyList())
    val events: StateFlow<List<ConferenceEvent>> = _events.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _operationResult = MutableStateFlow<OperationResult?>(null)
    val operationResult: StateFlow<OperationResult?> = _operationResult.asStateFlow()
    
    private val _imageUploadProgress = MutableStateFlow<Float?>(null)
    val imageUploadProgress: StateFlow<Float?> = _imageUploadProgress.asStateFlow()
    
    init {
        loadEvents()
    }
    
    private fun loadEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            eventRepository.getAllEvents()
                .catch { exception ->
                    _error.value = "Failed to load events: ${exception.message}"
                    _isLoading.value = false
                }
                .collect { eventList ->
                    _events.value = eventList.sortedBy { it.startDate }
                    _isLoading.value = false
                }
        }
    }
    
    fun createEvent(
        name: String,
        description: String,
        startDate: String,
        endDate: String,
        location: String,
        maxCapacity: Int,
        registrationDeadline: String? = null,
        tags: List<String> = emptyList(),
        status: String = "Draft"
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val event = ConferenceEvent(
                id = generateEventId(),
                name = name,
                description = description,
                startDate = startDate,
                endDate = endDate,
                location = location,
                maxCapacity = maxCapacity,
                registrationDeadline = registrationDeadline,
                tags = tags,
                status = status
            )
            
            eventRepository.createEvent(event)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Event created successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to create event: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun updateEvent(event: ConferenceEvent) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            eventRepository.updateEvent(event)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Event updated successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to update event: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            eventRepository.deleteEvent(eventId)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Event deleted successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to delete event: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun uploadEventImage(eventId: String, imageData: ByteArray) {
        viewModelScope.launch {
            _imageUploadProgress.value = 0f
            _error.value = null
            
            // Simulate upload progress
            for (i in 1..10) {
                _imageUploadProgress.value = i / 10f
                kotlinx.coroutines.delay(100)
            }
            
            eventRepository.uploadEventImage(eventId, imageData)
                .onSuccess { imageUrl ->
                    // Update event with new image URL
                    val event = _events.value.find { it.id == eventId }
                    event?.let { currentEvent ->
                        val updatedEvent = currentEvent.copy(imageUrl = imageUrl)
                        updateEvent(updatedEvent)
                    }
                    _operationResult.value = OperationResult.Success("Event image uploaded successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to upload image: ${exception.message}"
                }
            
            _imageUploadProgress.value = null
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearOperationResult() {
        _operationResult.value = null
    }
    
    private fun generateEventId(): String {
        return "event_${System.currentTimeMillis()}"
    }
}
