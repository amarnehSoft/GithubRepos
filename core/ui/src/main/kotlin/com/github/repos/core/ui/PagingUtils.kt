package com.github.repos.core.ui

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.github.repos.core.ui.states.ErrorRow
import com.github.repos.core.ui.states.ErrorScreen
import com.github.repos.core.ui.states.LoadingScreen

fun <T : Any> LazyListScope.pagingItems(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyItemScope.(index: Int, value: T?) -> Unit,
    emptyState: (@Composable LazyItemScope.() -> Unit)? = null,
    endOfPaginationState: (@Composable LazyItemScope.() -> Unit)? = null,
    initialLoadState: @Composable LazyItemScope.() -> Unit = {
        LoadingScreen(modifier = Modifier.fillParentMaxSize())
    },
    nextPageLoadingState: @Composable LazyItemScope.() -> Unit = {
        LoadingScreen()
    },
    initialErrorState: @Composable LazyItemScope.() -> Unit = {
        ErrorScreen(
            Modifier.fillParentMaxSize(),
            onRetry = {
                items.retry()
            },
        )
    },
    nextPageLoadingErrorState: @Composable LazyItemScope.() -> Unit = {
        ErrorRow(
            onRetry = {
                items.retry()
            },
        )
    },
) {
    val refreshLoadState = items.loadState.source.refresh
    val appendLoadState = items.loadState.source.append

    when {
        refreshLoadState is LoadState.NotLoading &&
            appendLoadState is LoadState.NotLoading &&
            appendLoadState.endOfPaginationReached &&
            items.itemCount == 0 -> {
            // data loaded and we had 0 results...
            emptyState?.let {
                item { emptyState() }
            }
        }

        refreshLoadState is LoadState.Loading && items.itemCount == 0 -> {
            // first fetch of data is loading...
            item { initialLoadState() }
        }

        refreshLoadState is LoadState.Error -> {
            // error occurred with initial loading of data...
            item { initialErrorState() }
        }

        else -> {
            items(
                count = items.itemCount,
                key = items.itemKey(key = key),
                contentType = items.itemContentType(),
                itemContent = {
                    itemContent(it, items[it])
                }
            )
        }
    }

    when {
        appendLoadState is LoadState.Loading -> {
            // next page of data is loading...
            item { nextPageLoadingState() }
        }

        appendLoadState is LoadState.Error -> {
            // error occurred when loading next page of data...
            item { nextPageLoadingErrorState() }
        }

        refreshLoadState is LoadState.NotLoading &&
            appendLoadState is LoadState.NotLoading &&
            appendLoadState.endOfPaginationReached -> {
            // last page
            endOfPaginationState?.let {
                item { endOfPaginationState() }
            }
        }
    }
}

fun <T : Any> LazyPagingItems<T>.shouldShowPullRefreshIndicator() =
    loadState.source.refresh == LoadState.Loading && itemCount > 0

fun <T : Any> LazyPagingItems<T>.shouldEnablePullRefresh() =
    loadState.source.refresh != LoadState.Loading && loadState.source.refresh !is LoadState.Error