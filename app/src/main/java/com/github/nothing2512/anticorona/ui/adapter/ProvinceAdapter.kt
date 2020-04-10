/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nothing2512.anticorona.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ItemProvinceBinding
import com.github.nothing2512.anticorona.utils.animateValue
import com.github.nothing2512.anticorona.utils.getBinding

/**
 * [ProvinceAdapter] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [data], [openDialog]
 */
class ProvinceAdapter(
    private val data: List<CaseResponse>,
    private val openDialog: (CaseResponse) -> Unit
) : RecyclerView.Adapter<ProvinceAdapter.MainHolder>() {

    private var reloadData = ArrayList<Boolean>()

    /**
     * set recycler view item has stable id
     * @see RecyclerView.Adapter.setHasStableIds
     */
    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    /**
     * Creating recycler view holder
     *
     * @param parent
     * @param viewType
     *
     * @see RecyclerView.Adapter.onCreateViewHolder
     * @see MainHolder
     *
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(getBinding(R.layout.item_province, parent), openDialog)

    /**
     * Getting data size for loopin item count
     * @see RecyclerView.Adapter.getItemCount
     */
    override fun getItemCount() = data.size

    /**
     * triggering function to binding data to view when view is inflated
     *
     * @param holder
     * @param position
     *
     * @see RecyclerView.Adapter.onBindViewHolder
     */
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        try {
            if (reloadData[position]) holder.reload(data[position])
        } catch (e: Exception) {
            holder.bind(data[position])
            reloadData.add(position, true)
        }
    }

    /**
     * MainHolder class
     *
     * @constructor [binding], [openDialog]
     *
     * @see ItemProvinceBinding
     * @see RecyclerView.ViewHolder
     */
    class MainHolder(
        private val binding: ItemProvinceBinding,
        private val openDialog: (CaseResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binding data to view
         * @param item
         */
        fun bind(item: CaseResponse) {
            binding.item = item
            binding.itemProvinceCase.animateValue(item.cases.toString())
            binding.root.setOnClickListener { openDialog(item) }
        }

        /**
         * Binding data when reload view
         * @param item
         */
        fun reload(item: CaseResponse) {
            binding.item = item
            binding.itemProvinceCase.text = item.cases.toString()
            binding.root.setOnClickListener { openDialog(item) }
        }
    }
}
