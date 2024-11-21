package com.github.repos.feature.repos.repos2pane

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.AnimatedPaneScope
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.repos.feature.details.RepoDetailPlaceholder
import com.github.repos.feature.details.navigation.RepoDetailsRoute
import com.github.repos.feature.details.navigation.navigateToRepoDetails
import com.github.repos.feature.details.navigation.repoDetailsScreen
import com.github.repos.feature.repos.repos2pane.navigation.DetailPaneNavHostRoute
import com.github.repos.feature.repos.repos2pane.navigation.TopicPlaceholderRoute
import java.util.UUID

@Composable
internal fun ReposListDetailScreen(
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    ReposListDetailScreen(
        windowAdaptiveInfo = windowAdaptiveInfo,
        listContent = { onRepoClick, highlightSelectedRepo ->
            com.github.repos.feature.repos.ReposRoute(
                onRepoClick = onRepoClick,
                highlightSelectedRepo = highlightSelectedRepo,
            )
        }
    )
}

@Composable
fun ReposListDetailScreen(
    viewModel: Repos2PaneViewModel = hiltViewModel(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    listContent: (@Composable (onRepoClick: (repoId: String) -> Unit, highlightSelectedRepo: Boolean) -> Unit),
) {
    val selectedRepoId by viewModel.selectedRepoId.collectAsStateWithLifecycle()
    ReposListDetailScreen(
        selectedRepoId = selectedRepoId,
        onRepoClick = viewModel::onRepoClick,
        windowAdaptiveInfo = windowAdaptiveInfo,
        listContent = listContent,
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun ReposListDetailScreen(
    selectedRepoId: String?,
    onRepoClick: (String) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    listContent: (@Composable (onRepoClick: (repoId: String) -> Unit, highlightSelectedRepo: Boolean) -> Unit),
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo),
        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<Nothing>(ListDetailPaneScaffoldRole.Detail).takeIf {
                selectedRepoId != null
            },
        ),
    )
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    var nestedNavHostStartRoute by remember {
        val route = selectedRepoId?.let { RepoDetailsRoute(id = it) } ?: TopicPlaceholderRoute
        mutableStateOf(route)
    }
    var nestedNavKey by rememberSaveable(
        stateSaver = Saver({ it.toString() }, UUID::fromString),
    ) {
        mutableStateOf(UUID.randomUUID())
    }
    val nestedNavController = key(nestedNavKey) {
        rememberNavController()
    }

    fun onTopicClickShowDetailPane(topicId: String) {
        onRepoClick(topicId)
        if (listDetailNavigator.isDetailPaneVisible()) {
            // If the detail pane was visible, then use the nestedNavController navigate call
            // directly
            nestedNavController.navigateToRepoDetails(topicId) {
                popUpTo<DetailPaneNavHostRoute>()
            }
        } else {
            // Otherwise, recreate the NavHost entirely, and start at the new destination
            nestedNavHostStartRoute = RepoDetailsRoute(id = topicId)
            nestedNavKey = UUID.randomUUID()
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            AnimatedPane {
                listContent(
                    ::onTopicClickShowDetailPane,
                    listDetailNavigator.isDetailPaneVisible(),
                )
            }
        },
        detailPane = {
            AnimatedPane {
                key(nestedNavKey) {
                    NavHost(
                        navController = nestedNavController,
                        startDestination = nestedNavHostStartRoute,
                        route = DetailPaneNavHostRoute::class,
                    ) {
                        repoDetailsScreen(
                            showBackButton = !listDetailNavigator.isListPaneVisible(),
                            onBackClick = listDetailNavigator::navigateBack,
                        )
                        composable<TopicPlaceholderRoute> {
                            RepoDetailPlaceholder()
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
