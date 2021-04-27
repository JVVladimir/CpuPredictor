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

    private var predictedConditionId = -1

    init {
        predictedConditionId = predictNextCondition(null)
    }

    override infix fun event(conditionId: Int) {
        logger.info { "Event for conditionId: $conditionId triggered" }
        val condition = searchCondition(conditionId)
        if (condition.id != conditionId)
            throw RuntimeException("Алгоритм поиска отработал не корректно и нашел не то!")
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
    }

    private fun searchCondition(conditionId: Int): Condition {
        for (root in codeFragment.conditions) {
            return if (root.id == conditionId)
                root
            else {
                search(root, conditionId) ?: continue
            }
        }

        return codeFragment.conditions[0]
    }

    private fun search(root: Condition, conditionId: Int): Condition? {
        for (i in root.conditions) {
            return if (i.id == conditionId)
                i
            else if (i.conditions.isEmpty())
                continue
            else
                search(i, conditionId)
        }
        return null
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

        return if (currentCond.conditions.isEmpty()) {
            if (currentCond.root == null) {
                codeFragment.conditions
                    .firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id ?: -1
            } else {
                currentCond.root!!.conditions.firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id
                    ?: currentCond.root!!.id
            }
        } else {
            currentCond.conditions.first { it.type.isInitialCondition() }.id
        }
    }

    fun getInfo(): String {
        return "Missed: $countOfMisses, Hit: $countOfHits"
    }
}