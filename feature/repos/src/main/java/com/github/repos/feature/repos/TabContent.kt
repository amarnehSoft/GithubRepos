package com.github.repos.feature.repos

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
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
import com.github.repos.core.designsystem.component.scrollbar.DraggableScrollbar
import com.github.repos.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.github.repos.core.designsystem.component.scrollbar.scrollbarState
import com.github.repos.core.model.data.Repo
import com.github.repos.core.ui.InterestsItem

@Composable
fun ReposTabContent(
    repos: List<Repo>, // SavableRepo
    onRepoClick: (String) -> Unit,
    onSaveButtonClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withBottomSpacer: Boolean = true,
    selectedRepoId: String? = null,
    highlightSelectedRepo: Boolean = false,
) {
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
            repos.forEach { savableRepo ->
                val repoId = savableRepo.id
                item(key = repoId) {
                    val isSelected = highlightSelectedRepo && repoId == selectedRepoId
                    InterestsItem(
                        name = "name",
                        following = false,
                        description = "followableTopic.topic.shortDescription",
                        topicImageUrl = "followableTopic.topic.imageUrl",
                        onClick = { onRepoClick(repoId) },
                        onFollowButtonClick = { onSaveButtonClick(repoId, it) },
                        isSelected = isSelected,
                    )
                }
            }

            if (withBottomSpacer) {
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
        }
        val scrollbarState = scrollableState.scrollbarState(
            itemsAvailable = repos.size,
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
                itemsAvailable = repos.size,
            ),
        )
    }
}