package com.github.nothing2512.anticorona.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.databinding.ItemFaqsBinding
import com.github.nothing2512.anticorona.utils.getBinding

class FaqsAdapter(
    private val data: List<FaqsResponse>,
    private val openDialog: (FaqsResponse) -> Unit
): RecyclerView.Adapter<FaqsAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(getBinding(R.layout.item_faqs, parent), openDialog)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    class MainHolder(
        private val binding: ItemFaqsBinding,
        private val openDialog: (FaqsResponse) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FaqsResponse) {
            binding.item = item

            binding.root.setOnClickListener { openDialog(item) }
        }
    }
}