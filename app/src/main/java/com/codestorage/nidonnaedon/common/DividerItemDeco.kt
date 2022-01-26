package com.codestorage.nidonnaedon.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.codestorage.nidonnaedon.R

/**
 * @param dividerRes divider resId
 * @param padding input value is dip
 */
class DividerItemDeco(private val context: Context?, orientation: Int = VERTICAL, @DrawableRes dividerRes: Int = R.drawable.list_vertical_divider, private val padding: Float = 0f):
    DividerItemDecoration(context, orientation) {
    var dividerDrawable: Drawable? = context?.let { ContextCompat.getDrawable(it, dividerRes) }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int = dpToPixel(padding)
        val right: Int = parent.width - dpToPixel(padding)

        val childCount: Int = parent.childCount
        for (i in 0 until childCount-1) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom: Int = top + (dividerDrawable?.intrinsicHeight ?:0)
            dividerDrawable?.setBounds(left, top, right, bottom)
            dividerDrawable?.draw(c)
        }
    }

    private fun dpToPixel(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding, context?.resources?.displayMetrics).toInt()
}