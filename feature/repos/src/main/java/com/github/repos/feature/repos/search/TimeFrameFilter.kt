package com.github.repos.feature.repos.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.repos.feature.repos.R

enum class TimeFrameFilter {
    CREATED_IN_LAST_DAY,
    CREATED_IN_LAST_WEEK,
    CREATED_IN_LAST_MONTH,
}

@Composable
fun TimeFrameFilter.title(): String {
    return when (this) {
        TimeFrameFilter.CREATED_IN_LAST_DAY -> stringResource(R.string.feature_repos_created_in_last_day)
        TimeFrameFilter.CREATED_IN_LAST_WEEK -> stringResource(R.string.feature_repos_created_in_last_week)
        TimeFrameFilter.CREATED_IN_LAST_MONTH -> stringResource(R.string.feature_repos_created_in_last_month)
    }
}

@Composable
fun TimeFrameFilter.shortTitle(): String {
    return when (this) {
        TimeFrameFilter.CREATED_IN_LAST_DAY -> stringResource(R.string.feature_repos_last_day)
        TimeFrameFilter.CREATED_IN_LAST_WEEK -> stringResource(R.string.feature_repos_last_week)
        TimeFrameFilter.CREATED_IN_LAST_MONTH -> stringResource(R.string.feature_repos_last_month)
    }
}