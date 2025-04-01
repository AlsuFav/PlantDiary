package ru.fav.plantdiary.utils.validators

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class DateValidator {
    fun isYearInFuture(year: Int): Boolean {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        return year > currentYear
    }
}