package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.*

/**
 * Алгоритм статический вхождения в первое условие
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
        assertThat(algorithm.countOfHits).isEqualTo(1)
    }

    @Test
    fun `code example two test`() {
        algorithm = StaticalFirstForward(codeExampleTwo.fragmentCode)
        codeExampleTwo.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(1)
        assertThat(algorithm.countOfHits).isEqualTo(1)
    }

    @Test
    fun `code example three test`() {
        algorithm = StaticalFirstForward(codeExampleThree.fragmentCode)
        codeExampleThree.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(12)
        assertThat(algorithm.countOfHits).isEqualTo(20)
    }

    @Test
    fun `code example four test`() {
        algorithm = StaticalFirstForward(codeExampleFour.fragmentCode)
        codeExampleFour.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(2)
        assertThat(algorithm.countOfHits).isEqualTo(3)
    }

    @Test
    fun `code example five test`() {
        algorithm = StaticalFirstForward(codeExampleFive.fragmentCode)
        codeExampleFive.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(8) // 9
        assertThat(algorithm.countOfHits).isEqualTo(13) // 12
    }
}