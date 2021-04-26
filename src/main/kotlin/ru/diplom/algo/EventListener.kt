package ru.diplom.algo

interface EventListener {

    fun onConditionEvent(conditionId: Int)

    fun onCodeEnd()
}