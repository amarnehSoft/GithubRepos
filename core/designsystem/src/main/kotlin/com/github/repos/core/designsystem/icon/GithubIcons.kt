package com.github.repos.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.ForkRight
import androidx.compose.material.icons.rounded.Grid3x3
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.repos.core.designsystem.R

/**
 * Github icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object GithubIcons {
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val Bookmark = Icons.Rounded.Bookmark
    val BookmarkBorder = Icons.Rounded.BookmarkBorder
    val Bookmarks = Icons.Rounded.Bookmarks
    val BookmarksBorder = Icons.Outlined.Bookmarks
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val Grid3x3 = Icons.Rounded.Grid3x3
    val MoreVert = Icons.Default.MoreVert
    val Person = Icons.Rounded.Person
    val Search = Icons.Rounded.Search
    val Upcoming = Icons.Rounded.Upcoming
    val UpcomingBorder = Icons.Outlined.Upcoming

    val Error = Icons.Rounded.ErrorOutline
    val ErrorDrawable = R.drawable.ic_empty_error
    val RepoDrawable = R.drawable.ic_repo

    val Favorite = Icons.Rounded.Favorite
    val FavoriteBorder = Icons.Rounded.FavoriteBorder
    val Star = Icons.Rounded.Star
    val Fork = Icons.Rounded.ForkRight
    val Code = Icons.Rounded.Code
    val FilterList = Icons.Rounded.FilterList
}
