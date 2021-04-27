package ru.diplom.example

import Condition
import ConditionType.*
import FragmentCode
import mu.KLogging
import ru.diplom.algo.EventListener

class CodeExampleTwo : CodeExample {

    companion object : KLogging()

    // id идут строго по порядку по возрастанию!!!
    val fragmentCode = FragmentCode(
        conditions = listOf(
            Condition(1, IF).apply {
                conditions = listOf(
                    Condition(2, IF, root = this),
                    Condition(3, ELSE, root = this)
                )
            },
            Condition(4, ELSE_IF).apply {
                conditions = listOf(
                    Condition(5, IF, root = this),
                    Condition(6, ELSE, root = this)
                )
            },
            Condition(7, ELSE)
        )
    )

    override fun startCode(listener: EventListener) {
        val number1 = 60
        val number2 = 60
        if (number1 < 0) {
            listener event 1
            if (number2 < 0) {
                listener event 2
                "Negative numbers"
            } else {
                listener event 3
                "First negative, second positive"
            }
        } else if (number1 > 0) {
            listener event 4
            if (number2 > 0) {
                listener event 5
                "Positive numbers"
            } else {
                listener event 6
                "First positive, second negative"
            }
        } else {
            listener event 7
            "Both equal 0"
        }
        listener.onCodeEnd()
    }

}