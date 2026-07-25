@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun LazyListScrollObserver(
  lazyListState: LazyListState,
  onScroll: (LazyListScrollInfo) -> Unit,
) {
  LaunchedEffect(lazyListState) {
    snapshotFlow {
      val layoutInfo = lazyListState.layoutInfo
      LazyListScrollInfo(
        isScrollInProgress = lazyListState.isScrollInProgress,
        totalItemsCount = layoutInfo.totalItemsCount,
        visibleItemsIndices = layoutInfo.visibleItemsInfo.map { it.index },
      )
    }
      .distinctUntilChanged()
      .collect(onScroll)
  }
}

data class LazyListScrollInfo(
  val isScrollInProgress: Boolean,
  val totalItemsCount: Int,
  val visibleItemsIndices: List<Int>,
)
