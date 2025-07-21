package com.lyg.conference.android.di

import com.lyg.conference.repository.SessionRepository
import com.lyg.conference.repository.SpeakerRepository
import com.lyg.conference.repository.EventRepository
import com.lyg.conference.repository.MockSessionRepository
import com.lyg.conference.repository.MockSpeakerRepository
import com.lyg.conference.repository.MockEventRepository
import com.lyg.conference.usecases.GetScheduleUseCase
import com.lyg.conference.viewmodels.ScheduleViewModel
import com.lyg.conference.viewmodels.ScheduleManagementViewModel
import com.lyg.conference.viewmodels.SpeakerManagementViewModel
import com.lyg.conference.viewmodels.EventManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    
    // Repositories - Using Mock implementations for now
    single<SessionRepository> { MockSessionRepository() }
    single<SpeakerRepository> { MockSpeakerRepository() }
    single<EventRepository> { MockEventRepository() }
    
    // Use Cases
    single { GetScheduleUseCase(get()) }
    
    // ViewModels
    viewModel { ScheduleViewModel(get()) }
    viewModel { ScheduleManagementViewModel(get()) }
    viewModel { SpeakerManagementViewModel(get()) }
    viewModel { EventManagementViewModel(get()) }
}
