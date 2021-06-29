package com.example.productreviewsapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productreviewsapp.R
import com.example.productreviewsapp.models.Product

class ItemList (private val itemList: List<Product>, private val context: Context) : RecyclerView.Adapter<ItemList.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemList.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemList.ViewHolder, position: Int) {
        val item = itemList[position]

        holder.description.text = item.description
        holder.name.text = item.name
        holder.price.text = item.price.toString()

        Glide
            .with(context)
            .load(item.imgUrl)
            .centerCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.product_name)
        val description: TextView = view.findViewById<TextView>(R.id.product_description)
        val price: TextView = view.findViewById<TextView>(R.id.product_price)
        val image: ImageView = view.findViewById<ImageView>(R.id.product_image)

    }
}