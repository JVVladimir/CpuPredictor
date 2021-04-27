package ru.diplom.algo

interface EventListener {

    infix fun event(conditionId: Int)

    fun onCodeEnd(lastCode: Int = 0)
}