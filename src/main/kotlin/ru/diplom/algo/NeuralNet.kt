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
import kotlin.math.roundToInt

class NeuralNet(
    private val codeFragment: FragmentCode
) : AbstractAlgorithm(codeFragment) {

    private val net: MultiLayerNetwork
    private val curIteration = Nd4j.zeros(1, 3)

    init {
        net = createNet()
        logger.info { net.summary() }
        val ds = createDataSet()
        net.train(ds)
        logger.info { "After train output: ${net.output(ds.features)}" }
        predictedConditionId = predictNextCondition(null)
    }

    override fun predictNextCondition(currentCond: Condition?): Int {
        for (i in 0..1) {
            curIteration.putScalar(intArrayOf(0, i), curIteration.getInt(0, i + 1))
        }
        curIteration.putScalar(intArrayOf(0, 2), currentCond?.id ?: 0)
        val ds = DataSet(curIteration, curIteration)
        return net.output(ds.features).getDouble(0).roundToInt()
    }

    private fun MultiLayerNetwork.train(ds: DataSet, epoch: Int = 500) {
        for (i in 0 until epoch) {
            this.fit(ds)
        }
    }

    private fun createNet(): MultiLayerNetwork {
        val conf = NeuralNetConfiguration.Builder()
            .updater(Sgd(0.1))
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

    private fun createDataSet(): DataSet {
        val input = Nd4j.zeros(4, 3)
        val labels = Nd4j.zeros(4, 1)

        input.putScalar(intArrayOf(0, 0), 0)
        input.putScalar(intArrayOf(0, 1), 0)
        input.putScalar(intArrayOf(0, 2), 0)
        labels.putScalar(intArrayOf(0, 0), 2)

        input.putScalar(intArrayOf(1, 0), 0)
        input.putScalar(intArrayOf(1, 1), 0)
        input.putScalar(intArrayOf(1, 2), 2)
        labels.putScalar(intArrayOf(1, 0), 3)

        input.putScalar(intArrayOf(2, 0), 0)
        input.putScalar(intArrayOf(2, 1), 2)
        input.putScalar(intArrayOf(2, 2), 3)
        labels.putScalar(intArrayOf(2, 0), 5)

        input.putScalar(intArrayOf(3, 0), 2)
        input.putScalar(intArrayOf(3, 1), 3)
        input.putScalar(intArrayOf(3, 2), 5)
        labels.putScalar(intArrayOf(3, 0), -1)

        return DataSet(input, labels)
    }

}