package ch.skyfy.bettersnowgolem.config

import ch.skyfy.json5configlib.Validatable
import io.github.xn32.json5k.SerialComment
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialComment("A list of block where snow golem wont die if standing on")
    val standingAllowedBlocks: MutableSet<String> = mutableSetOf(
        "block.minecraft.snow_block",
        "block.minecraft.blue_ice",
        "block.minecraft.packed_ice"
    ),

//    var nearAllowedBlock: MutableMap<Set<String>, Int> = mutableMapOf(
//        setOf("block.minecraft.blue_ice") to 4
//    ),
//    var nearConfig: NearConfig = NearConfig(
//        "block.minecraft.blue_ice",
//        5,
//        3,
//        listOf(
//            Condition(
//                Operator.AND,
//                NearConfig(
//                    "block.minecraft.packet_ice",
//                    5,
//                    4,
//                    listOf()
//                )
//            )
//        )
//    )
) : Validatable

@Serializable
data class NearConfig(
    val name: String,
    val radius: Int,
    val numberOfBlock: Int,
    val childs: List<Condition>,
)

//abstract class NearConfig {
//    abstract val name: String
//    abstract val radius: Int
//    abstract val numberOfBlock: Int
//    abstract val operator: Operator
//    abstract val childs: List<NearConfig>
//}

@Serializable
data class AndNearConfig(
    var name: String,
    val radius: Int,
    val numberOfBlock: Int,
    @Contextual
    val childs: List<NearConfig>
)

//@Serializable
//data class OrNearConfig(
//    override val name: String, override val radius: Int, override val numberOfBlock: Int, override val operator: Operator, override val childs: List<NearConfig>
//
//) : NearConfig()
@Serializable
data class Condition(
    val operator: Operator,
    val nearConfig: NearConfig
)

enum class Operator() {
    OR,
    AND
}