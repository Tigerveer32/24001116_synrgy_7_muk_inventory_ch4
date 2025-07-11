package com.example.synergy7ch4.presentation.add_product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.synergy7ch4.data.local.entities.Product
import com.example.synergy7ch4.data.local.repository.PreferenceRepositoryImpl
import com.example.synergy7ch4.data.local.repository.ProductsRepositoryImpl
import com.example.synergy7ch4.domain.PreferenceUseCase
import com.example.synergy7ch4.domain.ProductsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))
    private var _addProduct = MutableLiveData<Long>()

    val addProduct: LiveData<Long> = _addProduct
    val getUsrId = runBlocking { preferenceUseCase.executeGetUsrId().first() }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productsUseCase.executeAdd(product).collect {
                _addProduct.postValue(it)
            }
        }
    }
}