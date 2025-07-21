package com.lyg.conference.repository

import com.lyg.conference.models.UserProfile
import com.lyg.conference.models.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay

class MockUserRepository : UserRepository {
    private val users = mutableListOf<UserProfile>()
    private var currentUser: UserProfile? = null
    private val usersFlow = MutableStateFlow<List<UserProfile>>(emptyList())

    override suspend fun getCurrentUser(): UserProfile? = currentUser

    override suspend fun signIn(email: String, password: String): Result<UserProfile> {
        delay(500)
        val user = users.find { it.email == email }
        return if (user != null) {
            currentUser = user
            Result.success(user)
        } else {
            Result.failure(Exception("User not found"))
        }
    }

    override suspend fun signUp(email: String, password: String, name: String, role: UserRole): Result<UserProfile> {
        delay(500)
        if (users.any { it.email == email }) {
            return Result.failure(Exception("Email already registered"))
        }
        val user = UserProfile(
            id = (users.size + 1).toString(),
            email = email,
            name = name,
            role = role
        )
        users.add(user)
        usersFlow.value = users.toList()
        currentUser = user
        return Result.success(user)
    }

    override suspend fun signOut(): Result<Unit> {
        currentUser = null
        return Result.success(Unit)
    }

    override suspend fun updateProfile(user: UserProfile): Result<UserProfile> {
        val idx = users.indexOfFirst { it.id == user.id }
        return if (idx >= 0) {
            users[idx] = user
            usersFlow.value = users.toList()
            currentUser = user
            Result.success(user)
        } else {
            Result.failure(Exception("User not found"))
        }
    }

    override suspend fun getUserById(userId: String): UserProfile? = users.find { it.id == userId }

    override suspend fun getUsers(): Flow<List<UserProfile>> = usersFlow.asStateFlow()

    override suspend fun deleteUser(userId: String): Result<Unit> {
        val removed = users.removeIf { it.id == userId }
        usersFlow.value = users.toList()
        return if (removed) Result.success(Unit) else Result.failure(Exception("User not found"))
    }
}
