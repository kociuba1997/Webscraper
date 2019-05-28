package com.newsscraper.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.AbsListView
import android.widget.GridView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ksoichiro.android.observablescrollview.ObservableListView

class ScrollableViewSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    private var swipeableChildren: AbsListView = GridView(context)

    fun addScrollableView(v: AbsListView) {
        swipeableChildren = v
    }

    override fun canChildScrollUp(): Boolean {
        return canViewScrollUp(swipeableChildren)
    }

    private fun canViewScrollUp(view: AbsListView): Boolean {
        return when (view) {
            is GridView -> view.getChildCount() > 0 && (view.getFirstVisiblePosition() > 0 || view.getChildAt(
                0
            ).top < view.getPaddingTop())
            is ObservableListView -> {
                val headerCount = view.headerViewsCount
                view.getChildCount() > headerCount + 1 && (view.getFirstVisiblePosition() > headerCount || view.getChildAt(
                    headerCount
                ).top < -SizeUtils.dp2px(56.0f))
            }
            else -> false
        }
    }
}
