package com.lazuardy.zonacoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lazuardy.zonacoding.data.ArticlesResponseItem
import kotlinx.android.synthetic.main.adapter_article.view.*
import kotlin.collections.ArrayList

class ArticleAdapter (var results: ArrayList<ArticlesResponseItem>, val listener: OnAdapterListener):
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            LayoutInflater.from( parent.context ).inflate( R.layout.adapter_article,
                parent, false)
        )

        override fun getItemCount() = results.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val result = results[position]
//            holder.view.textView.text = result.title
            Glide.with(holder.view)
                .load(result.thumbnail)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(holder.view.imageView)
            holder.view.setOnClickListener { listener.onClick( result ) }
        }

        class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

        fun setData(data: List<ArticlesResponseItem>){
            this.results.clear()
            this.results.addAll(data)
            notifyDataSetChanged()
        }

    interface OnAdapterListener {
        fun onClick(result: ArticlesResponseItem)
    }
}