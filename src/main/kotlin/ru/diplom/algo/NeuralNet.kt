package ru.diplom.algo

import Condition
import FragmentCode
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.distribution.UniformDistribution
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.learning.config.Sgd
import org.nd4j.linalg.lossfunctions.LossFunctions
import ru.diplom.example.CodeExample
import kotlin.math.roundToInt

class NeuralNet(
    codeFragment: FragmentCode
) : AbstractAlgorithm(codeFragment) {

    private val net: MultiLayerNetwork
    private val curIteration = Nd4j.zeros(1, 3)

    init {
        net = createNet()
        logger.info { net.summary() }
    }

    fun prepareDataSet(codeExample: CodeExample) {
        codeExample.startCode(this)
        this.clearAlgo()
        val ds = createDataSet(this.input)

        net.train(ds)
        logger.info { "After train output: ${net.output(ds.features)}" }
        predictedConditionId = predictNextCondition(null)
    }

    private fun createDataSet(list: MutableList<Pair<MutableList<Int>, Int>>): DataSet {
        val input = Nd4j.zeros(list.size, 3)
        val labels = Nd4j.zeros(list.size, 1)
        var i = 0
        list.forEach {
            input.putScalar(intArrayOf(i, 0), it.first[0])
            input.putScalar(intArrayOf(i, 1), it.first[1])
            input.putScalar(intArrayOf(i, 2), it.first[2])
            labels.putScalar(intArrayOf(i, 0), it.second)
            i++
        }
        return DataSet(input, labels)
    }

    override fun predictNextCondition(currentCond: Condition?): Int {
        for (i in 0..1) {
            curIteration.putScalar(intArrayOf(0, i), curIteration.getInt(0, i + 1))
        }
        curIteration.putScalar(intArrayOf(0, 2), currentCond?.id ?: 0)
        val ds = DataSet(curIteration, curIteration)
        return net.output(ds.features).getDouble(0).roundToInt()
    }

    private fun MultiLayerNetwork.train(ds: DataSet, epoch: Int = 3000) {
        for (i in 0 until epoch) {
            this.fit(ds)
        }
    }

    private fun createNet(): MultiLayerNetwork {
        val conf = NeuralNetConfiguration.Builder()
            .updater(Sgd(0.035))
            .seed(12345)
            .weightInit(WeightInit.XAVIER)
            .biasInit(0.0)
            .miniBatch(false)
            .list()
            .layer(
                0,
                DenseLayer.Builder()
                    .nIn(3)
                    .nOut(4)
                    .activation(Activation.SIGMOID)
                    .weightInit(UniformDistribution(0.0, 1.0))
                    .build()
            )
            .layer(
                1,
                OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                    .nIn(4)
                    .nOut(1)
                    .activation(Activation.ELU)
                    .weightInit(UniformDistribution(0.0, 1.0))
                    .build()
            )
            .build()

        val net = MultiLayerNetwork(conf)
        net.init()

        net.setListeners(ScoreIterationListener(100))
        return net
    }

    override fun clearAlgo() {
        countOfHits = 0
        countOfMisses = 0
        predictedConditionId = -1
        precise = 0.0
        for (i in 0..2)
            curIteration.putScalar(intArrayOf(0, i), 0)
    }

}