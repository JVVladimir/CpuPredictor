package ru.diplom.algo

import Condition
import FragmentCode
import isCycle
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
        val condition = searchCondition(conditionId)
        if (condition.id != conditionId)
            throw RuntimeException("Алгоритм поиска отработал не корректно и нашел не то!")
        condition.countExecutions++

        logger.info { "Branch predicted: $predictedConditionId, actual: $conditionId" }
        if (predictedConditionId != conditionId) {
            countOfMisses++
        } else {
            countOfHits++
        }
        predictedConditionId = predictNextCondition(condition)
    }

    override fun onCodeEnd(lastCode: Int) {
        logger.info { "Branch predicted: ${if (predictedConditionId == -1) "end" else predictedConditionId}, actual: end" }
        logger.info { "Code end" }
        if (predictedConditionId != -1)
            countOfMisses++
        else if (predictedConditionId != lastCode) ;
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

    // работает не больше трех уровней вложенности
    private fun predictNextCondition(currentCond: Condition?): Int {
        if (currentCond == null) {
            return codeFragment.conditions.first { it.id == 1 }.id
        }

        if (currentCond.conditions.isEmpty()) {
            if (currentCond.root == null) {
                return codeFragment.conditions
                    .firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id ?: -1
            } else {
                if (currentCond.root!!.type.isCycle()) {
                    return currentCond.root!!.conditions.firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id
                        ?: currentCond.root!!.id
                } else {
                    return currentCond.root!!.conditions.firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id
                        ?: currentCond.root!!.root?.id ?: -1
                }
            }
        } else {
            return currentCond.conditions.first { it.type.isInitialCondition() }.id
        }
    }

    fun getInfo(): String {
        return "Missed: $countOfMisses, Hit: $countOfHits"
    }
}