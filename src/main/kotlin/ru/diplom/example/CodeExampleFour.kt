package ru.diplom.example

import Condition
import ConditionType.*
import FragmentCode
import ru.diplom.algo.EventListener

class CodeExampleFour : CodeExample {

    val fragmentCode = FragmentCode(
        listOf(
            Condition(1, WHILE).apply {
                conditions = listOf(
                    Condition(2, IF, root = this),
                    Condition(3, ELSE_IF, root = this),
                    Condition(4, ELSE, root = this),
                )
            }
        )
    )

    val example = """
        val list = intArrayOf(1, 4, 11, 13, 19, 22, 29, 33, 46, 74, 91, 95, 103, 156, 191, 196)
        val key = 196
        var rangeStart = 0
        var rangeEnd = list.count()
        var index = -1
        while (rangeStart < rangeEnd && index == -1) {
            listener event 1
            val midIndex = rangeStart + (rangeEnd - rangeStart) / 2
            if (list[midIndex] == key) {
                listener event 2
                index = midIndex
            } else if (list[midIndex] < key) {
                listener event 3
                rangeStart = midIndex + 1
            } else {
                listener event 4
                rangeEnd = midIndex
            }
        }

        listener.onCodeEnd()
    """.trimIndent()

    override fun startCode(listener: EventListener) {
        val list = intArrayOf(1, 4, 11, 13, 19, 22, 29, 33, 46, 74, 91, 95, 103, 156, 191, 196)
        val key = 196
        var rangeStart = 0
        var rangeEnd = list.count()
        var index = -1
        while (rangeStart < rangeEnd && index == -1) {
            listener event 1
            val midIndex = rangeStart + (rangeEnd - rangeStart) / 2
            if (list[midIndex] == key) {
                listener event 2
                index = midIndex
            } else if (list[midIndex] < key) {
                listener event 3
                rangeStart = midIndex + 1
            } else {
                listener event 4
                rangeEnd = midIndex
            }
        }

        listener.onCodeEnd()
    }

    override fun getCode() = example

}