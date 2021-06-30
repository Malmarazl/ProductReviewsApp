package com.example.productreviewsapp.detail

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R
import com.example.productreviewsapp.helpers.DialogReviews
import com.example.productreviewsapp.home.ProductAdapter
import com.example.productreviewsapp.models.Review


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
        var productID = ""

        if (bundle != null) {
            productID = bundle.getString("productID", "0")

            viewModel.getProduct(productID)
        }

        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


        var newReviewListener = object: NewReviewListener {
            override fun sendNewReview(newText: String, starReview: Float) {

                var newReview: Review = Review()

                newReview.text = newText
                newReview.productId = productID
                newReview.locale = ""
                newReview.rating = starReview

                viewModel.addNewReview(newReview)
            }
        }

        val recyclerReviewList: RecyclerView = view.findViewById(R.id.recyclerReviewsList)

        viewModel.product.observe(
            viewLifecycleOwner, {
                view.findViewById<TextView>(R.id.product_name).text = it.name
                view.findViewById<TextView>(R.id.product_description).text = it.description
                view.findViewById<TextView>(R.id.product_price).text = it.price.toString() + it.currency

                Glide
                    .with(this)
                    .load(it.imgUrl)
                    .into(view.findViewById<ImageView>(R.id.product_image))

                viewModel.getReviews(it.id)
            }
        )

        viewModel.reviewList.observe(
            viewLifecycleOwner, {
                val adapter = ReviewAdapter(it)
                recyclerReviewList.adapter = adapter
                recyclerReviewList.layoutManager = LinearLayoutManager(context)
            }
        )

        val buttonAddReview = view.findViewById<Button>(R.id.buttonAddReview)

        buttonAddReview.setOnClickListener {
           DialogReviews(newReviewListener).show(childFragmentManager, "")
        }
    }
}