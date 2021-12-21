package com.example.teke.ViewModel

import androidx.lifecycle.*
import com.example.teke.Product.CartRepo
import com.example.teke.Product.ProductEntity
import kotlinx.coroutines.launch

class CartViewModel(val cartRepo: CartRepo) : ViewModel() {

    val allProducts: LiveData<List<ProductEntity>> = cartRepo.allProduct().asLiveData()

    val wishList: LiveData<List<ProductEntity>> = cartRepo.wishList().asLiveData()

    fun updateCart(productEntity: ProductEntity) = viewModelScope.launch {
        cartRepo.updateCart(productEntity)
    }

    class viewModelFactory(private val repository: CartRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

