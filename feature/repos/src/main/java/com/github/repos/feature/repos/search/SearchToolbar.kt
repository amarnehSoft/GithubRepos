package com.github.repos.feature.repos.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.repos.core.designsystem.component.GithubLoadingWheel
import com.github.repos.core.designsystem.icon.GithubIcons
import com.github.repos.core.designsystem.theme.GithubTheme
import com.github.repos.feature.repos.R

@Composable
internal fun SearchToolbar(
    searchQuery: String,
    isSearching: Boolean,
    selectedFilter: TimeFrameFilter,
    onFilterSelected: (TimeFrameFilter) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    shouldShowFilter: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
            isSearching = isSearching,
        )

        if (shouldShowFilter) {
            FilterByDropDownButton(
                selectedFilter = selectedFilter,
                onFilterSelected = onFilterSelected,
            )
        }
    }
}

@Composable
private fun RowScope.SearchTextField(
    searchQuery: String,
    isSearching: Boolean,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            AnimatedContent(
                targetState = isSearching,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220, delayMillis = 90))).togetherWith(fadeOut(animationSpec = tween(90)))
                },
                label = "",
            ) {
                if (it) {
                    GithubLoadingWheel(
                        modifier = Modifier.size(40.dp),
                    )
                } else {
                    Icon(
                        imageVector = GithubIcons.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = GithubIcons.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) onSearchQueryChanged(it)
        },
        modifier = Modifier
            .weight(1f)
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(32.dp),
        value = searchQuery,
        placeholder = {
            Text(
                text = stringResource(id = R.string.feature_repos_search_placeholder),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Preview
@Composable
private fun SearchToolbarPreview() {
    GithubTheme {
        SearchToolbar(
            searchQuery = "",
            onSearchQueryChanged = {},
            onSearchTriggered = {},
            isSearching = false,
            shouldShowFilter = true,
            selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY,
            onFilterSelected = {},
        )
    }
}