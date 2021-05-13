package ru.diplom.algo

import Condition
import FragmentCode
import mu.KLogging

abstract class AbstractAlgorithm(
    private val codeFragment: FragmentCode
) : EventListener {

    companion object : KLogging()

    open var countOfMisses = 0
    open var countOfHits = 0
    open var predictedConditionId = -1

    override fun event(conditionId: Int) {
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

    abstract fun predictNextCondition(currentCond: Condition?): Int

    fun getInfo(): String {
        return "Missed: $countOfMisses, Hit: $countOfHits"
    }
}