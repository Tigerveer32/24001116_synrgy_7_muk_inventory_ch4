package com.example.synergy7ch4.domain

import com.example.synergy7ch4.common.UIState
import com.example.synergy7ch4.data.local.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun login(user: User): Flow<UIState<User>>
    suspend fun checkUser(id: Long?): Flow<User>
    suspend fun register(user: User): Flow<UIState<Long>>
}