package com.lyg.conference.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyg.conference.models.Speaker
import com.lyg.conference.repository.SpeakerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SpeakerManagementViewModel(
    private val speakerRepository: SpeakerRepository
) : ViewModel() {
    
    private val _speakers = MutableStateFlow<List<Speaker>>(emptyList())
    val speakers: StateFlow<List<Speaker>> = _speakers.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _operationResult = MutableStateFlow<OperationResult?>(null)
    val operationResult: StateFlow<OperationResult?> = _operationResult.asStateFlow()
    
    private val _imageUploadProgress = MutableStateFlow<Float?>(null)
    val imageUploadProgress: StateFlow<Float?> = _imageUploadProgress.asStateFlow()
    
    init {
        loadSpeakers()
    }
    
    private fun loadSpeakers() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            speakerRepository.getAllSpeakers()
                .catch { exception ->
                    _error.value = "Failed to load speakers: ${exception.message}"
                    _isLoading.value = false
                }
                .collect { speakerList ->
                    _speakers.value = speakerList.sortedBy { it.name }
                    _isLoading.value = false
                }
        }
    }
    
    fun createSpeaker(speaker: Speaker) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            speakerRepository.createSpeaker(speaker)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Speaker created successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to create speaker: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun updateSpeaker(speaker: Speaker) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            speakerRepository.updateSpeaker(speaker)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Speaker updated successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to update speaker: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun deleteSpeaker(speakerId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            speakerRepository.deleteSpeaker(speakerId)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Speaker deleted successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to delete speaker: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun uploadSpeakerImage(speakerId: String, imageData: ByteArray) {
        viewModelScope.launch {
            _imageUploadProgress.value = 0f
            _error.value = null
            
            // Simulate upload progress
            for (i in 1..10) {
                _imageUploadProgress.value = i / 10f
                kotlinx.coroutines.delay(100)
            }
            
            speakerRepository.uploadSpeakerImage(speakerId, imageData)
                .onSuccess { imageUrl ->
                    // Update speaker with new image URL
                    val speaker = _speakers.value.find { it.id == speakerId }
                    speaker?.let { currentSpeaker ->
                        val updatedSpeaker = currentSpeaker.copy(profileImageUrl = imageUrl)
                        updateSpeaker(updatedSpeaker)
                    }
                    _operationResult.value = OperationResult.Success("Speaker image uploaded successfully")
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
}
