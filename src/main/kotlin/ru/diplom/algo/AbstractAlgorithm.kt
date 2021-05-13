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
    open var input = mutableListOf<Pair<MutableList<Int>, Int>>()

    override fun event(conditionId: Int) {
        addId(conditionId)
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
        addId(-1)
        logger.info { "Branch predicted: ${if (predictedConditionId == -1) "end" else predictedConditionId}, actual: end" }
        if (predictedConditionId != -1)
            countOfMisses++
        else if (predictedConditionId != lastCode) ;
        else
            countOfHits++

        logger.info { "Code end with info: ${getInfo()}" }
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

    private fun addId(id: Int) {
        when (input.size) {
            0 -> input.add(mutableListOf(0, 0, 0) to id)
            1 -> input.add(mutableListOf(0, 0, input[0].second) to id)
            2 -> input.add(mutableListOf(0, input[0].second, input[1].second) to id)
            3 -> input.add(mutableListOf(input[0].second, input[1].second, input[2].second) to id)
            else -> {
                val pair = input[input.size - 1]
                input.add(mutableListOf(pair.first[1], pair.first[2], pair.second) to id)
            }
        }
    }

    fun getInfo(): String {
        return "Missed: $countOfMisses, Hit: $countOfHits, Precise: ${countOfHits.toDouble() / (countOfMisses + countOfHits)}"
    }
}