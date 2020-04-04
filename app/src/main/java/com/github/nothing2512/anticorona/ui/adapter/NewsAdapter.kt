package com.github.nothing2512.anticorona.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import com.github.nothing2512.anticorona.databinding.ItemNewsBinding
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.openBrowser

class NewsAdapter(private val data: List<NewsResponse>) :
    RecyclerView.Adapter<NewsAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(getBinding(R.layout.item_news, parent))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NewsAdapter.MainHolder, position: Int) {
        holder.bind(data[position])
    }

    class MainHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsResponse) {
            binding.item = item

            binding.root.apply {
                setOnClickListener {
                    context.openBrowser(item.url)
                }
            }
        }
    }

}