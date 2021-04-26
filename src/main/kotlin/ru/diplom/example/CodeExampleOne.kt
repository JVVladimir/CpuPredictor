package ru.diplom.example

import Condition
import FragmentCode

class CodeExampleOne : Example {

    val exampleNum = 1
    val fragmentCode = FragmentCode(
        example = this,
        variables = mutableMapOf("a" to "0", "b" to "0"),
        conditions = listOf(
            Condition(
                id = 12,
                type = ConditionType.IF,
                countExecution = 2,
                commandAddress = "1"
            ),
            Condition(
                type = ConditionType.ELSE,
                countExecution = 5
            )
        )
    )

    override fun example() {
        var a = 0
        var b = 0

        if (a > 1_000) {
            // callback to algo: algo.onEvent(id), id - идентификатор условия
            b = a
        } else {
            a++
        }

        println("$a $b")
        // Предсказатель ветвлений должен, не разбирая условие, понять куда ему двигаться
        // Возможно добавить рекурсию в это дерево фрагмента кода
    }

}