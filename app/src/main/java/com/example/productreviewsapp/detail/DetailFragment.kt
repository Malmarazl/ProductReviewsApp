package com.example.productreviewsapp.detail

import android.os.Bundle
import android.transition.Explode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R


class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = DetailViewModel()

        val bundle = this.arguments

        if (bundle != null) {
            val productID = bundle.getString("productID", "0")

            viewModel.getProduct(productID)
        }

        view.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.product.observe(
            viewLifecycleOwner, {
                view.findViewById<TextView>(R.id.product_name).text = it.name
                view.findViewById<TextView>(R.id.product_description).text = it.description
                view.findViewById<TextView>(R.id.product_price).text = it.price.toString() + it.currency

                Glide
                    .with(this)
                    .load(it.imgUrl)
                    .into(view.findViewById<ImageView>(R.id.product_image))
            }
        )
    }

}