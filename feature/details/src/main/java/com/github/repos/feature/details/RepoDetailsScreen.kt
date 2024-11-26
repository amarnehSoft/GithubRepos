package com.github.repos.feature.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.repos.core.designsystem.component.GithubFilterChip
import com.github.repos.core.designsystem.component.GithubLoadingWheel
import com.github.repos.core.designsystem.component.GithubOutlinedButton
import com.github.repos.core.designsystem.icon.GithubIcons
import com.github.repos.core.model.data.Repository
import com.github.repos.core.ui.CachedDynamicAsyncImage
import com.github.repos.core.ui.openInBrowser
import com.github.repos.core.ui.states.ErrorScreen

@Composable
fun RepoDetailsScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RepoDetailsViewModel = hiltViewModel(),
) {
    val uiState: RepoDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    RepoDetailsScreen(
        uiState = uiState,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        onSaveClick = { viewModel.toggleFavourite() },
        modifier = modifier,
    )
}

@Composable
private fun RepoDetailsScreen(
    uiState: RepoDetailsUiState,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (uiState) {
                RepoDetailsUiState.Loading -> item {
                    GithubLoadingWheel(
                        modifier = modifier,
                    )
                }

                RepoDetailsUiState.Error -> item {
                    ErrorScreen()
                }

                is RepoDetailsUiState.Success -> {
                    item {
                        RepoDetailsToolbar(
                            showBackButton = showBackButton,
                            onBackClick = onBackClick,
                            onFollowClick = onSaveClick,
                            repository = uiState.repository,
                        )
                    }

                    item {
                        RepositoryDetailsScreen(
                            repository = uiState.repository,
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@Composable
private fun RepositoryDetailsScreen(
    repository: Repository,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CachedDynamicAsyncImage(
                url = repository.ownerAvatarUrl,
                modifier = Modifier.size(64.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = repository.ownerUsername,
                    style = MaterialTheme.typography.labelMedium,
                )

                Text(
                    text = repository.name,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = repository.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionText(text = repository.description)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RepositoryAttribute(
                icon = GithubIcons.Star,
                label = stringResource(R.string.feature_details_stars),
                value = repository.stargazersCount.toString()
            )

            RepositoryAttribute(
                icon = GithubIcons.Fork,
                label = stringResource(R.string.feature_details_forks),
                value = repository.forks.toString()
            )

            if (repository.language.isNotBlank()) {
                RepositoryAttribute(
                    icon = GithubIcons.Code,
                    label = stringResource(R.string.feature_details_language),
                    value = repository.language,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        GithubOutlinedButton(
            onClick = {
                openInBrowser(url = repository.htmlUrl, context = context)
            }
        ) {
            Text(text = stringResource(R.string.feature_details_open_in_browser))
        }
    }
}

@Composable
private fun RepoDetailsToolbar(
    repository: Repository,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    onFollowClick: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showBackButton) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = GithubIcons.ArrowBack,
                        contentDescription = "",
                    )
                }
            } else {
                // Keeps the FilterChip aligned to the end of the Row.
                Spacer(modifier = Modifier.width(1.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            val selected = repository.isFavourite
            GithubFilterChip(
                selected = selected,
                onSelectedChange = onFollowClick,
                modifier = Modifier
                    .animateContentSize()
                    .padding(end = 24.dp),
                label = {
                    if (selected) {
                        Text(stringResource(R.string.feature_details_added_to_favourites))
                    } else {
                        Text(stringResource(R.string.feature_details_add_to_favourites ))
                    }
                },
                leadingIcon = {
                    if (selected) {
                        Icon(
                            imageVector = GithubIcons.Favorite,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            imageVector = GithubIcons.FavoriteBorder,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.DescriptionText(
    text: String,
) {
    if (text.isNotBlank()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    } else {
        Text(
            text = stringResource(id = com.github.repos.core.ui.R.string.core_ui_no_description),
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun RepositoryAttribute(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}