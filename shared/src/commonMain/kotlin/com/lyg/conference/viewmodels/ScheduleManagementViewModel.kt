package com.lyg.conference.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyg.conference.models.Session
import com.lyg.conference.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class OperationResult {
    data class Success(val message: String) : OperationResult()
    data class Error(val message: String) : OperationResult()
}

class ScheduleManagementViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    
    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = _sessions.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _operationResult = MutableStateFlow<OperationResult?>(null)
    val operationResult: StateFlow<OperationResult?> = _operationResult.asStateFlow()
    
    init {
        loadSessions()
    }
    
    private fun loadSessions() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            sessionRepository.getAllSessions()
                .catch { exception ->
                    _error.value = "Failed to load sessions: ${exception.message}"
                    _isLoading.value = false
                }
                .collect { sessionList ->
                    _sessions.value = sessionList.sortedBy { it.startTime }
                    _isLoading.value = false
                }
        }
    }
    
    fun createSession(session: Session) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            sessionRepository.createSession(session)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Session created successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to create session: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun updateSession(session: Session) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            sessionRepository.updateSession(session)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Session updated successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to update session: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun deleteSession(sessionId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            sessionRepository.deleteSession(sessionId)
                .onSuccess {
                    _operationResult.value = OperationResult.Success("Session deleted successfully")
                }
                .onFailure { exception ->
                    _error.value = "Failed to delete session: ${exception.message}"
                }
            
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearOperationResult() {
        _operationResult.value = null
    }
}
