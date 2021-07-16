package com.example.productreviewsapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productreviewsapp.MainActivity
import com.example.productreviewsapp.R
import com.example.productreviewsapp.detail.DetailFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.screen_error.*

class HomeFragment: Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var adapter: ProductAdapter

    private var productListener = object: ProductListener {
        override fun sendProductID(id: String) {

            val detailFragment = DetailFragment()
            val bundle = Bundle()

            bundle.putString(PRODUCT_ID, id)
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
        setObservers()
        inputSearchFunctionality(view)
        viewModel.getProducts()
    }

    private fun setObservers() {
        viewModel.productList.observe(
            viewLifecycleOwner,{
                adapter = ProductAdapter(it, requireContext(), productListener)
                recyclerProductList.adapter = adapter
                recyclerProductList.layoutManager = LinearLayoutManager(context)
            }
        )

        viewModel.filtereredList.observe(
            viewLifecycleOwner, {
                adapter.updateList(it)
            }
        )

        viewModel.error.observe(
            viewLifecycleOwner, {
                screen_error.visibility = View.VISIBLE

                button_retry.setOnClickListener {
                    viewModel.getProducts()
                    screen_error.visibility = View.GONE
                }
            }
        )
    }

    private fun inputSearchFunctionality(view: View) {
       inputSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                viewModel.filterList(newText)

                return false
            }
        })
    }

    companion object {
        const val PRODUCT_ID = "productID"
    }
}