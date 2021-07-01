package com.example.productreviewsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R
import com.example.productreviewsapp.helpers.DialogReviews
import com.example.productreviewsapp.models.Review


class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel
    lateinit var adapter: ReviewAdapter
    var productID = ""

    private val newReviewListener = object: NewReviewListener {
        override fun sendNewReview(text: String, starReview: Float) {

            val newReview = Review()

            newReview.text = text
            newReview.productId = productID
            newReview.locale = ""
            newReview.rating = starReview

            viewModel.addNewReview(newReview)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = DetailViewModel()

        val bundle = this.arguments
        if (bundle != null) {
            productID = bundle.getString(PRODUCT_ID, "0")
            viewModel.getProduct(productID)
        }

        val buttonBack = view.findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        setObservers(view)

        val buttonAddReview = view.findViewById<Button>(R.id.buttonAddReview)
        buttonAddReview.setOnClickListener {
           DialogReviews(newReviewListener).show(childFragmentManager, "")
        }
    }

    private fun setObservers(view: View) {
        val recyclerReviewList: RecyclerView = view.findViewById(R.id.recyclerReviewsList)

        viewModel.product.observe(
            viewLifecycleOwner, {
                view.findViewById<TextView>(R.id.product_name).text = it.name
                view.findViewById<TextView>(R.id.product_description).text = it.description
                val currentPrice = it.price.toString() + it.currency
                view.findViewById<TextView>(R.id.product_price).text = currentPrice

                Glide
                    .with(this)
                    .load(it.imgUrl)
                    .into(view.findViewById(R.id.product_image))

                viewModel.getReviews(it.id)
            }
        )

        viewModel.reviewList.observe(
            viewLifecycleOwner, {

                adapter = ReviewAdapter(it)
                recyclerReviewList.adapter = adapter
                recyclerReviewList.layoutManager = LinearLayoutManager(context)
            }
        )

        viewModel.review.observe(
            viewLifecycleOwner, {
                viewModel.getReviews(productID)
            })

        viewModel.error.observe(
            viewLifecycleOwner, {
                viewModel.error.observe(
                    viewLifecycleOwner, {
                        view.findViewById<ConstraintLayout>(R.id.screen_error_detail).visibility = View.VISIBLE
                        view.findViewById<Button>(R.id.button_retry).setOnClickListener {
                            viewModel.getProduct(productID)
                            viewModel.getReviews(productID)
                            view.findViewById<ConstraintLayout>(R.id.screen_error_detail).visibility = View.GONE
                        }
                    }
                )
            })
    }

    companion object {
        const val PRODUCT_ID = "productID"
    }
}