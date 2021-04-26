package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.diplom.algo.StaticalFirstForward
import ru.diplom.example.CodeCodeExampleOne

class StaticalFirstForwardTest {

    private lateinit var algorithm: StaticalFirstForward
    private var codeExampleOne = CodeCodeExampleOne()

    @BeforeEach
    fun init() {
        algorithm = StaticalFirstForward(codeExampleOne.fragmentCode)
    }

    @Test
    fun `code example one test`() {
        codeExampleOne.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(2)
        assertThat(algorithm.countOfHits).isEqualTo(1)
    }
//
//    @Test
//    fun `code example one bad test`() {
//        codeExampleOne.startFragment(algorithm)
//
//        assertThat(algorithm.countOfMisses).isEqualTo(0)
//        assertThat(algorithm.countOfHits).isEqualTo(1)
//    }
}