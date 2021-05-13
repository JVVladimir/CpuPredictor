const val MAX_ALGO = 5

fun main() {
    println("Введите номер примера кода от 1 до 5")
    val sampleNum = readLine() ?: throw RuntimeException("Введена пустота!")
    if (sampleNum.toInt() < 1 || sampleNum.toInt() > MAX_ALGO)
        throw RuntimeException("Ошибка, сынок!")
    println(
        """
        Варианты алгоритмов:
            1. Статический не интеллектуальный +
            2. Статический интеллектуальный
            3. Динамический интеллектуальный +
            4. Динамический, основанный на перцептроне
            5. Условие завершения/продолжения цикла
        Введите номер алгоритма от 1 до $MAX_ALGO""".trimIndent()
    )
    val algNum = readLine() ?: throw RuntimeException("Введена пустота!")
    if (algNum.toInt() < 1 || algNum.toInt() > MAX_ALGO)
        throw RuntimeException("Ошибка, сынок!")
    println("Введены значения: $sampleNum $algNum")
}