import kotlinx.serialization.Serializable
import ru.diplom.example.Example

@Serializable
data class FragmentCode(
    val example: Example,
    val variables: MutableMap<String, String>, // todo: в идеале не мутабл

    val conditions: List<Condition>
)

@Serializable
data class Condition(
    val type: ConditionType,
    // val name: String,
    var countExecutions: Int = 0
)

enum class ConditionType {
    IF,
    ELSE_IF,
    ELSE,
    WHILE,
    FOR
}
