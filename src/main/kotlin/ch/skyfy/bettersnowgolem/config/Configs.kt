package ch.skyfy.bettersnowgolem.config

import ch.skyfy.bettersnowgolem.BetterSnowGolemMod
import ch.skyfy.json5configlib.ConfigData


object Configs {
    @JvmField
    val CONFIG = ConfigData.invokeSpecial<Config>(BetterSnowGolemMod.CONFIG_DIRECTORY.resolve("config.json5"), false)

    @JvmField
    val AVAILABLE_BLOCKS = ConfigData.invokeSpecial<AvailableBlocks>(BetterSnowGolemMod.CONFIG_DIRECTORY.resolve("available-blocks.json5"), false)
}
