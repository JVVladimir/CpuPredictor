package ru.diplom.algo

import Condition
import FragmentCode
import isCycle
import isInitialCondition

/**
 * Статический интеллектуальный алгоритм
 *
 * */
class StaticalFirstForward(
    private val codeFragment: FragmentCode,
    stepMode: Boolean
) : AbstractAlgorithm(codeFragment, stepMode) {

    init {
        predictedConditionId = predictNextCondition(null)
    }

    override fun printSpecialInfoOnEachStep() {
    }

    // работает не больше трех уровней вложенности
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