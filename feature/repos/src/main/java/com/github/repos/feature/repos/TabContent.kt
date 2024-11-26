package com.github.repos.feature.repos

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.github.repos.core.designsystem.component.scrollbar.DraggableScrollbar
import com.github.repos.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.github.repos.core.designsystem.component.scrollbar.scrollbarState
import com.github.repos.core.model.data.Repository
import com.github.repos.core.ui.pagingItems
import com.github.repos.core.ui.states.EmptyScreen
import com.github.repos.feature.repos.search.SearchToolbar
import com.github.repos.feature.repos.search.TimeFrameFilter

@Composable
fun ReposTabContent(
    repositories: LazyPagingItems<Repository>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    selectedFilter: TimeFrameFilter,
    onFilterSelected: (TimeFrameFilter) -> Unit,
    onRepoClick: (Long) -> Unit,
    onSaveButtonClick: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withBottomSpacer: Boolean = true,
    selectedRepoId: Long? = null,
    highlightSelectedRepo: Boolean = false,
    shouldShowFilter: Boolean,
) {
    val isSearching =
        repositories.loadState.refresh is LoadState.Loading && repositories.itemCount > 0

    Column {
        SearchToolbar(
            isSearching = isSearching,
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
            selectedFilter = selectedFilter,
            onFilterSelected = onFilterSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shouldShowFilter = shouldShowFilter,
        )

        Box(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            val scrollableState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                state = scrollableState,
            ) {
                pagingItems(
                    items = repositories,
                    key = { it.id },
                    itemContent = { _, repo ->
                        val repoId = repo?.id ?: return@pagingItems
                        val isSelected = highlightSelectedRepo && repoId == selectedRepoId
                        RepositoryItem(
                            name = repo.name,
                            ownerUserName = repo.ownerUsername,
                            onClick = { onRepoClick(repoId) },
                            isSelected = isSelected,
                            description = repo.description,
                            starsCount = repo.stargazersCount,
                            isFavourite = repo.isFavourite,
                            onFavouriteToggleClick = { onSaveButtonClick(repoId, it) },
                            ownerAvatarUrl = repo.ownerAvatarUrl,
                        )
                    },
                    emptyState = {
                        EmptyScreen()
                    },
                )

                if (withBottomSpacer) {
                    item {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                    }
                }
            }
            val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = repositories.itemCount,
            )
            scrollableState.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 2.dp)
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = scrollableState.rememberDraggableScroller(
                    itemsAvailable = repositories.itemCount,
                ),
            )
        }
    }
}