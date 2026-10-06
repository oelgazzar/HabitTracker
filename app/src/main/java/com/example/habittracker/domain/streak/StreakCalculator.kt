package com.example.habittracker.domain.streak

import com.example.habittracker.domain.models.Record
import java.time.LocalDate

fun calculateStreak(
    records: List<Record>,
    rule: StreakRule
): Pair<Int, Int> {

    val preparedRecords = records
        .filter { record -> record.isSatisfied && rule.countsTowardsStreakCalculation(record) }
        .sortedBy { record -> record.anchorDate }

    if (preparedRecords.isEmpty()) return 0 to 0

    var current = 1
    var best = 1

    for (i in 1..<preparedRecords.size) {
        if (rule.isConsecutive(preparedRecords[i - 1], preparedRecords[i])) {
            current++
            best = maxOf(best, current)
        } else {
            current = 1
        }
    }

    if (rule.isActive(preparedRecords.last(), LocalDate.now()).not()) {
        current = 0
    }

    return current to best
}