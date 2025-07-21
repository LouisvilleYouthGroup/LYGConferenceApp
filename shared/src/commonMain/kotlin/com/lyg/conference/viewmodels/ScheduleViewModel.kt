package com.lyg.conference.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyg.conference.models.Session
import com.lyg.conference.usecases.GetScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {
    
    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = _sessions.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                getScheduleUseCase.getAllSessions()
                    .catch { exception ->
                        _error.value = "Failed to load schedule: ${exception.message}"
                        _isLoading.value = false
                    }
                    .collect { sessions ->
                        _sessions.value = sessions
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = "Failed to load schedule: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
