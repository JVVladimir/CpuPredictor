package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.CodeExampleOne
import ru.diplom.example.CodeExampleTwo

class StaticalFirstForwardTest {

    private lateinit var algorithm: StaticalFirstForward
    private var codeExampleOne = CodeExampleOne()
    private var codeExampleTwo = CodeExampleTwo()

    @Test
    fun `code example one test`() {
        algorithm = StaticalFirstForward(codeExampleOne.fragmentCode)
        codeExampleOne.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(2)
        assertThat(algorithm.countOfHits).isEqualTo(1)
    }

    @Test
    fun `code example one bad test`() {
        algorithm = StaticalFirstForward(codeExampleTwo.fragmentCode)
        codeExampleTwo.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(11)
        assertThat(algorithm.countOfHits).isEqualTo(21)
    }
}