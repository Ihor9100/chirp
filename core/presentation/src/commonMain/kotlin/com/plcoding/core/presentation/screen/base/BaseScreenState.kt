package com.plcoding.core.presentation.screen.base

data class BaseScreenState<Content>(
  val content: Content,
  val baseContent: BaseContent = BaseContent(),
)