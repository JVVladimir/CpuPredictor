package ru.diplom.example

import Condition
import ConditionType.ELSE
import ConditionType.IF
import FragmentCode
import ru.diplom.algo.EventListener

// Предсказатель ветвлений должен, не разбирая условие, понять куда ему двигаться!!!
class CodeExampleOne : CodeExample {

    // id идут строго по порядку по возрастанию!!!
    val fragmentCode = FragmentCode(
        conditions = listOf(
            Condition(1, IF),
            Condition(2, ELSE),
            Condition(3, IF),
            Condition(4, IF),
            Condition(5, IF)
        )
    )

    val example = """
        val a = 5
        val b = 0

        if (a > 1_000) {
            listener event 1
        } else {
            listener event 2
        }

        if (b < 10) {
            listener event 3
        }

        if (a > 10) {
            listener event 4
        } else {
            listener event 5
        }
        listener.onCodeEnd()
    """

    override fun startCode(listener: EventListener) {
        val a = 5
        val b = 0

        if (a > 1_000) {
            listener event 1
        } else {
            listener event 2
        }

        if (b < 10) {
            listener event 3
        }

        if (a > 10) {
            listener event 4
        } else {
            listener event 5
        }
        listener.onCodeEnd()
    }

    override fun getCode() = example
}