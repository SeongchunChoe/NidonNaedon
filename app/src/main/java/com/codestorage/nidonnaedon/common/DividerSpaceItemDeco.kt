package com.codestorage.nidonnaedon.common

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @param space divider 폭
 * @param addLast 마지막 라인에 divider 추가 여부
 */
class DividerSpaceItemDeco(
    private val context: Context?,
    private val orientation: Int = LinearLayoutManager.VERTICAL,
    private val space: Float = 0f,
    private val addLast: Boolean = false,
    private val addFirst: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    private val spacePx: Int

    init {
        spacePx = dpToPixel(space)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            if(addFirst) {
                if (orientation == LinearLayoutManager.VERTICAL)
                    outRect.top = spacePx
                else
                    outRect.left = spacePx
            }
            if(parent.adapter?.itemCount == 1){
                if(addLast){
                    if(orientation == LinearLayoutManager.VERTICAL)
                        outRect.bottom = spacePx
                    else
                        outRect.right = spacePx
                }
            }
        }else if(parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)){
            if(addLast){
                if(orientation == LinearLayoutManager.VERTICAL)
                    outRect.bottom = spacePx
                else
                    outRect.right = spacePx
            }
            if(orientation == LinearLayoutManager.VERTICAL)
                outRect.top = spacePx
            else
                outRect.left = spacePx
        }else{
            if(orientation == LinearLayoutManager.VERTICAL)
                outRect.top = spacePx
            else
                outRect.left = spacePx
        }
    }

    private fun dpToPixel(dp: Float): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context?.resources?.displayMetrics
    ).toInt()
}