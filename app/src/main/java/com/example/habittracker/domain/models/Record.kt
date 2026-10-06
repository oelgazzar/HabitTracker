package com.example.habittracker.domain.models

import java.time.LocalDate

sealed interface Record {
    val anchorDate: LocalDate
    val progress: Int
    val target: Int
    val isSatisfied: Boolean
        get() = progress >= target

}