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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.repos.core.designsystem.component.GithubBackground
import com.github.repos.core.designsystem.component.scrollbar.DraggableScrollbar
import com.github.repos.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.github.repos.core.designsystem.component.scrollbar.scrollbarState
import com.github.repos.core.designsystem.theme.GithubTheme
import com.github.repos.core.model.data.Repository
import com.github.repos.core.ui.DevicePreviews
import com.github.repos.core.ui.pagingItems
import com.github.repos.core.ui.states.EmptyScreen
import com.github.repos.feature.repos.search.SearchToolbar
import com.github.repos.feature.repos.search.TimeFrameFilter
import kotlinx.coroutines.flow.flowOf

@Composable
fun ReposRoute(
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    viewModel: ReposViewModel = hiltViewModel(),
) {
    val repositoriesPagingItems = viewModel.repositoriesPagingData.collectAsLazyPagingItems()
    val selectedRepoId by viewModel.selectedRepoId.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.timeFrameFilter.collectAsStateWithLifecycle()

    ReposScreen(
        repositoriesPagingItems = repositoriesPagingItems,
        selectedRepoId = selectedRepoId,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        toggleFavourite = viewModel::toggleFavourite,
        onRepoClick = {
            viewModel.onRepoClick(it)
            onRepoClick(it)
        },
        highlightSelectedRepo = highlightSelectedRepo,
        modifier = modifier,
        selectedFilter = selectedFilter,
        onFilterSelected = viewModel::onFilterSelected,
    )
}

@Composable
fun ReposScreen(
    repositoriesPagingItems: LazyPagingItems<Repository>,
    selectedRepoId: Long?,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    selectedFilter: TimeFrameFilter = TimeFrameFilter.CREATED_IN_LAST_DAY,
    onFilterSelected: (TimeFrameFilter) -> Unit = {},
    toggleFavourite: (Long) -> Unit,
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    shouldShowFilter: Boolean = true,
) {
    val isSearching = repositoriesPagingItems.loadState.refresh is LoadState.Loading &&
        repositoriesPagingItems.itemCount > 0

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
                    items = repositoriesPagingItems,
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
                            onFavouriteToggleClick = { toggleFavourite(repoId) },
                            ownerAvatarUrl = repo.ownerAvatarUrl,
                        )
                    },
                    emptyState = {
                        EmptyScreen()
                    },
                )

                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
            val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = repositoriesPagingItems.itemCount,
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
                    itemsAvailable = repositoriesPagingItems.itemCount,
                ),
            )
        }
    }
}

@DevicePreviews
@Composable
fun PreviewReposScreen() {
    GithubTheme {
        GithubBackground {
            ReposScreen(
                toggleFavourite = {},
                onRepoClick = {},
                repositoriesPagingItems = flowOf(PagingData.from(emptyList<Repository>())).collectAsLazyPagingItems(),
                selectedRepoId = null,
                highlightSelectedRepo = false,
                searchQuery = "",
                onSearchQueryChanged = {},
                selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY,
                onFilterSelected = {},
                shouldShowFilter = true,
            )
        }
    }
}

@DevicePreviews
@Composable
fun ReposScreenLoading() {
    GithubTheme {
        GithubBackground {
            ReposScreen(
                toggleFavourite = {},
                onRepoClick = {},
                repositoriesPagingItems = flowOf(PagingData.from(emptyList<Repository>())).collectAsLazyPagingItems(),
                selectedRepoId = null,
                highlightSelectedRepo = false,
                searchQuery = "",
                onSearchQueryChanged = {},
                selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY,
                onFilterSelected = {},
                shouldShowFilter = true,
            )
        }
    }
}

@DevicePreviews
@Composable
fun ReposScreenEmpty() {
    GithubTheme {
        GithubBackground {
            ReposScreen(
                toggleFavourite = {},
                onRepoClick = {},
                repositoriesPagingItems = flowOf(PagingData.from(emptyList<Repository>())).collectAsLazyPagingItems(),
                selectedRepoId = null,
                highlightSelectedRepo = false,
                searchQuery = "",
                onSearchQueryChanged = {},
                selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY,
                onFilterSelected = {},
                shouldShowFilter = true,
            )
        }
    }
}
