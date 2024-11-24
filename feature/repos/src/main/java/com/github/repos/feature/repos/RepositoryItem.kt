package com.github.repos.feature.repos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.repos.core.designsystem.component.DynamicAsyncImage
import com.github.repos.core.designsystem.component.NiaIconToggleButton
import com.github.repos.core.designsystem.icon.NiaIcons
import com.github.repos.core.designsystem.theme.NiaTheme

@Composable
fun RepositoryItem(
    name: String,
    ownerUserName: String,
    ownerAvatarUrl: String,
    description: String,
    starsCount: Int,
    isFavourite: Boolean,
    onClick: () -> Unit,
    onFavouriteToggleClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    ListItem(
        leadingContent = {
            OwnerAvatar(imageUrl = ownerAvatarUrl, iconModifier.size(48.dp))
        },
        headlineContent = {
            Column {
                Text(text = name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = ownerUserName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        supportingContent = {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$starsCount",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        trailingContent = {
            NiaIconToggleButton(
                checked = isFavourite,
                onCheckedChange = onFavouriteToggleClick,
                icon = {
                    Icon(
                        imageVector = NiaIcons.Add,
                        contentDescription = "",
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = NiaIcons.Check,
                        contentDescription = "",
                    )
                },
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Color.Transparent
            },
        ),
        modifier = modifier
            .semantics(mergeDescendants = true) {
                selected = isSelected
            }
            .clickable(enabled = true, onClick = onClick),
    )
}

@Composable
private fun OwnerAvatar(imageUrl: String, modifier: Modifier = Modifier) {
    if (imageUrl.isEmpty()) {
        Icon(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp),
            imageVector = NiaIcons.Person,
            // decorative image
            contentDescription = null,
        )
    } else {
        DynamicAsyncImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun RepositoryCardPreview() {
    NiaTheme {
        Surface {
            RepositoryItem(
                name = "Compose",
                description = "Description",
                onClick = { },
                ownerUserName = "Mohammad Amarneh",
                ownerAvatarUrl = "",
                starsCount = 3,
                isFavourite = true,
                onFavouriteToggleClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun RepositoryCardLongNamePreview() {
    NiaTheme {
        Surface {
            RepositoryItem(
                name = "A very very very very very very very very long name",
                description = "Description",
                onClick = { },
                ownerUserName = "Mohammad Amarneh",
                ownerAvatarUrl = "",
                starsCount = 3,
                isFavourite = true,
                onFavouriteToggleClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryCardLongDescriptionPreview() {
    NiaTheme {
        Surface {
            RepositoryItem(
                name = "A very very very very very very very very long name",
                description = "A very very very very very very very very long Description",
                onClick = { },
                ownerUserName = "Mohammad Amarneh",
                ownerAvatarUrl = "",
                starsCount = 3,
                isFavourite = true,
                onFavouriteToggleClick = {},
            )
        }
    }
}