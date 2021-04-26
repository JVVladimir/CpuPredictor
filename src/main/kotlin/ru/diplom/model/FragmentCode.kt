import kotlinx.serialization.Serializable

@Serializable
data class FragmentCode(
    val conditions: List<Condition>
)

@Serializable
data class Condition(
    var id: Int,
    val type: ConditionType,
    var countExecutions: Int = 0,
    var root: Condition? = null,
    var conditions: List<Condition> = emptyList()
)

enum class ConditionType {
    IF,
    ELSE_IF,
    ELSE,
    WHILE,
    FOR;
}

fun ConditionType.isInitialCondition() =
    this == ConditionType.IF || this == ConditionType.WHILE || this == ConditionType.FOR