package com.example.synergy7ch4.presentation.edit_product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.synergy7ch4.common.UIState
import com.example.synergy7ch4.data.local.entities.Product
import com.example.synergy7ch4.data.local.repository.PreferenceRepositoryImpl
import com.example.synergy7ch4.data.local.repository.ProductsRepositoryImpl
import com.example.synergy7ch4.domain.PreferenceUseCase
import com.example.synergy7ch4.domain.ProductsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productsUseCase = ProductsUseCase(ProductsRepositoryImpl(application))
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))
    private var _editProduct = MutableLiveData<Int>()
    private var _checkProduct = MutableLiveData<UIState<Product>>()

    val editProduct: LiveData<Int> = _editProduct
    val checkProduct: LiveData<UIState<Product>> = _checkProduct
    val getUsrId = runBlocking { preferenceUseCase.executeGetUsrId().first() }

    fun editProduct(product: Product) {
        viewModelScope.launch {
            productsUseCase.executeUpdate(product).collect {
                _editProduct.postValue(it)
            }
        }
    }

    fun checkProduct(id: Long?) {
        viewModelScope.launch {
            productsUseCase.executeCheckProduct(id).collect {
                _checkProduct.postValue(it)
            }
        }
    }
}