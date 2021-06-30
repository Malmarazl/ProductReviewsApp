package com.example.productreviewsapp.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productreviewsapp.R
import com.example.productreviewsapp.models.Review

class ReviewAdapter(private var reviewList: List<Review>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val review = reviewList[position]

        holder.comment.text = review.text
        holder.locale.text = review.locale
        holder.rating.text = "Rating is " + review.rating
    }

    override fun getItemCount(): Int = reviewList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment: TextView = view.findViewById(R.id.review_comment)
        val locale: TextView = view.findViewById(R.id.review_locale)
        val rating: TextView = view.findViewById(R.id.review_rating)
    }


}