package ru.diplom.algo

import Condition
import FragmentCode

abstract class AbstractAlgorithm(
    private val codeFragment: FragmentCode,
    private val stepMode: Boolean
) : EventListener {

    open var countOfMisses = 0
    open var countOfHits = 0
    open var predictedConditionId = -1
    open var precise = 0.0
    open var input = mutableListOf<Pair<MutableList<Int>, Int>>()

    override fun event(conditionId: Int) {
        codeFragment.countExecutions++
        addId(conditionId)
        val condition = searchCondition(conditionId)
        if (condition.id != conditionId)
            throw RuntimeException("Алгоритм поиска отработал не корректно и нашел не то!")
        condition.countExecutions++

        printSpecialInfoOnEachStep()
        println("Branch predicted: ${if (predictedConditionId == -1) "-1" else searchCondition(predictedConditionId)}, actual: $condition")
        if (predictedConditionId != conditionId) {
            countOfMisses++
        } else {
            countOfHits++
        }
        predictedConditionId = predictNextCondition(condition)
        if (stepMode) {
            readLine()
        }
    }

    abstract fun printSpecialInfoOnEachStep()

    override fun onCodeEnd(lastCode: Int) {
        addId(-1)
        print("Branch predicted: ${if (predictedConditionId == -1) "end" else predictedConditionId}, actual: end")
        if (predictedConditionId != -1)
            countOfMisses++
        else
            countOfHits++

        precise = countOfHits.toDouble() / (countOfMisses + countOfHits)
        println("""
=============================================================
"Code ends with result: ${getInfo()}"
=============================================================
        """
        )
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

    open fun clearAlgo() {
        countOfMisses = 0
        countOfHits = 0
        precise = 0.0
        predictedConditionId = -1
        input = mutableListOf()
    }

    private fun getInfo(): String {
        return "Missed: $countOfMisses, Hits: $countOfHits, Precise: $precise"
    }
}