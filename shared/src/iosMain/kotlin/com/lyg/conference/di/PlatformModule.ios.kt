package com.lyg.conference.di

import com.lyg.conference.repository.SessionRepository
import com.lyg.conference.repository.UserRepository
import org.koin.dsl.module

actual val platformModule = module {
    // iOS-specific implementations would go here
    // For now, we'll use mock implementations
}
