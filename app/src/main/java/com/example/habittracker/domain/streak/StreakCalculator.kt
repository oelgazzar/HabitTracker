package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Occurrence
import java.time.LocalDate

fun calculateStreak(
    occurrences: List<Occurrence>,
    rule: StreakRule
): Pair<Int, Int> {

    if (occurrences.isEmpty()) return 0 to 0

    val sorted = occurrences.distinct().sortedBy { it.anchorDate }
    println(sorted)

    var current = 1
    var best = 1

    for (i in 1..<sorted.size) {
        if (rule.isConsecutive(sorted[i - 1], sorted[i])) {
            current++
            best = maxOf(best, current)
        } else {
            current = 1
        }
    }

    if (rule.isActive(sorted.last(), LocalDate.now()).not()) {
        current = 0
    }

    return current to best
}