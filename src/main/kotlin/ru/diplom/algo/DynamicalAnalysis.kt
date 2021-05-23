package ru.diplom.algo

import Condition
import ExecutionCountComparator
import FragmentCode
import isCycle
import isInitialCondition

/**
 * Динамический интеллектуальный алгоритм
 *
 * */
class DynamicalAnalysis(
    private val codeFragment: FragmentCode,
    stepMode: Boolean
) : AbstractAlgorithm(codeFragment, stepMode) {

    init {
        predictedConditionId = predictNextCondition(null)
    }

    override fun printSpecialInfoOnEachStep() {
        var isHeaderPrinted = false
        for (i in codeFragment.conditions) {
            if (!isHeaderPrinted) {
                println("Global pattern history table: ")
                isHeaderPrinted = true
            }
            printCountOfExecution(i)
        }
    }

    private fun printCountOfExecution(root: Condition) {
        if (root.conditions.isNotEmpty()) {
            for (i in root.conditions) {
                printCountOfExecution(i)
            }
        }
        if (root.countExecutions != 0) {
            println("\t$root -> ${root.countExecutions.toFloat() / codeFragment.countExecutions}")
        }
    }

    override fun predictNextCondition(currentCond: Condition?): Int {
        if (currentCond == null) {
            return codeFragment.conditions.maxOfWith(ExecutionCountComparator()) { it }.id
        }

        if (currentCond.conditions.isEmpty()) {
            if (currentCond.root == null) {
                return codeFragment.conditions
                    .firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id ?: -1
            } else {
                if (currentCond.root!!.type.isCycle()) {
                    val nextCascade =
                        currentCond.root!!.conditions.firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id
                            ?: return currentCond.root!!.id
                    return currentCond.root!!.conditions.firstOrNull {
                        it.id >= nextCascade && it.countExecutions == countMax(
                            currentCond
                        )
                    }?.id
                        ?: currentCond.root!!.id
                } else {
                    val nextCascade =
                        currentCond.root!!.conditions.firstOrNull { currentCond.id < it.id && it.type.isInitialCondition() }?.id
                            ?: return -1
                    return currentCond.root!!.conditions.firstOrNull {
                        it.id >= nextCascade && it.countExecutions == countMax(
                            currentCond
                        )
                    }?.id
                        ?: currentCond.root!!.root?.id ?: -1
                }
            }
        } else {
            return currentCond.conditions.maxOfWith(ExecutionCountComparator()) { it }.id
        }
    }

    private fun countMax(currentCond: Condition) =
        currentCond.root!!.conditions.filter { currentCond.id < it.id }
            .maxOfWith(ExecutionCountComparator()) { it }.countExecutions

}