package ru.diplom.algo

import FragmentCode
import mu.KLogging

class StaticalNoIntelligent: Algorithm, EventListener {

    companion object: KLogging()

    var countOfMisses = 0
    var countOfHits = 0
    var timeForExecution = 0L

    override fun execute(fragmentCode: FragmentCode) {

    }

}