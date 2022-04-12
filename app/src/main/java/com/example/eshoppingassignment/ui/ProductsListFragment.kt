package com.example.eshoppingassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eshoppingassignment.R
import com.example.eshoppingassignment.data.models.Product
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.databinding.FragmentProductListBinding
import com.example.eshoppingassignment.repo.ProductViewModel
import com.example.eshoppingassignment.ui.adapter.ProductAdapter
import com.example.eshoppingassignment.util.Resource
import com.example.eshoppingassignment.util.hide
import com.example.eshoppingassignment.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenDataUpdates()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
      //  viewModel.getProducts()
        viewModel.deleteProduct(1)
    }

    private fun setListeners() {
        binding.addProductButton.setOnClickListener {
            showAddProduct()
        }
        binding.retryTextView.setOnClickListener {
            viewModel.getProducts()
        }
    }

    private fun showAddProduct() {
        val dialog = AddProductDialogFragment.create()
        dialog.show(parentFragmentManager, "AddProductDialogFragment")
    }

    private fun setUpRecyclerView(data: MutableList<Product>) {
        val adapter = ProductAdapter(data, {
            binding.checkedCountTextView.text = getString(R.string.items_left_msg, it.toString())
        }, {
            viewModel.deleteProduct(data[it].id)
            val checkedCount = data.filter { product -> !product.isChecked }.size.toString()
            binding.checkedCountTextView.text =
                getString(R.string.items_left_msg, checkedCount)
        })
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun listenDataUpdates() {
        viewModel.getProductResponseLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.ResourceSuccess -> {
                    handleSuccessProductResponse(it.data)
                }
                is Resource.ResourceError -> {
                    binding.retryTextView.show()
                    binding.recyclerView.hide()
                    binding.checkedCountTextView.hide()
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
                is Resource.ResourceLoading -> {
                    binding.retryTextView.hide()
                    binding.recyclerView.hide()
                    binding.checkedCountTextView.hide()
                    binding.progressBar.show()
                }
            }
        }

        viewModel.getProductDeleteLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.ResourceSuccess -> {
                    binding.checkedCountTextView.show()
                    val msg = getString(R.string.item_delete_msg, it.data.title)
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                    Log.d("delete", it.data.toString())
                }
                is Resource.ResourceError -> {
                    binding.checkedCountTextView.show()
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                    Log.d("delete error", it.toString())
                }
                is Resource.ResourceLoading -> {
                    binding.checkedCountTextView.hide()
                }
            }
        }
    }

    private fun handleSuccessProductResponse(productResponse: ProductResponse) {
        binding.retryTextView.hide()
        binding.progressBar.hide()
        binding.recyclerView.show()
        binding.checkedCountTextView.text =
            getString(R.string.items_left_msg, productResponse.size.toString())
        binding.checkedCountTextView.show()
        setUpRecyclerView(getProductsList(productResponse))
    }

    private fun getProductsList(productResponse: ProductResponse): MutableList<Product> {
        val list = mutableListOf<Product>()
        list.addAll(productResponse.map {
            Product(it.id, it.image, it.title)
        })
        return list
    }

    companion object {
        fun create() = ProductsListFragment()
    }
}