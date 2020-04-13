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
import com.github.nothing2512.anticorona.BuildConfig
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import com.github.nothing2512.anticorona.databinding.ItemNewsBinding
import com.github.nothing2512.anticorona.ui.news.NewsActivity
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.goto
import com.github.nothing2512.anticorona.utils.openBrowser

/**
 * [NewsAdapter] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [data]
 */
class NewsAdapter(private val data: List<NewsResponse>) :
    RecyclerView.Adapter<NewsAdapter.MainHolder>() {

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
        MainHolder(getBinding(R.layout.item_news, parent))

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
        holder.bind(data[position])
    }

    /**
     * MainHolder class
     *
     * @constructor [binding]
     *
     * @see ItemNewsBinding
     * @see RecyclerView.ViewHolder
     */
    class MainHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binding data to view
         * @param item
         */
        fun bind(item: NewsResponse) {
            binding.item = item
            binding.root.setOnClickListener {
                binding.root.context.applicationContext.apply {
                    @Suppress("ConstantConditionIf")
                    if (BuildConfig.IS_BETA) goto(this, NewsActivity::class.java) {
                        putExtra(Constants.EXTRA_URL, item.url)
                    } else openBrowser(this, item.url)
                }
            }
        }
    }

}