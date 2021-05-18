package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.diplom.algo.DynamicalAnalysis
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.*

/**
 * Алгоритм динамический не интеллектуальный
 *
 * */
class DynamicAnalysisTest {

    private lateinit var algorithm: DynamicalAnalysis
    private var codeExampleOne = CodeExampleOne()
    private var codeExampleTwo = CodeExampleTwo()
    private var codeExampleThree = CodeExampleThree()
    private var codeExampleFour = CodeExampleFour()
    private var codeExampleFive = CodeExampleFive()

    @Test
    fun `code example one test`() {
        algorithm = DynamicalAnalysis(codeExampleOne.fragmentCode)
        repeat(2) {
            codeExampleOne.startCode(algorithm)
            algorithm = DynamicalAnalysis(codeExampleOne.fragmentCode)
        }

        assertThat(algorithm.countOfMisses).isEqualTo(2)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(4)
    }

    @Test
    fun `code example two test`() {
        algorithm = DynamicalAnalysis(codeExampleTwo.fragmentCode)
        codeExampleTwo.startCode(algorithm)
        assertThat(algorithm.countOfMisses).isEqualTo(1)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(3)
    }

    @Test
    fun `code example three test`() {
        algorithm = DynamicalAnalysis(codeExampleThree.fragmentCode)
        codeExampleThree.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(3)
        assertThat(algorithm.countOfHits).isEqualTo(29)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(32)
    }

    @Test
    fun `code example four test`() {
        repeat(2) {
            algorithm = DynamicalAnalysis(codeExampleFour.fragmentCode)
            codeExampleFour.startCode(algorithm)
            algorithm.clearAlgo()
        }

        assertThat(algorithm.countOfMisses).isEqualTo(3)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(5)
    }

    @Test
    fun `code example five test`() {
        algorithm = DynamicalAnalysis(codeExampleFive.fragmentCode)
        repeat(2) {
            codeExampleFive.startCode(algorithm)
            algorithm.clearAlgo()
        }

        assertThat(algorithm.countOfMisses).isEqualTo(8)
        assertThat(algorithm.countOfHits).isEqualTo(13)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(21)
    }
}