package com.github.repos.feature.repos

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.repos.core.designsystem.component.NiaBackground
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.core.model.data.Repository
import com.github.repos.core.ui.DevicePreviews
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
        addToFavourites = viewModel::addToFavourites,
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
    addToFavourites: (Long, Boolean) -> Unit,
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    shouldShowFilter: Boolean = true,
) {
    ReposTabContent(
        repositories = repositoriesPagingItems,
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        onRepoClick = onRepoClick,
        onSaveButtonClick = addToFavourites,
        selectedRepoId = selectedRepoId,
        highlightSelectedRepo = highlightSelectedRepo,
        modifier = modifier,
        selectedFilter = selectedFilter,
        onFilterSelected = onFilterSelected,
        shouldShowFilter = shouldShowFilter,
    )
}

@Composable
private fun ReposEmptyScreen() {
    Text(text = stringResource(id = R.string.feature_repos_empty_header))
}

@Composable
private fun ReposErrorScreen() {
    Text(text = stringResource(id = R.string.feature_repos_error_header))
}

@DevicePreviews
@Composable
fun PreviewReposScreen() {
    NiaTheme {
        NiaBackground {
            ReposScreen(
                addToFavourites = { _, _ -> },
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
    NiaTheme {
        NiaBackground {
            ReposScreen(
                addToFavourites = { _, _ -> },
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
    NiaTheme {
        NiaBackground {
            ReposScreen(
                addToFavourites = { _, _ -> },
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
