package com.github.repos.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.repos.core.designsystem.icon.GithubIcons
import com.github.repos.core.designsystem.theme.GithubTheme

@Composable
fun RepoDetailPlaceholder(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.core_ui_select_a_repo),
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
    ) {
        PlaceholderContent(
            modifier = Modifier.fillMaxSize(),
            text = text,
        )
    }
}

@Composable
fun PlaceholderContent(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = GithubIcons.RepoDrawable),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(widthDp = 200, heightDp = 300)
@Composable
fun TopicDetailPlaceholderPreview() {
    GithubTheme {
        RepoDetailPlaceholder()
    }
}