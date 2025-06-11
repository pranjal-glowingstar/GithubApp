package com.apps.assignment.common

import androidx.compose.foundation.lazy.LazyListState

object AppUtils {

    object AppConstants{
        const val EMPTY = ""
        const val NO_INFO = "<NA>"
    }

    fun isScrolledToTheEnd(
        lazyListState: LazyListState,
    ): Boolean {
        val layoutInfo = lazyListState.layoutInfo
        if (layoutInfo.visibleItemsInfo.isEmpty()) return true

        val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
        val totalItems = layoutInfo.totalItemsCount

        return lastVisibleItem.index >= totalItems - 1
    }
}