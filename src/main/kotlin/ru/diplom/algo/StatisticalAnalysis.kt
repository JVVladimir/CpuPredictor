package ru.diplom.algo

import Condition
import FragmentCode
import isCycle
import isInitialCondition

class StatisticalAnalysis(
    private val codeFragment: FragmentCode
) : AbstractAlgorithm(codeFragment) {

    init {
        predictedConditionId = predictNextCondition(null)
    }

    override fun predictNextCondition(currentCond: Condition?): Int {
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
}