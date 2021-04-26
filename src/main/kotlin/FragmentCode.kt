import kotlinx.serialization.Serializable

@Serializable
data class FragmentCode(
    val variables: Map<String, String>,

    val conditions: List<Condition>
)

@Serializable
data class Condition(
    val type: ConditionType,
    val name: String,
    val countExecutions: Int
)

enum class ConditionType {
    IF,
    ELSE_IF,
    ELSE,
    WHILE,
    FOR
}
