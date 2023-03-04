package ch.skyfy.bettersnowgolem.config

import ch.skyfy.bettersnowgolem.BetterSnowGolemMod
import ch.skyfy.json5configlib.ConfigData


object Configs {
    @JvmField
    val CONFIG = ConfigData.invoke<Config, DefaultConfig>(BetterSnowGolemMod.CONFIG_DIRECTORY.resolve("config.json5"), false)
}
