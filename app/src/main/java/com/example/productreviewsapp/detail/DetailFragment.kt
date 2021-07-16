package com.example.productreviewsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R
import com.example.productreviewsapp.helpers.DialogReviews
import com.example.productreviewsapp.models.Review
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.screen_error.*


class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel
    lateinit var adapter: ReviewAdapter
    var productID = ""

    private val newReviewListener = object: NewReviewListener {
        override fun sendNewReview(text: String, starReview: Float) {

            val newReview = Review(
                text = text,
                productId = productID,
                locale = "",
                rating = starReview
            )

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

        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        setObservers(view)

        buttonAddReview.setOnClickListener {
           DialogReviews(newReviewListener).show(childFragmentManager, "")
        }
    }

    private fun setObservers(view: View) {
        viewModel.product.observe(
            viewLifecycleOwner, {
                product_name.text = it.name
                product_description.text = it.description
                val currentPrice = it.price.toString() + it.currency
                product_price.text = currentPrice

                Glide
                    .with(this)
                    .load(it.imgUrl)
                    .into(product_image)

                viewModel.getReviews(it.id)
            }
        )

        viewModel.reviewList.observe(
            viewLifecycleOwner, {

                adapter = ReviewAdapter(it)
                recyclerReviewsList.adapter = adapter
                recyclerReviewsList.layoutManager = LinearLayoutManager(context)
            }
        )

        viewModel.review.observe(
            viewLifecycleOwner, {
                viewModel.getReviews(productID)
            })

        viewModel.error.observe(
            viewLifecycleOwner, {
                        screen_error_detail.visibility = View.VISIBLE
                        button_retry.setOnClickListener {
                            viewModel.getProduct(productID)
                            screen_error_detail.visibility = View.GONE
                        }
            })
    }

    companion object {
        const val PRODUCT_ID = "productID"
    }
}