package com.example.synergy7ch4.domain

import com.example.synergy7ch4.common.UIState
import com.example.synergy7ch4.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

class ProductsUseCase(private val productsRepository: ProductsRepository) {

    fun executeGetAll(userId: Long?): Flow<List<Product>> = productsRepository.getAll(userId)

    fun executeGetQuery(userId: Long?, query: String?): Flow<List<Product>> = productsRepository.getQuery(userId, query)

    suspend fun executeCheckProduct(id: Long?): Flow<UIState<Product>> = productsRepository.checkProduct(id)

    suspend fun executeAdd(product: Product): Flow<Long> = productsRepository.add(product)

    suspend fun executeUpdate(product: Product): Flow<Int> = productsRepository.update(product)

    suspend fun executeDelete(id: Long?) = productsRepository.delete(id)
}