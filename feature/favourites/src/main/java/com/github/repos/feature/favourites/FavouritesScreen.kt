package com.github.repos.feature.favourites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.repos.feature.repos.ReposScreen

@Composable
fun FavouritesRoute(
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReposScreen(
        uiState = uiState,
        addToFavourites = { _, _ -> }, // TODO
        onRepoClick = {
            viewModel.onRepoClick(it)
            onRepoClick(it)
        },
        highlightSelectedRepo = highlightSelectedRepo,
        modifier = modifier,
    )
}