package com.plcoding.feature.chat.presentation.utils

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.today
import chirp.feature.chat.presentation.generated.resources.today_x
import chirp.feature.chat.presentation.generated.resources.yesterday
import chirp.feature.chat.presentation.generated.resources.yesterday_x
import com.plcoding.core.presentation.model.TextProvider
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object DateUtils {

  fun formatToDateTime(instant: Instant, clock: Clock = Clock.System): TextProvider {
    val timeZone = TimeZone.currentSystemDefault()
    val messageDateTime = instant.toLocalDateTime(timeZone)
    val todayDate = clock.now().toLocalDateTime(timeZone).date
    val yesterdayDate = todayDate.minus(1, DateTimeUnit.DAY)

    // Example: 06:14pm
    val formattedTime = messageDateTime.format(
      LocalDateTime.Format {
        amPmHour()
        char(':')
        minute()
        amPmMarker("am", "pm")
      }
    )
    // Example: 24/07/2026 06:14pm
    val formattedDateTime = messageDateTime.format(
      LocalDateTime.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
        chars(" $formattedTime")
      }
    )

    return when (messageDateTime.date) {
      todayDate -> TextProvider.Resource(Res.string.today_x, listOf(formattedTime))
      yesterdayDate -> TextProvider.Resource(Res.string.yesterday_x, listOf(formattedTime))
      else -> TextProvider.Dynamic(formattedDateTime)
    }
  }

  fun formatToDate(localDate: LocalDate): TextProvider {
    val timeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.now().toLocalDateTime(timeZone).date
    val yesterday = today.minus(1, DateTimeUnit.DAY)

    return when (localDate) {
      today -> TextProvider.Resource(Res.string.today)
      yesterday -> TextProvider.Resource(Res.string.yesterday)
      else -> TextProvider.Dynamic(
        // Example: 24/07/2026
        localDate.format(
          LocalDate.Format {
            day(); char('/'); monthNumber(); char('/'); year()
          }
        )
      )
    }
  }
}