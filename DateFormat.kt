package com.brightline.beta.utility

import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class Dates(val type : String) {
    object SelectCalendarDate : Dates("selectDate") // EEEE, MMMM d, yyyy
    object DateSelectionHeader : Dates("dateSelectionHeader") // EEE, MMM dd
    object SelectCalendarMonth : Dates("calendarMonth") // MMMM yyyy
    // TODO: train selection date carousel EEE/nMMM d
    // TODO: RTI departure trip on Return Trip screen EEE, MMM d, yyyy
    // TODO: RTI trip details M/d/yy
    // TODO: Trips
}

fun targetDate(type: Dates, addDays: Long = 0, addMonths: Long = 0): String {
    val localDate = LocalDate.now().plusDays(addDays).plusMonths(addMonths)
    val dateFormat: String =
        when (type) {
            Dates.SelectCalendarDate -> "EEEE, MMMM d, yyyy"
            Dates.DateSelectionHeader -> "EEE, MMM d"
            Dates.SelectCalendarMonth -> "MMMM yyyy"
        }

    val formatter = DateTimeFormatter.ofPattern(dateFormat)
    return localDate.format(formatter).toString()
}


