package ru.diplom.example

import Condition
import ConditionType.*
import FragmentCode
import ru.diplom.algo.EventListener

class CodeExampleFive : CodeExample {

    val fragmentCode = FragmentCode(
        listOf(
            Condition(1, WHILE).apply {
                conditions = listOf(
                    Condition(2, FOR, root = this).apply {
                        conditions = listOf(Condition(3, IF, root = this))
                    }
                )
            }
        )
    )

    override fun startCode(listener: EventListener) {
        // Для полностью случайного массива - предсказание не возможно
        val arr = intArrayOf(2, 15, 1, 8, 4) //16, 29, 1, 32, 46, 98, 63, 71, 15, 19, 92, 67)
        var swap = true
        while (swap) {
            listener event 1
            swap = false
            for (i in 0 until arr.size - 1) {
                listener event 2
                if (arr[i] > arr[i + 1]) {
                    listener event 3
                    val temp = arr[i]
                    arr[i] = arr[i + 1]
                    arr[i + 1] = temp
                    swap = true
                }
            }
        }

        listener.onCodeEnd()
    }

}