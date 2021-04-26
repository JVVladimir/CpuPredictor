package ru.diplom.algo

import Condition
import FragmentCode
import isInitialCondition
import mu.KLogging

class StaticalFirstForward(
    private val codeFragment: FragmentCode
) : EventListener {

    companion object : KLogging()

    var countOfMisses = 0
    var countOfHits = 0

    //var overallTimeForExecution = 0

    //  private var timePerStep = 0
    private var predictedConditionId = -1

    init {
        predictedConditionId = predictNextCondition(null)
    }

    override infix fun event(conditionId: Int) {
        logger.info { "Event for conditionId: $conditionId triggered" }
        val condition = codeFragment.conditions.first { it.id == conditionId }
        condition.countExecutions++

        if (predictedConditionId != conditionId) {
            conditionMissed(condition)
        } else {
            conditionHit(condition)
        }
        predictedConditionId = predictNextCondition(condition)
    }

    override fun onCodeEnd() {
        logger.info { "Condition end" }
        if (predictedConditionId != -1)
            countOfMisses++
        else
            countOfHits++

//        overallTimeForExecution =
//            2 * countOfMisses * CONDITION_EXECUTION + BODY_EXECUTION * countOfMisses + countOfHits * (CONDITION_EXECUTION + BODY_EXECUTION - CONDITION_EXECUTION + 1)
    }

    private fun conditionMissed(condition: Condition) {
        logger.info { "Condition missed: $condition" }
        countOfMisses++
    }

    private fun conditionHit(condition: Condition) {
        logger.info { "Condition hit: $condition" }
        countOfHits++
    }

    private fun predictNextCondition(currentCond: Condition?): Int {
        logger.info { "Current condition id: $currentCond" }
        if (currentCond == null) {
            return codeFragment.conditions.first { it.id == 1 }.id
        }

        if (currentCond.conditions.isEmpty()) {
            if (currentCond.root == null) {
                return codeFragment.conditions
                    .firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id ?: -1
            }
            // Если сверху цикл, то возвращаем его же
            // иначе ищем след начальное условие того же уровня
            return -1
        } else {
            return currentCond.conditions.first { it.type.isInitialCondition() }.id
        }
    }

    fun getInfo(): String {
        return "Missed: $countOfMisses, Hit: $countOfHits"
    }
}