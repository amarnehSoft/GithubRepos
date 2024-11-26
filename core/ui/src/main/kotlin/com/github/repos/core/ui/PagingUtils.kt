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
            modifier = Modifier.fillParentMaxSize(),
            onRetry = { items.retry() },
        )
    },
    nextPageLoadingErrorState: @Composable LazyItemScope.() -> Unit = {
        ErrorRow(onRetry = { items.retry() })
    },
) {
    val refreshLoadState = items.loadState.source.refresh
    val appendLoadState = items.loadState.source.append

    // Empty state handling
    if (refreshLoadState is LoadState.NotLoading &&
        appendLoadState is LoadState.NotLoading &&
        appendLoadState.endOfPaginationReached &&
        items.itemCount == 0) {
        emptyState?.let {
            item { emptyState() }
        }
        return
    }

    // Initial loading state handling
    if (refreshLoadState is LoadState.Loading && items.itemCount == 0) {
        item { initialLoadState() }
        return
    }

    // Initial error state handling
    if (refreshLoadState is LoadState.Error) {
        item { initialErrorState() }
        return
    }

    // Display items
    items(
        count = items.itemCount,
        key = key?.let { items.itemKey(key = it) },
        contentType = items.itemContentType(),
        itemContent = { index -> itemContent(index, items[index]) }
    )

    // Next page loading state handling
    if (appendLoadState is LoadState.Loading) {
        item { nextPageLoadingState() }
    }

    // Next page error state handling
    if (appendLoadState is LoadState.Error) {
        item { nextPageLoadingErrorState() }
    }

    // End of pagination state handling
    if (refreshLoadState is LoadState.NotLoading &&
        appendLoadState is LoadState.NotLoading &&
        appendLoadState.endOfPaginationReached) {
        endOfPaginationState?.let {
            item { endOfPaginationState() }
        }
    }
}