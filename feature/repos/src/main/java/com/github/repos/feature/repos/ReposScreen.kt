package com.github.repos.feature.repos

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.github.repos.core.designsystem.component.NiaBackground
import com.github.repos.core.designsystem.component.NiaLoadingWheel
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.core.ui.DevicePreviews

@Composable
fun ReposRoute(
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
    viewModel: ReposViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReposScreen(
        uiState = uiState,
        addToFavourites = viewModel::addToFavourites,
        onRepoClick = {
            viewModel.onRepoClick(it)
            onRepoClick(it)
        },
        highlightSelectedRepo = highlightSelectedRepo,
        modifier = modifier,
    )
}

@Composable
fun ReposScreen(
    uiState: ReposUiState,
    addToFavourites: (Long, Boolean) -> Unit,
    onRepoClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedRepo: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            ReposUiState.Loading ->
                NiaLoadingWheel(modifier = modifier)

            is ReposUiState.Repos ->
                ReposTabContent(
                    repositories = emptyList(), //uiState.repositories,
                    onRepoClick = onRepoClick,
                    onSaveButtonClick = addToFavourites,
                    selectedRepoId = uiState.selectedRepoId,
                    highlightSelectedRepo = highlightSelectedRepo,
                    modifier = modifier,
                )

            is ReposUiState.Empty -> ReposEmptyScreen()

            is ReposUiState.Error -> ReposErrorScreen()
        }
    }
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
                uiState = ReposUiState.Repos(
                    selectedRepoId = null,
                    repositories = PagingData.empty(),
                ),
                addToFavourites = { _, _ -> },
                onRepoClick = {},
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
                uiState = ReposUiState.Loading,
                addToFavourites = { _, _ -> },
                onRepoClick = {},
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
                uiState = ReposUiState.Empty,
                addToFavourites = { _, _ -> },
                onRepoClick = {},
            )
        }
    }
}
