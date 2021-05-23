import kotlinx.serialization.Serializable

@Serializable
data class FragmentCode(
    val conditions: List<Condition>,
    var countExecutions: Int = 0
)

@Serializable
data class Condition(
    var id: Int,
    val type: ConditionType,
    var countExecutions: Int = 0,
    var root: Condition? = null,
    var conditions: List<Condition> = emptyList()
) {
    override fun toString(): String {
        return "0x${Integer.toHexString(id*1203217)}"
    }
}

class ExecutionCountComparator : Comparator<Condition> {

    override fun compare(o1: Condition, o2: Condition): Int {
        return o1.countExecutions.compareTo(o2.countExecutions)
    }
}

enum class ConditionType {
    IF,
    ELSE_IF,
    ELSE,
    WHILE,
    FOR;
}

fun ConditionType.isInitialCondition() =
    this == ConditionType.IF || this == ConditionType.WHILE || this == ConditionType.FOR

fun ConditionType.isCycle() =
    this == ConditionType.WHILE || this == ConditionType.FOR