package com.github.repos.core.ui.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.repos.core.designsystem.component.NiaLoadingWheel
import com.github.repos.core.designsystem.theme.NiaTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        NiaLoadingWheel(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun PreviewLoadingScreen() {
    NiaTheme {
        LoadingScreen()
    }
}