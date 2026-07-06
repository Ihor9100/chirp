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
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.components.TitleDescriptionPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.component.ChatMemberPc
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun BaseChatDialogScreenContent(
  contentPm: BaseChatDialogScreenContentPm<*>,
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
        text = stringResource(contentPm.titleRes),
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
    HorizontalDividerPc()
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
        textFieldState = contentPm.searchTextFieldState,
        inputPlaceholder = stringResource(Res.string.invite_by_username_or_email),
      )
      ButtonPc(
        text = stringResource(Res.string.add),
        style = ButtonPcStyle.SECONDARY,
        onClick = { onAction(BaseChatDialogScreenAction.OnAddClick) }
      )
    }
    contentPm.foundChatMemberPm?.apply {
      ChatMemberPc(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(bottom = 12.dp),
        chatMemberPm = this,
      )
    }
    HorizontalDividerPc()
    if (contentPm.isEmptyState()) {
      TitleDescriptionPc(
        modifier = Modifier.padding(vertical = 24.dp),
        titleRes = Res.string.no_members,
        descriptionRes = Res.string.add_members_to_your_chat,
      )
    }
    if (contentPm.foundChatMembersPm.isNotEmpty()) {
      ChatMembersLazyColumnPc(
        chatMembersPm = contentPm.foundChatMembersPm,
        onKey = { contentPm.foundChatMembersPm[it].id },
      )
    }
    HorizontalDividerPc()
    if (contentPm.inChatMembersPm.isNotEmpty()) {
      ChatMembersLazyColumnPc(
        chatMembersPm = contentPm.inChatMembersPm,
        onKey = { contentPm.inChatMembersPm[it].id + "Invited" },
      )
    }
    HorizontalDividerPc()
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
      ButtonPc(
        modifier = Modifier,
        text = stringResource(Res.string.cancel),
        style = ButtonPcStyle.SECONDARY,
      )
      ButtonPc(
        modifier = Modifier,
        text = stringResource(contentPm.positiveButtonRes),
        style = ButtonPcStyle.PRIMARY,
        onClick = { onAction(BaseChatDialogScreenAction.OnPositiveClick) }
      )
    }
  }
}

@Composable
private fun ChatMembersLazyColumnPc(
  chatMembersPm: List<ChatMemberPm>,
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
    items(chatMembersPm.size, onKey) { index ->
      ChatMemberPc(
        modifier = Modifier,
        chatMemberPm = chatMembersPm[index],
      )
    }
  }
}