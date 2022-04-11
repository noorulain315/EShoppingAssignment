package com.example.eshoppingassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.data.models.Product
import com.example.eshoppingassignment.data.models.ProductResponse
import com.example.eshoppingassignment.databinding.FragmentProductListBinding
import com.example.eshoppingassignment.repo.ProductViewModel
import com.example.eshoppingassignment.ui.adapter.ProductAdapter
import com.example.eshoppingassignment.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private val viewModel: ProductViewModel by viewModels()
    private val adapter = ProductAdapter()

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
        setUpRecyclerView()
        listenDataUpdates()
        viewModel.deleteProduct(6)
        viewModel.addProduct(AddProductRequest("electronic", "hgasgh", "jhgaf", 20.0, "jhfas"))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun listenDataUpdates() {
        viewModel.getProductResponseLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.ResourceSuccess -> {
                    adapter.setData(getProductsList(it.data))
                }
                is Resource.ResourceError -> {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
                is Resource.ResourceLoading -> {}
            }
        }
    }

    private fun getProductsList(productResponse: ProductResponse): List<Product> {
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