package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.*

/**
 * Алгоритм статический не интеллектуальный вхождения в первое условие
 *
 * */
class StaticalFirstForwardTest {

    private lateinit var algorithm: StaticalFirstForward
    private var codeExampleOne = CodeExampleOne()
    private var codeExampleTwo = CodeExampleTwo()
    private var codeExampleThree = CodeExampleThree()
    private var codeExampleFour = CodeExampleFour()
    private var codeExampleFive = CodeExampleFive()

    @Test
    fun `code example one test`() {
        algorithm = StaticalFirstForward(codeExampleOne.fragmentCode)
        codeExampleOne.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(2)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(4)
    }

    @Test
    fun `code example two test`() {
        algorithm = StaticalFirstForward(codeExampleTwo.fragmentCode)
        codeExampleTwo.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(1)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(3)
    }

    @Test
    fun `code example three test`() {
        algorithm = StaticalFirstForward(codeExampleThree.fragmentCode)
        codeExampleThree.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(12)
        assertThat(algorithm.countOfHits).isEqualTo(20)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(32)
    }

    @Test
    fun `code example four test`() {
        algorithm = StaticalFirstForward(codeExampleFour.fragmentCode)
        codeExampleFour.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(4)
        assertThat(algorithm.countOfHits).isEqualTo(5)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(9)
    }

    @Test
    fun `code example five test`() {
        algorithm = StaticalFirstForward(codeExampleFive.fragmentCode)
        codeExampleFive.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(8)
        assertThat(algorithm.countOfHits).isEqualTo(13)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(21)
    }
}