package com.github.repos.feature.repos.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.repos.core.designsystem.component.NiaLoadingWheel
import com.github.repos.core.designsystem.icon.NiaIcons
import com.github.repos.core.designsystem.theme.NiaTheme

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
            AnimatedContent(targetState = isSearching, label = "") {
                if (it) {
                    NiaLoadingWheel()
                } else {
                    Icon(
                        imageVector = NiaIcons.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = NiaIcons.Close,
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
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
}

@Preview
@Composable
private fun SearchToolbarPreview() {
    NiaTheme {
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