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
        assertThat(algorithm.countOfHits).isEqualTo(4)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(4)
    }

    @Test
    fun `code example two test`() {
        algorithm = NeuralNet(codeExampleTwo.fragmentCode)
        algorithm.prepareDataSet(codeExampleTwo)

        codeExampleTwo.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(0)
        assertThat(algorithm.countOfHits).isEqualTo(3)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(3)
    }

    @Test
    fun `code example three test`() {
        algorithm = NeuralNet(codeExampleThree.fragmentCode)
        algorithm.prepareDataSet(codeExampleThree)

        codeExampleThree.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(0)
        assertThat(algorithm.countOfHits).isEqualTo(32)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(32)
    }

    @Test
    fun `code example four test`() {
        algorithm = NeuralNet(codeExampleFour.fragmentCode)
        algorithm.prepareDataSet(codeExampleFour)

        codeExampleFour.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(1)
        assertThat(algorithm.countOfHits).isEqualTo(8)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(9)
    }

    @Test
    fun `code example five test`() {
        algorithm = NeuralNet(codeExampleFive.fragmentCode)
        algorithm.prepareDataSet(codeExampleFive)

        codeExampleFive.startCode(algorithm)

        assertThat(algorithm.countOfMisses).isEqualTo(8)
        assertThat(algorithm.countOfHits).isEqualTo(13)
        assertThat(algorithm.countOfHits + algorithm.countOfMisses).isEqualTo(21)
    }
}