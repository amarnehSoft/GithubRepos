package com.github.repos.feature.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.repos.feature.repos.ReposScreen

@Composable
fun FavouritesRoute(
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {
    val repositoriesPagingItems = viewModel.repositoriesPagingData.collectAsLazyPagingItems()
    val selectedRepoId by viewModel.selectedRepoId.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    ReposScreen(
        repositoriesPagingItems = repositoriesPagingItems,
        selectedRepoId = selectedRepoId,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        addToFavourites = { repoId, _ -> viewModel.addToFavourites(repoId) },
        onRepoClick = {
            viewModel.onRepoClick(it)
            onRepoClick(it)
        },
        highlightSelectedRepo = highlightSelectedRepo,
        modifier = modifier,
        shouldShowFilter = false,
    )
}