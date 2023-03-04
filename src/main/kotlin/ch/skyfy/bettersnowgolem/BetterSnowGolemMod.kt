package ch.skyfy.bettersnowgolem

import ch.skyfy.bettersnowgolem.callback.SnowGolemEntityOnTickMovementCallback
import ch.skyfy.bettersnowgolem.config.Configs
import ch.skyfy.json5configlib.ConfigManager
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.BlockState
import net.minecraft.util.TypedActionResult
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.file.Path
import kotlin.io.path.*

@Suppress("MemberVisibilityCanBePrivate")
class BetterSnowGolemMod : ModInitializer {

    companion object {
        const val MOD_ID: String = "bettersnowgolem"
        val CONFIG_DIRECTORY: Path = FabricLoader.getInstance().configDir.resolve(MOD_ID)
        val LOGGER: Logger = LogManager.getLogger(BetterSnowGolemMod::class.java)


        val PLAYERS_NAMES_AND_UUIDS = mutableMapOf<String, String>()
        val MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get()

    }

    init {
        ConfigManager.loadConfigs(arrayOf(Configs::class.java))
    }

    override fun onInitialize() {

        SnowGolemEntityOnTickMovementCallback.EVENT.register { snowGolemEntity, damageSource, v ->
            val blockAbove: BlockState = snowGolemEntity.world.getBlockState(snowGolemEntity.blockPos.down())
//            println("tr : " + blockAbove.block.translationKey)
//            println("id: " + Registries.BLOCK.getId(blockAbove.block))
//            println("block tr: " + Registries.BLOCK.getId(blockAbove.block).toTranslationKey())
//            println()

//            Registries.BLOCK.get(Registries.BLOCK.getId(blockAbove.block))
//            Blocks.SNOW.translationKey

            if (Configs.CONFIG.serializableData.allowedBlocks.none { it == blockAbove.block.translationKey })
                return@register TypedActionResult.pass(snowGolemEntity.damage(damageSource, v))
            TypedActionResult.pass(true)
        }


    }

}


