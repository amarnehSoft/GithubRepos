package com.github.repos.feature.details

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.github.repos.core.designsystem.component.DynamicAsyncImage
import com.github.repos.core.designsystem.component.NiaBackground
import com.github.repos.core.designsystem.component.NiaFilterChip
import com.github.repos.core.designsystem.component.NiaLoadingWheel
import com.github.repos.core.designsystem.component.scrollbar.DraggableScrollbar
import com.github.repos.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.github.repos.core.designsystem.component.scrollbar.scrollbarState
import com.github.repos.core.designsystem.icon.NiaIcons
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.core.model.data.Repository
import com.github.repos.core.ui.DevicePreviews

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
        onSaveClick = {},
        modifier = modifier,
    )
}

// DynamicAsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(repository.ownerAvatarUrl)
//                    .crossfade(true)
//                    .transformations(CircleCropTransformation())
//                    .build(),
//                contentDescription = "Owner Avatar",
//                modifier = Modifier
//                    .size(64.dp)
//                    .padding(end = 16.dp)
//                    .clip(CircleShape)
//            )

@Composable
fun RepositoryDetailsScreen(
    repository: Repository,
    onFavouriteClick: (Boolean) -> Unit,
    onOpenWebClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Owner Info
        Row(verticalAlignment = Alignment.CenterVertically,) {
            DynamicAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(repository.ownerAvatarUrl)
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = "Owner Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
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

        Text(
            text = repository.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            RepositoryAttribute(
                icon = Icons.Default.Star,
                label = "Stars",
                value = repository.stargazersCount.toString()
            )
            RepositoryAttribute(
                icon = Icons.Default.ForkRight,
                label = "Forks",
                value = repository.forks.toString()
            )
            RepositoryAttribute(
                icon = Icons.Default.Code,
                label = "Language",
                value = repository.language
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Favourite and Open in Browser
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = { onOpenWebClick(repository.htmlUrl) }) {
                Text(text = "Open in Browser")
            }
        }
    }
}

@Composable
fun RepositoryAttribute(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
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


@VisibleForTesting
@Composable
internal fun RepoDetailsScreen(
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
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }
            when (uiState) {
                RepoDetailsUiState.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                    )
                }

                RepoDetailsUiState.Error -> TODO()
                is RepoDetailsUiState.Success -> {
                    item {
                        RepoDetailsToolbar(
                            showBackButton = showBackButton,
                            onBackClick = onBackClick,
                            onFollowClick = onSaveClick,
                            repository = uiState.repository,
                        )
                    }
                    topicBody(
                        repository = uiState.repository,
                    )
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = topicItemsSize(uiState)
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable,
        )
        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
    }
}

private fun topicItemsSize(
    uiState: RepoDetailsUiState
) = when (uiState) {
    RepoDetailsUiState.Error -> 0 // Nothing
    RepoDetailsUiState.Loading -> 1 // Loading bar
    is RepoDetailsUiState.Success -> 2
}

private fun LazyListScope.topicBody(
    repository: Repository,
) {
    item {
        RepositoryDetailsScreen(
            repository = repository,
            onFavouriteClick = {},
            onOpenWebClick = {}
        )
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
                        imageVector = NiaIcons.ArrowBack,
                        contentDescription = "",
                    )
                }
            } else {
                // Keeps the NiaFilterChip aligned to the end of the Row.
                Spacer(modifier = Modifier.width(1.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            val selected = repository.isFavourite
            NiaFilterChip(
                selected = selected,
                onSelectedChange = onFollowClick,
                modifier = Modifier.padding(end = 24.dp),
            ) {
                if (selected) {
                    Text("FOLLOWING")
                } else {
                    Text("NOT FOLLOWING")
                }
            }
        }

//        Text(
//            text = repository.name,
//            style = MaterialTheme.typography.displayLarge,
//            modifier = Modifier.padding(horizontal = 24.dp),
//        )
    }
}

@DevicePreviews
@Composable
fun TopicScreenLoading() {
    NiaTheme {
        NiaBackground {
            RepoDetailsScreen(
                uiState = RepoDetailsUiState.Loading,
                showBackButton = true,
                onBackClick = {},
                onSaveClick = {},
            )
        }
    }
}