package com.github.repos.feature.details

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                        name = "uiState.followableTopic.topic.name",
                        description = "uiState.followableTopic.topic.longDescription",
                        imageUrl = "uiState.followableTopic.topic.imageUrl",
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
    name: String,
    description: String,
    imageUrl: String,
) {
    // TODO: Show icon if available
    item {
        TopicHeader(name, description, imageUrl)
    }
}

@Composable
private fun TopicHeader(name: String, description: String, imageUrl: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        DynamicAsyncImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(132.dp)
                .padding(bottom = 12.dp),
        )
        Text(name, style = MaterialTheme.typography.displayMedium)
        if (description.isNotEmpty()) {
            Text(
                description,
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}


@Preview
@Composable
private fun TopicBodyPreview() {
    NiaTheme {
        LazyColumn {
            topicBody(
                name = "Jetpack Compose",
                description = "Lorem ipsum maximum",
                imageUrl = "",
            )
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
                        imageVector = NiaIcons.ArrowBack,
                        contentDescription = "",
                    )
                }
            } else {
                // Keeps the NiaFilterChip aligned to the end of the Row.
                Spacer(modifier = Modifier.width(1.dp))
            }
            val selected = true // TODO
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

        Text(
            text = repository.id.toString(),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
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