package com.example.synergy7ch4.presentation.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.synergy7ch4.R
import com.example.synergy7ch4.data.local.entities.Product
import com.example.synergy7ch4.databinding.ListItemHomeBinding
import kotlin.random.Random

class HomeAdapter : ListAdapter<Product, HomeAdapter.ProductViewHolder>(ProductComparator) {

    private var _onInteraction: HomeInteraction? = null

    private object ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ListItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindItem(getItem(position))
        val randomColor = holder.itemView.context.resources.getIntArray(R.array.card_colors)
        val randomIndex = Random.nextInt(randomColor.size)
        val colorTint = ColorStateList.valueOf(randomColor[randomIndex])
        ViewCompat.setBackgroundTintList(holder.itemView, colorTint)
    }

    inner class ProductViewHolder(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onInteraction?.onClick(bindingAdapterPosition, getItem(bindingAdapterPosition))
            }

            itemView.setOnLongClickListener {
                _onInteraction?.onLongClick(bindingAdapterPosition, getItem(bindingAdapterPosition))
                true
            }
        }

        fun bindItem(item: Product) {
            binding.txtTitle.text = item.name
            binding.txtDescription.text = item.description
            binding.txtPrice.text = item.price.toString()
        }
    }

    fun onInteraction(listener: HomeInteraction) {
        _onInteraction = listener
    }
}