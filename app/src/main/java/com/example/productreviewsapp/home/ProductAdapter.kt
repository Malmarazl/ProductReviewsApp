package com.example.productreviewsapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R
import com.example.productreviewsapp.models.Product
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter (
    private var itemList: List<Product>,
    private val context: Context,
    private val productListener: ProductListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val item = itemList[position]

        holder.description.text = item.description
        holder.name.text = item.name
        holder.price.text = item.price.toString() +  item.currency

        Glide
            .with(context)
            .load(item.imgUrl)
            .into(holder.image)

        holder.card.setOnClickListener {
            productListener.sendProductID(item.id)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.product_name)
        val description: TextView = view.findViewById<TextView>(R.id.product_description)
        val price: TextView = view.findViewById<TextView>(R.id.product_price)
        val image: ImageView = view.findViewById<ImageView>(R.id.product_image)
        val card: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.card)
    }

    fun updateList(filteredList: List<Product>) {
        itemList = filteredList
        notifyDataSetChanged()
    }
}