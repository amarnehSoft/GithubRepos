package com.github.repos.core.ui.states

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.repos.core.designsystem.component.NiaButton
import com.github.repos.core.designsystem.icon.NiaIcons
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.core.ui.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = NiaIcons.ErrorDrawable),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(150.dp)
        )

        Text(
            text = errorText ?: stringResource(id = R.string.core_ui_something_went_wrong),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        NiaButton(
            content = {
                Text(
                    text = stringResource(R.string.core_ui_retry)
                )
            },
            onClick = onRetry,
        )
    }
}

@Composable
fun ErrorRow(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = NiaIcons.Error,
                contentDescription = null
            )

            Text(
                text = stringResource(id = R.string.core_ui_something_went_wrong),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        NiaButton(
            content = {
                Text(
                    text = stringResource(R.string.core_ui_retry)
                )
            },
            onClick = onRetry,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    NiaTheme() {
        ErrorScreen(onRetry = {})
    }
}

@Preview
@Composable
private fun ErrorRowPreview() {
    NiaTheme {
        ErrorRow(onRetry = {})
    }
}
