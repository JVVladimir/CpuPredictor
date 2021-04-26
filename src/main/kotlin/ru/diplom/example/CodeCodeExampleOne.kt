package ru.diplom.example

import Condition
import ConditionType
import FragmentCode
import mu.KLogging
import ru.diplom.algo.EventListener

// Предсказатель ветвлений должен, не разбирая условие, понять куда ему двигаться!!!
class CodeCodeExampleOne : CodeExample {

    companion object : KLogging()

    // id идут строго по порядку по возрастанию!!!
    val fragmentCode = FragmentCode(
        conditions = listOf(
            Condition(
                id = 1,
                type = ConditionType.IF, // todo: добавить текстовое представление для удобства (a > 1_000)
            ),
            Condition(
                id = 2,
                type = ConditionType.ELSE,
            ),
            Condition(
                id = 3,
                type = ConditionType.IF,
            ),
            Condition(
                id = 4,
                type = ConditionType.IF,
            )
        )
    )

    override fun startCode(listener: EventListener) {
        val a = 5
        val b = 0

        if (a > 1_000) {
            listener.onConditionEvent(1)
        } else {
            listener.onConditionEvent(2)
        }

        if (b < 10) {
            listener.onConditionEvent(3)
        }

        if (a > 10) {
            listener.onConditionEvent(4)
        }
        listener.onCodeEnd()
    }

}