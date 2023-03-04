package ch.skyfy.bettersnowgolem.config

import ch.skyfy.json5configlib.Defaultable
import ch.skyfy.json5configlib.Validatable
import io.github.xn32.json5k.SerialComment
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialComment("A list of block where snow golem wont die if above in a hot biome")
    val allowedBlocks: MutableSet<String>
) : Validatable

class DefaultConfig : Defaultable<Config> {
    override fun getDefault(): Config {
        return Config(
            mutableSetOf(
                "block.minecraft.snow_block",
                "block.minecraft.blue_ice",
                "block.minecraft.packed_ice"
            )
        )
    }
}