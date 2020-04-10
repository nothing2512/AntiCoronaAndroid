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

package com.github.nothing2512.anticorona.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import com.github.nothing2512.anticorona.ui.news.NewsActivity
import java.net.URL

/**
 * [NotificationSender] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [context]
 */
class NotificationSender(private val context: Context) {

    /**
     * Declare news response variable
     * @see NewsResponse
     */
    private var item: NewsResponse? = null

    /**
     * Get collapsed view
     * @see RemoteViews
     */
    private val collapsedView: RemoteViews
        get() = RemoteViews(context.packageName, R.layout.notification_collapsed)

    /**
     * Get expanded views
     * @see RemoteViews
     */
    private val expandedView: RemoteViews
        get() {
            val view = RemoteViews(context.packageName, R.layout.notification_expanded)
            view.setTextViewText(R.id.tvNotifTitle, item?.title)

            /**
             * Set url to image to imageView
             * @see URL
             * @see BitmapFactory
             */
            URL(item?.image).apply {
                AsyncTask.execute {
                    val connection = openConnection()
                    connection.doInput = true
                    connection.connect()
                    view.setImageViewBitmap(
                        R.id.imNotifNews,
                        BitmapFactory.decodeStream(connection.getInputStream())
                    )
                }
            }

            return view
        }

    /**
     * send notification
     * @param item
     */
    fun send(item: NewsResponse?) {

        this.item = item

        /**
         * Set intent to news activity
         * @see Intent
         */
        val intent = Intent(context, NewsActivity::class.java).apply {
            putExtra(Constants.EXTRA_URL, item?.url)
            putExtra(Constants.EXTRA_STATUS, true)
        }

        /**
         * Set pending Intent to activity
         * @see PendingIntent
         */
        val pendingIntent = PendingIntent.getActivity(
            context,
            Constants.NOTIF_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        /**
         * Building notification
         * @see NotificationCompat.Builder
         */
        val builder = NotificationCompat.Builder(context, Constants.NOTIF_CHANNEL_ID)
            .setSmallIcon(R.drawable.covid)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.see_expanded))
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomBigContentView(expandedView)
            .setCustomContentView(collapsedView)
            .setContentIntent(pendingIntent)
            .build()

        /**
         * getting notification manager from system
         * send notification to ui
         * @see Context.getSystemService
         * @see NotificationManager.notify
         */
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Constants.NOTIF_ID, builder)
    }
}