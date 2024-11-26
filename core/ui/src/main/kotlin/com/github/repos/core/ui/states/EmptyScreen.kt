package com.github.repos.core.ui.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.repos.core.ui.PlaceholderContent
import com.github.repos.core.ui.R

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        PlaceholderContent(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.core_ui_empty_result),
        )
    }
}