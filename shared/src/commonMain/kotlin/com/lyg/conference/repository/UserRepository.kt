package com.lyg.conference.repository

import com.lyg.conference.models.UserProfile
import com.lyg.conference.models.UserRole
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getCurrentUser(): UserProfile?
    suspend fun signIn(email: String, password: String): Result<UserProfile>
    suspend fun signUp(email: String, password: String, name: String, role: UserRole): Result<UserProfile>
    suspend fun signOut(): Result<Unit>
    suspend fun updateProfile(user: UserProfile): Result<UserProfile>
    suspend fun getUserById(userId: String): UserProfile?
    suspend fun getUsers(): Flow<List<UserProfile>>
    suspend fun deleteUser(userId: String): Result<Unit>
}
