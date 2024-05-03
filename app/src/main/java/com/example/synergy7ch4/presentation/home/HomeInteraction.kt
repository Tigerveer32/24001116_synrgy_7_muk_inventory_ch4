package com.example.synergy7ch4.presentation.home

import com.example.synergy7ch4.data.local.entities.Product

interface HomeInteraction {
    fun onClick(position: Int, item: Product)
    fun onLongClick(position: Int, item: Product)
}