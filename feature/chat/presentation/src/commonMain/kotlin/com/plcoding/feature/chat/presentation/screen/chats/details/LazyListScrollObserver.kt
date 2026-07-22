@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun LazyListScrollObserver(
  lazyListState: LazyListState,
  isPageLoading: Boolean,
  isLastPage: Boolean,
  onLoadMore: () -> Unit,
  onScrollToStartChanged: (Boolean) -> Unit,
) {
  val isLastPage by rememberUpdatedState(isLastPage)
  val isPageLoading by rememberUpdatedState(isPageLoading)
  var lastItemsCount by remember { mutableIntStateOf(0) }

  LaunchedEffect(lazyListState) {
    snapshotFlow {
      val layoutInfo = lazyListState.layoutInfo
      val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
      val scrolledItemsCount = layoutInfo.visibleItemsInfo.firstOrNull()?.index
      val currentItemsCount = layoutInfo.totalItemsCount

      val remainingItemsCount = lastVisibleItemIndex?.let {
        currentItemsCount - lastVisibleItemIndex - 1
      }

      println("lol: remainingItemsCount=$remainingItemsCount")
      println("lol: scrolledItemsCount=$scrolledItemsCount")

      LazyListScrollObserverState(
        loadMore = remainingItemsCount != null &&
          remainingItemsCount <= 5 &&
          currentItemsCount > lastItemsCount &&
          !isPageLoading &&
          !isLastPage,
        currentItemsCount = currentItemsCount,
        showScrollToStart = scrolledItemsCount != null &&
          scrolledItemsCount > 2
      )
    }
      .distinctUntilChanged()
      .collect {
        if (it.loadMore) {
          lastItemsCount = it.currentItemsCount
          onLoadMore()
        }
        onScrollToStartChanged(it.showScrollToStart)
      }
  }
}

data class LazyListScrollObserverState(
  val loadMore: Boolean,
  val currentItemsCount: Int,
  val showScrollToStart: Boolean,
)
