package com.github.nothing2512.anticorona.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ItemProvinceBinding
import com.github.nothing2512.anticorona.utils.animate
import com.github.nothing2512.anticorona.utils.getBinding

class ProvinceAdapter(
    private val data: List<CaseResponse>,
    private val openDialog: (CaseResponse) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(getBinding(R.layout.item_province, parent), openDialog)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(data[position])
    }

    class MainHolder(
        private val binding: ItemProvinceBinding,
        private val openDialog: (CaseResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CaseResponse) {

            val value = try {
                item.cases / item.recovered
            } catch (_: Exception) {
                0
            }

            binding.item = item
            binding.itemProvinceSeekbar.apply {
                isEnabled = false
                animate(value)
            }

            binding.root.setOnClickListener { openDialog(item) }
        }
    }
}
