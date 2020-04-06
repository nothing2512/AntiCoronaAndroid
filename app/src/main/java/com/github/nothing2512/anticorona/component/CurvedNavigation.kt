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

package com.github.nothing2512.anticorona.component

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.github.nothing2512.anticorona.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * [CurvedNavigation] class component
 * @author Robet Atiq Maulana Rifqi
 */
class CurvedNavigation : BottomNavigationView {

    /**
     * Defining Read - Write Variable
     */
    private var path = Path()
    private var paint = Paint()
    private var size = 0
    private var deviceWidth = 0
    private var parent: View? = null

    /**
     * Defining Read Only Variable
     */
    private val radius = 56

    private val firstStart = Point()
    private val firstEnd = Point()
    private val firstControl1 = Point()
    private val firstControl2 = Point()

    private val secondStart = Point()
    private val secondEnd = Point()
    private val secondControl1 = Point()
    private val secondControl2 = Point()

    /**
     * constructor
     * @param context
     */
    constructor(context: Context) : super(context)

    /**
     * constructor
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CurvedNavigation)
        size = ta.getInt(R.styleable.CurvedNavigation_size, 3)
        ta.recycle()
    }

    /**
     * constructor
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CurvedNavigation)
        size = ta.getInt(R.styleable.CurvedNavigation_size, 3)
        ta.recycle()
    }

    init {

        /**
         * Styling Background
         */
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    /**
     * Function onDraw
     * @see BottomNavigationView.onDraw
     * @param canvas?
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * Resetting Path to default
         */
        path.reset()
        path.moveTo(0F, 0F)
        path.lineTo(firstStart.x.toFloat(), firstStart.y.toFloat())

        /**
         * Set first curved path
         * @see Path.cubicTo
         */
        path.cubicTo(
            firstControl1.x.toFloat(),
            firstControl1.y.toFloat(),
            firstControl2.x.toFloat(),
            firstControl2.y.toFloat(),
            firstEnd.x.toFloat(),
            firstEnd.y.toFloat()
        )

        /**
         * Set end curved path
         * @see Path.cubicTo
         */
        path.cubicTo(
            secondControl1.x.toFloat(),
            secondControl1.y.toFloat(),
            secondControl2.x.toFloat(),
            secondControl2.y.toFloat(),
            secondEnd.x.toFloat(),
            secondEnd.y.toFloat()
        )

        /**
         * set width and height of path
         * @see Path.lineTo
         */
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        /**
         * Drawing Curved Path
         * @see Canvas.drawPath
         */
        canvas?.drawPath(path, paint)
    }

    /**
     * Function onSizeChanged
     * @see BottomNavigationView.onSizeChanged
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        /**
         * Drawing Curved Point
         * @see draw
         */
        draw(1, 2)
    }

    /**
     * Function draw
     * using to drawing curved point
     *
     * @param i
     * @param j
     */
    private fun draw(i: Int, j: Int = 1) {

        /**
         * Creating First Path Point
         * @see Point.set
         */
        firstStart.set((width * i / j) - (radius * 3), 0)
        firstEnd.set(width * i / j, radius + (radius / 4))

        firstControl1.set(firstStart.x + radius + (radius / 4), firstStart.y)
        firstControl2.set(firstEnd.x - radius, firstEnd.y)

        /**
         * Creating End Path Point
         * @see Point.set
         */
        secondStart.set(firstEnd.x, firstEnd.y)
        secondEnd.set((width * i / j) + (radius * 3), 0)

        secondControl1.set(secondStart.x + radius, secondStart.y)
        secondControl2.set(secondEnd.x - (radius + (radius / 4)), secondEnd.y)
    }

    /**
     * Function setup
     * Setting deviceWidth and parent view
     *
     * @param deviceWidth
     * @param parent
     */
    fun setup(deviceWidth: Int, parent: View) {
        this.deviceWidth = deviceWidth
        this.parent = parent
    }

    /**
     * Function update
     * updating curved position
     *
     * @param view
     * @param position
     *
     * @throws IndexOutOfBoundsException
     */
    fun update(view: View, position: Int) {

        /**
         * Check if position index is out of bound or not
         * @see IndexOutOfBoundsException
         */
        if (position > size) throw IndexOutOfBoundsException("CurvedNavigation: Position must less than size")

        /**
         * Getting Curved Position
         */
        val len = size * 2
        val index = ((position - 1) * 2) + 1

        /**
         * Drawing Curved Point
         * @see draw
         */
        draw(index, len)

        /**
         * Getting Button Position
         */
        val viewX = view.width / 2
        val deviceX = ((deviceWidth * index / len) - (parent?.width ?: 0 / 2)).toFloat()

        /**
         * Moving Button Position
         */
        parent?.let { if (it.x > 0f) it.x = deviceX + viewX }
    }
}