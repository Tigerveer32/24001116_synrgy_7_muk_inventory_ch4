package com.example.synergy7ch4.domain

import com.example.synergy7ch4.common.UIState
import com.example.synergy7ch4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAll(userId: Long?): Flow<List<Product>>

    fun getQuery(userId: Long?, query: String?): Flow<List<Product>>

    suspend fun checkProduct(id: Long?): Flow<UIState<Product>>

    suspend fun add(product: Product): Flow<Long>

    suspend fun update(product: Product): Flow<Int>

    suspend fun delete(id: Long?): Flow<Unit>
}