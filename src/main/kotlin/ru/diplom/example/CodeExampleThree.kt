package ru.diplom.example

import Condition
import ConditionType.*
import FragmentCode
import mu.KLogging
import ru.diplom.algo.EventListener

class CodeExampleThree : CodeExample {

    companion object : KLogging()

    val fragmentCode = FragmentCode(
        listOf(
            Condition(1, WHILE).apply {
                conditions = listOf(
                    Condition(2, IF, root = this),
                    Condition(3, ELSE, root = this),
                    Condition(4, IF, root = this),
                )
            },
            Condition(5, IF),
            Condition(6, IF)
        )
    )

    override fun startCode(listener: EventListener) {
        var a = 0
        val b = 0

        while (a < 10) {
            listener event 1
            if (a > 1_000) {
                listener event 2
            } else {
                listener event 3
            }

            if (b < 10) {
                listener event 4
            }
            a++
        }

        if (a > 9) {
            listener event 5
        }

        if (a < 9) {
            listener event 6
        }

        listener.onCodeEnd()
    }

}