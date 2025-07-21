package com.lyg.conference.repository

import com.lyg.conference.models.User
import com.lyg.conference.models.UserRole
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String, name: String, role: UserRole): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun updateProfile(user: User): Result<User>
    suspend fun getUserById(userId: String): User?
    suspend fun getUsers(): Flow<List<User>>
    suspend fun deleteUser(userId: String): Result<Unit>
}
