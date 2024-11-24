package com.github.repos.feature.repos.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.repos.core.designsystem.theme.NiaTheme
import com.github.repos.feature.repos.R

enum class TimeFrameFilter {
    CREATED_IN_LAST_DAY,
    CREATED_IN_LAST_WEEK,
    CREATED_IN_LAST_MONTH,
}

@Composable
private fun TimeFrameFilter.title(): String {
    return when (this) {
        TimeFrameFilter.CREATED_IN_LAST_DAY -> stringResource(R.string.feature_repos_created_in_last_day)
        TimeFrameFilter.CREATED_IN_LAST_WEEK -> stringResource(R.string.feature_repos_created_in_last_week)
        TimeFrameFilter.CREATED_IN_LAST_MONTH -> stringResource(R.string.feature_repos_created_in_last_month)
    }
}

@Composable
private fun TimeFrameFilter.shortTitle(): String {
    return when (this) {
        TimeFrameFilter.CREATED_IN_LAST_DAY -> stringResource(R.string.feature_repos_last_day)
        TimeFrameFilter.CREATED_IN_LAST_WEEK -> stringResource(R.string.feature_repos_last_week)
        TimeFrameFilter.CREATED_IN_LAST_MONTH -> stringResource(R.string.feature_repos_last_month)
    }
}

@Composable
fun FilterByDropDownButton(
    selectedFilter: TimeFrameFilter,
    onFilterSelected: (TimeFrameFilter) -> Unit
) {
    // State to manage the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

    // Options for the dropdown menu
    //val options = listOf("Option 1", "Option 2", "Option 3")

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
                Icon(Icons.Default.FilterList, contentDescription = "Menu")
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
fun PreviewIconButtonWithDropdown() {
    NiaTheme {
        Box(Modifier.fillMaxSize()) {
            FilterByDropDownButton(
                onFilterSelected = {},
                selectedFilter = TimeFrameFilter.CREATED_IN_LAST_DAY
            )
        }
    }
}
