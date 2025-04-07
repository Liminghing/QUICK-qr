package com.jkweyu.quickqr.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.jkweyu.quickqr.R
import kotlin.math.absoluteValue

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatTextView(context, attrs) {
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0)
        val lh = typedArray.getDimensionPixelSize(R.styleable.MyTextView_bezier_lineHeight, 1).toFloat()

        val ascentAbs = paint.fontMetricsInt.ascent.absoluteValue
        val spacer = ((lh - ascentAbs) / 2).coerceAtLeast(0f)

        firstBaselineToTopHeight = (ascentAbs + spacer).toInt()
        lastBaselineToBottomHeight = spacer.toInt()

        setLineSpacing(lh, 0f)
    }
}