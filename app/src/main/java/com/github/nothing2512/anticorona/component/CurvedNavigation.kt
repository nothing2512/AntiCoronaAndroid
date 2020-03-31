package com.github.nothing2512.anticorona.component

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class CurvedNavigation : BottomNavigationView {

    private var path = Path()
    private var paint = Paint()

    private val radius = 56

    private val firstStart = Point()
    private val firstEnd = Point()
    private val firstControl1 = Point()
    private val firstControl2 = Point()

    private val secondStart = Point()
    private val secondEnd = Point()
    private val secondControl1 = Point()
    private val secondControl2 = Point()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.reset()
        path.moveTo(0F, 0F)
        path.lineTo(firstStart.x.toFloat(), firstStart.y.toFloat())

        path.cubicTo(
            firstControl1.x.toFloat(),
            firstControl1.y.toFloat(),
            firstControl2.x.toFloat(),
            firstControl2.y.toFloat(),
            firstEnd.x.toFloat(),
            firstEnd.y.toFloat()
        )

        path.cubicTo(
            secondControl1.x.toFloat(),
            secondControl1.y.toFloat(),
            secondControl2.x.toFloat(),
            secondControl2.y.toFloat(),
            secondEnd.x.toFloat(),
            secondEnd.y.toFloat()
        )

        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
        canvas?.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        firstStart.set((width / 2) - (radius * 3), 0)
        firstEnd.set(width / 2, radius + (radius / 4))

        secondStart.set(firstEnd.x, firstEnd.y)
        secondEnd.set((width / 2) + (radius * 3), 0)

        firstControl1.set(firstStart.x + radius + (radius / 4), firstStart.y)
        firstControl2.set(firstEnd.x - radius, firstEnd.y)

        secondControl1.set(secondStart.x + radius, secondStart.y)
        secondControl2.set(secondEnd.x - (radius + (radius / 4)), secondEnd.y)
    }

    fun draw(i: Int, j: Int = 1) {
        firstStart.set((width * i / j) - (radius * 3), 0)
        firstEnd.set(width * i / j, radius + (radius / 4))

        secondStart.set(firstEnd.x, firstEnd.y)
        secondEnd.set((width * i / j) + (radius * 3), 0)

        firstControl1.set(firstStart.x + radius + (radius / 4), firstStart.y)
        firstControl2.set(firstEnd.x - radius, firstEnd.y)

        secondControl1.set(secondStart.x + radius, secondStart.y)
        secondControl2.set(secondEnd.x - (radius + (radius / 4)), secondEnd.y)
    }
}