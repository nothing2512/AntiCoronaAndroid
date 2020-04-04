package com.github.nothing2512.anticorona.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ItemCountryBinding
import com.github.nothing2512.anticorona.utils.getBinding

class CountryAdapter(
    private val data: List<CaseResponse>,
    private val openDialog: (CaseResponse) -> Unit
) : RecyclerView.Adapter<CountryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(getBinding(R.layout.item_country, parent), openDialog)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    class MainHolder(
        private val binding: ItemCountryBinding,
        private val openDialog: (CaseResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CaseResponse) {
            binding.item = item
            binding.root.setOnClickListener { openDialog(item) }
        }
    }
}