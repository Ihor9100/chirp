package com.plcoding.feature.chat.presentation.screen.chats.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.add
import chirp.feature.chat.presentation.generated.resources.add_members_to_your_chat
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.ic_cross
import chirp.feature.chat.presentation.generated.resources.invite_by_username_or_email
import chirp.feature.chat.presentation.generated.resources.no_members
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.components.TitleDescription
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.component.ChatMember
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun BaseChatDialogScreenContent(
  uiState: BaseChatDialogUiState<*>,
  onAction: (BaseChatDialogScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(uiState.titleRes),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      IconButton(
        onClick = { onAction(BaseChatDialogScreenAction.OnDismiss) }
      ) {
        Icon(
          modifier = Modifier.size(24.dp),
          imageVector = vectorResource(Res.drawable.ic_cross),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.extended.textSecondary,
        )
      }
    }
    HorizontalDivider()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      TextFieldPlain(
        modifier = Modifier.weight(1f),
        textFieldState = uiState.searchTextFieldState,
        inputPlaceholder = stringResource(Res.string.invite_by_username_or_email),
      )
      Button(
        text = stringResource(Res.string.add),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(BaseChatDialogScreenAction.OnAddClick) }
      )
    }
    uiState.foundChatMemberUi?.apply {
      ChatMember(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(bottom = 12.dp),
        chatMemberUi = this,
      )
    }
    HorizontalDivider()
    if (uiState.isEmptyState()) {
      TitleDescription(
        modifier = Modifier.padding(vertical = 24.dp),
        titleRes = Res.string.no_members,
        descriptionRes = Res.string.add_members_to_your_chat,
      )
    }
    if (uiState.foundChatMembersUi.isNotEmpty()) {
      ChatMembersLazyColumn(
        chatMembersUi = uiState.foundChatMembersUi,
        onKey = { uiState.foundChatMembersUi[it].id },
      )
    }
    HorizontalDivider()
    if (uiState.inChatMembersUi.isNotEmpty()) {
      ChatMembersLazyColumn(
        chatMembersUi = uiState.inChatMembersUi,
        onKey = { uiState.inChatMembersUi[it].id + "Invited" },
      )
    }
    HorizontalDivider()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 20.dp,
        ),
      horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Button(
        modifier = Modifier,
        text = stringResource(Res.string.cancel),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(BaseChatDialogScreenAction.OnDismiss) }
      )
      Button(
        modifier = Modifier,
        text = stringResource(uiState.positiveButtonRes),
        style = ButtonStyle.PRIMARY,
        onClick = { onAction(BaseChatDialogScreenAction.OnPositiveClick) }
      )
    }
  }
}

@Composable
private fun ChatMembersLazyColumn(
  chatMembersUi: List<ChatMemberUi>,
  onKey: (index: Int) -> Any,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        horizontal = 16.dp,
        vertical = 12.dp,
      ),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    items(chatMembersUi.size, onKey) { index ->
      ChatMember(
        modifier = Modifier,
        chatMemberUi = chatMembersUi[index],
      )
    }
  }
}