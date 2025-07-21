package com.lyg.conference.android.di

import com.lyg.conference.repository.SessionRepository
import com.lyg.conference.repository.UserRepository
import com.lyg.conference.usecases.GetScheduleUseCase
import com.lyg.conference.viewmodels.ScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories - These would be implemented with Firebase/API calls
    single<SessionRepository> { SessionRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    
    // Use Cases
    single { GetScheduleUseCase(get()) }
    
    // ViewModels
    viewModel { ScheduleViewModel(get()) }
}

// Temporary implementations - replace with actual Firebase/API implementations
class SessionRepositoryImpl : SessionRepository {
    override suspend fun getSessions(eventId: String) = kotlinx.coroutines.flow.flowOf(emptyList<com.lyg.conference.models.Session>())
    override suspend fun getSession(sessionId: String) = null
    override suspend fun createSession(session: com.lyg.conference.models.Session) = Result.success(session)
    override suspend fun updateSession(session: com.lyg.conference.models.Session) = Result.success(session)
    override suspend fun deleteSession(sessionId: String) = Result.success(Unit)
    override suspend fun registerForSession(sessionId: String, userId: String) = Result.success(Unit)
    override suspend fun unregisterFromSession(sessionId: String, userId: String) = Result.success(Unit)
    override suspend fun getUserSessions(userId: String) = kotlinx.coroutines.flow.flowOf(emptyList<com.lyg.conference.models.Session>())
}

class UserRepositoryImpl : UserRepository {
    override suspend fun getCurrentUser() = null
    override suspend fun signIn(email: String, password: String) = Result.success(
        com.lyg.conference.models.User("1", email, "Test User", com.lyg.conference.models.UserRole.ATTENDEE)
    )
    override suspend fun signUp(email: String, password: String, name: String, role: com.lyg.conference.models.UserRole) = Result.success(
        com.lyg.conference.models.User("1", email, name, role)
    )
    override suspend fun signOut() = Result.success(Unit)
    override suspend fun updateProfile(user: com.lyg.conference.models.User) = Result.success(user)
    override suspend fun getUserById(userId: String) = null
    override suspend fun getUsers() = kotlinx.coroutines.flow.flowOf(emptyList<com.lyg.conference.models.User>())
    override suspend fun deleteUser(userId: String) = Result.success(Unit)
}
