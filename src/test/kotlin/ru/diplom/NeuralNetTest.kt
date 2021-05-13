package ru.diplom

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.diplom.algo.NeuralNet
import ru.diplom.example.*

/**
 * Алгоритм динамический не интеллектуальный
 *
 * */
class NeuralNetTest {

    private lateinit var algorithm: NeuralNet
    private var codeExampleOne = CodeExampleOne()
    private var codeExampleTwo = CodeExampleTwo()
    private var codeExampleThree = CodeExampleThree()
    private var codeExampleFour = CodeExampleFour()
    private var codeExampleFive = CodeExampleFive()

    @Test
    fun `code example one test`() {
        algorithm = NeuralNet(codeExampleOne.fragmentCode)
        algorithm.prepareDataSet(codeExampleOne)

        codeExampleOne.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(0)
        assertThat(algorithm.countOfHits).isEqualTo(3)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(3)
    }

    @Test
    fun `code example two test`() {
        algorithm = NeuralNet(codeExampleTwo.fragmentCode)
        codeExampleTwo.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(1)
        assertThat(algorithm.countOfHits).isEqualTo(1)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(2)
    }

    @Test
    fun `code example three test`() {
        algorithm = NeuralNet(codeExampleThree.fragmentCode)
        codeExampleThree.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(3)
        assertThat(algorithm.countOfHits).isEqualTo(29)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(32)
    }

    @Test
    fun `code example four test`() {
        algorithm = NeuralNet(codeExampleFour.fragmentCode)
        codeExampleFour.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(3)
        assertThat(algorithm.countOfHits).isEqualTo(2)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(5)
    }

    @Test
    fun `code example five test`() {
        algorithm = NeuralNet(codeExampleFive.fragmentCode)
        codeExampleFive.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(8)
        assertThat(algorithm.countOfHits).isEqualTo(13)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(21)
    }
}