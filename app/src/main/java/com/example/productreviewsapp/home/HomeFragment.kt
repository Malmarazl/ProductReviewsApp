package com.example.productreviewsapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
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
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = HomeViewModel()
        setObservers(view)
        inputSearchFunctionality(view)
    }

    private fun setObservers(view: View) {
        val recyclerProductList: RecyclerView = view.findViewById(R.id.recyclerProductList)

        viewModel.productList.observe(
            viewLifecycleOwner,{
                adapter = ProductAdapter(it, requireContext(), productListener)
                filterList = it
                recyclerProductList.adapter = adapter
                recyclerProductList.layoutManager = LinearLayoutManager(context)
            }
        )

        viewModel.error.observe(
            viewLifecycleOwner, {
                view.findViewById<ConstraintLayout>(R.id.screen_error).visibility = View.VISIBLE

                view.findViewById<Button>(R.id.button_retry).setOnClickListener {
                    viewModel.getProducts()
                    view.findViewById<ConstraintLayout>(R.id.screen_error).visibility = View.GONE
                }
            }
        )
    }

    private fun inputSearchFunctionality(view: View) {

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