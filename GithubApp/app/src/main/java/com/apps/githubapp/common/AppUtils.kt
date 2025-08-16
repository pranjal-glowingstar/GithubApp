package com.apps.githubapp.common

import androidx.compose.foundation.lazy.LazyListState

object AppConstants{
    const val EMPTY = ""
    const val NO_INFO = "<NA>"
    const val SEARCH_DEBOUNCE_TIME = 500L
    const val SEARCH_THRESHOLD = 3
    const val BASE_URL = "https://api.github.com/"
    const val DATABASE_NAME = "githubDatabase"
}

fun LazyListState.isScrolledToTheEnd(): Boolean {
    val layoutInfo = this.layoutInfo
    if (layoutInfo.visibleItemsInfo.isEmpty()) return true

    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
    val totalItems = layoutInfo.totalItemsCount

    return lastVisibleItem.index >= totalItems - 1
}
