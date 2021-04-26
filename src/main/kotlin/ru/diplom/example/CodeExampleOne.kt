package ru.diplom.example

import Condition
import FragmentCode

class CodeExampleOne : Example {

    val exampleNum = 1

    override fun example() {
        var a = 0
        var b = 0

        if (a > 1_000) {
            b = a
        } else {
            a++
        }

        println("$a $b")
        FragmentCode(
            example = this,
            variables = mutableMapOf("a" to "0", "b" to "0"),
            conditions = listOf(Condition(
                type = ConditionType.IF
            ),
            Condition(
                type = ConditionType.ELSE
            ))
        )
    }

}