package com.lyg.conference.di

import com.lyg.conference.repository.SessionRepository
import com.lyg.conference.repository.UserRepository
import org.koin.dsl.module

actual val platformModule = module {
    // Platform-specific implementations will be provided by the Android app module
    // This allows the Android app to provide Firebase or other Android-specific implementations
}
