package com.example.productreviewsapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productreviewsapp.R

class HomeFragment: Fragment() {

    lateinit var viewModel: HomeViewModel

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
                val adapter = ItemList(it, requireContext())
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(context)
            }
        )

        viewModel.getProducts()
    }
}