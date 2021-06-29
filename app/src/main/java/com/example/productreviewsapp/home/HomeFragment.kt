package com.example.productreviewsapp.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productreviewsapp.MainActivity
import com.example.productreviewsapp.R
import com.example.productreviewsapp.detail.DetailFragment
import com.example.productreviewsapp.models.Product

class HomeFragment: Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var adapter: ProductAdapter
    lateinit var filterList: List<Product>


    private var productListener = object: ProductListener {
        override fun sendProductID(id: String) {

            val detailFragment = DetailFragment()
            val bundle = Bundle()

            bundle.putString("productID", id)
            detailFragment.arguments = bundle

            view?.let{
                (activity as MainActivity).openFragment(detailFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = HomeViewModel()

        val recycler: RecyclerView = view.findViewById(R.id.recycler)


        viewModel.productList.observe(
            viewLifecycleOwner,{
                adapter = ProductAdapter(it, requireContext(), productListener)
                filterList = it
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(context)
            }
        )

        view.findViewById<androidx.appcompat.widget.SearchView>(R.id.inputSearch).setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                    val filteredList: List<Product> = filterList.filter { it.name == query || it.description == query}
                    adapter.updateList(filteredList)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty())
                    adapter.updateList(filterList)
                return false
            }
        })

        viewModel.getProducts()
    }
}