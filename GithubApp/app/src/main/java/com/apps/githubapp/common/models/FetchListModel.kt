package com.apps.githubapp.common.models

import com.google.gson.annotations.SerializedName

data class FetchListModel(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<UserSummary>
)
