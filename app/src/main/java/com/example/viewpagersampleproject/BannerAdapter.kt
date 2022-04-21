package com.example.viewpagersampleproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BannerAdapter(private val list: ArrayList<DataPage>) : RecyclerView.Adapter<BannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
//        holder.bind(list[position])

        // Endless Scroll
        holder.bind(list[position % list.size])
    }

    override fun getItemCount(): Int {
//        return list.size

        // Endless Scroll
        return Int.MAX_VALUE
    }
}

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val layout: RelativeLayout = view.findViewById(R.id.rl_layout)
    val text: TextView = view.findViewById(R.id.tv_title)

    fun bind(data: DataPage) {
        layout.setBackgroundResource(data.color)
        text.text = data.title
    }
}