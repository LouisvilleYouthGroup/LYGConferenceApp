package com.lyg.conference.di

import com.lyg.conference.repository.SessionRepository
import com.lyg.conference.repository.UserRepository
import com.lyg.conference.usecases.GetScheduleUseCase
import com.lyg.conference.viewmodels.ScheduleViewModel
import org.koin.dsl.module

val sharedModule = module {
    // Use Cases
    single { GetScheduleUseCase(get()) }
    
    // ViewModels
    single { ScheduleViewModel(get()) }
}

// Platform-specific modules will provide repository implementations
expect val platformModule: org.koin.core.module.Module
