package com.example.synergy7ch4.domain

import com.example.synergy7ch4.common.UIState
import com.example.synergy7ch4.data.local.entities.User
import kotlinx.coroutines.flow.Flow

class UsersUseCase(private val usersRepository: UsersRepository) {
    suspend fun executeLogin(user: User): Flow<UIState<User>> = usersRepository.login(user)
    suspend fun executeCheckUser(id: Long?): Flow<User> = usersRepository.checkUser(id)
    suspend fun executeRegister(user: User): Flow<UIState<Long>> = usersRepository.register(user)
}