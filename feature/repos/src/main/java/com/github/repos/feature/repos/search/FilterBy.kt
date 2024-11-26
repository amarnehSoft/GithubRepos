package com.github.repos.feature.repos.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.repos.core.designsystem.icon.GithubIcons
import com.github.repos.core.designsystem.theme.GithubTheme

@Composable
fun FilterByDropDownButton(
    selectedFilter: TimeFrameFilter,
    onFilterSelected: (TimeFrameFilter) -> Unit
) {
    // State to manage the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedFilter.shortTitle(),
                style = MaterialTheme.typography.labelSmall.copy(
                    lineHeight = 10.sp,
                ),
                textAlign = TextAlign.Center,
            )

            // IconButton to toggle the dropdown menu
            IconButton(onClick = { expanded = true }) {
                Icon(GithubIcons.FilterList, contentDescription = "")
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TimeFrameFilter.entries.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(option.title())
                    },
                    onClick = {
                        onFilterSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewIconButtonWithDropdown() {
    GithubTheme {
        Box(Modifier.fillMaxSize()) {
            FilterByDropDownButton(
                onFilterSelected = {},
                selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY
            )
        }
    }
}
