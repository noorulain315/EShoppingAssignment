package com.example.eshoppingassignment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eshoppingassignment.data.models.Product
import com.example.eshoppingassignment.databinding.ListRowProductBinding

class ProductAdapter(
    private var data: MutableList<Product>,
    var onProductChecked: (count: Int) -> Unit,
    var onProductDelete: (position: Int) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ListRowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position], position, {
            onProductChecked(checkProduct(position))
        }, {
            deleteProduct(position)
            onProductDelete(position)
        })
    }

    override fun getItemCount(): Int = data.size

    class ProductViewHolder(private val binding: ListRowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: Product, position: Int, onProductChecked: (position: Int) -> Unit,
            onProductDelete: (position: Int) -> Unit
        ) {
            val context = binding.root.context
            binding.titleTextview.text = product.title
            binding.checkImageView.visibility = if (product.isChecked) View.VISIBLE else View.GONE
            Glide.with(context).load(product.image).centerCrop().into(binding.productImageView)
            binding.productImageView.setOnClickListener {
                onProductChecked(position)
            }
            binding.deleteImageView.setOnClickListener {
                onProductDelete(position)
            }
        }
    }

    private fun checkProduct(position: Int): Int {
        data[position].isChecked = !data[position].isChecked
        notifyItemChanged(position)
        return data.filter { !it.isChecked }.size
    }

    private fun deleteProduct(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
    }

}