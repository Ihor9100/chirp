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
  itemsCount: Int,
  isPageLoading: Boolean,
  isLastPage: Boolean,
  onLoadMore: () -> Unit,
) {
  val itemsCount by rememberUpdatedState(itemsCount)
  val isLastPage by rememberUpdatedState(isLastPage)
  val isPageLoading by rememberUpdatedState(isPageLoading)
  var lastItemsCount by remember { mutableIntStateOf(0) }

  LaunchedEffect(lazyListState) {
    snapshotFlow {
      val layoutInfo = lazyListState.layoutInfo
      val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
      val totalItemsCount = layoutInfo.totalItemsCount

      val remainingItemsCount = if (lastVisibleItemIndex != null) {
        totalItemsCount - lastVisibleItemIndex - 1
      } else {
        null
      }

      remainingItemsCount != null &&
        remainingItemsCount <= 5 &&
        !isPageLoading &&
        !isLastPage
    }
      .distinctUntilChanged()
      .collect { isEligible ->
        if (isEligible && itemsCount > lastItemsCount) {
          lastItemsCount = itemsCount
          onLoadMore()
        }
      }
  }
}