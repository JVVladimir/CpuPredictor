package ru.diplom

import FragmentCode
import ru.diplom.algo.DynamicalAnalysis
import ru.diplom.algo.NeuralNet
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.*

const val MAX_FRAGMENT_CODE = 5
const val MAX_ALGO = 3

fun main() {
    println("Введите номер примера кода от 1 до 5")
    val sampleNum = readLine()?.toInt() ?: throw RuntimeException("Введена пустая строка!")
    if (sampleNum < 1 || sampleNum > MAX_FRAGMENT_CODE)
        throw RuntimeException("Варианта фрагмента кода не существует!")
    println(
        """
        Варианты алгоритмов:
            1. Статический интеллектуальный
            2. Динамический интеллектуальный
            3. Динамический, основанный на перцептроне
        Введите номер алгоритма от 1 до $MAX_ALGO""".trimIndent()
    )
    val algNum = readLine()?.toInt() ?: throw RuntimeException("Введена пустая строка!")
    if (algNum < 1 || algNum > MAX_ALGO)
        throw RuntimeException("Варианта алгоритма не существует!")

    println("Включить пошаговый режим исполнения? (Y/N)")
    val stepByStepMode = readLine() ?: throw RuntimeException("Введена пустая строка!")
    var stepMode = false
    if (stepByStepMode.toUpperCase() == "Y") {
        stepMode = true
    }

    val codeToFragment = codeFromSampleNum(sampleNum)

    println("Показать фрагмент кода? (Y/N)")
    val showCode = readLine() ?: throw RuntimeException("Введена пустая строка!")
    if (showCode.toUpperCase() == "Y") {
        println(
            "================ !! Code fragment!! ======================" +
                    "${codeToFragment.first.getCode()}\n" +
                    "======================================"
        )
    }

    when (algNum) {
        1 -> {
            codeToFragment.first.startCode(StaticalFirstForward(codeToFragment.second, stepMode))
        }
        2 -> {
            var algo = DynamicalAnalysis(codeToFragment.second, stepMode)
            repeat(2) {
                codeToFragment.first.startCode(algo)
                if (it == 1)
                    println("Prediction after warming up")
                algo = DynamicalAnalysis(codeToFragment.second, stepMode)
            }
        }
        3 -> {
            println("Введите число итераций для обучения перцептрона (рекомендуемое значение - 3000): ")
            val epochs = readLine() ?: throw RuntimeException("Введена пустая строка!")
            val algo = NeuralNet(codeToFragment.second, stepMode, epochs.toInt())
            println("Train process")
            println("=================================================")
            algo.prepareDataSet(codeToFragment.first)
            println("\nCode execution:")
            println("=================================================")
            codeToFragment.first.startCode(algo)
        }
        else -> throw RuntimeException("Выбран номер несуществующего алгоритма!")
    }

}

private fun codeFromSampleNum(sampleNum: Int): Pair<CodeExample, FragmentCode> {
    return when (sampleNum) {
        1 -> {
            val code = CodeExampleOne(); Pair(code, code.fragmentCode)
        }
        2 -> {
            val code = CodeExampleTwo(); Pair(code, code.fragmentCode)
        }
        3 -> {
            val code = CodeExampleThree(); Pair(code, code.fragmentCode)
        }
        4 -> {
            val code = CodeExampleFour(); Pair(code, code.fragmentCode)
        }
        5 -> {
            val code = CodeExampleFive(); Pair(code, code.fragmentCode)
        }
        else -> {
            val code = CodeExampleOne(); Pair(code, code.fragmentCode)
        }
    }
}