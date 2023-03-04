package ch.skyfy.bettersnowgolem

import ch.skyfy.bettersnowgolem.callback.SnowGolemEntityOnTickMovementCallback
import ch.skyfy.bettersnowgolem.commands.ReloadFilesCmd
import ch.skyfy.bettersnowgolem.config.AvailableBlocks
import ch.skyfy.bettersnowgolem.config.Configs
import ch.skyfy.json5configlib.ConfigManager
import ch.skyfy.json5configlib.updateIterable
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.registry.Registries
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
    }

    init { ConfigManager.loadConfigs(arrayOf(Configs::class.java)) }

    override fun onInitialize() {
        registerCommands()

        Configs.AVAILABLE_BLOCKS.updateIterable(AvailableBlocks::allowedBlocks) {
            it.clear()
            it.addAll(Registries.BLOCK.ids.map { b -> b.toTranslationKey() })
        }

        SnowGolemEntityOnTickMovementCallback.EVENT.register { snowGolemEntity, damageSource, v ->
            if (Configs.CONFIG.serializableData.allowedBlocks.none { it == snowGolemEntity.world.getBlockState(snowGolemEntity.blockPos.down()).block.translationKey })
                return@register TypedActionResult.pass(snowGolemEntity.damage(damageSource, v))
            TypedActionResult.pass(true)
        }

    }

    private fun registerCommands() = CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
        ReloadFilesCmd.register(dispatcher)
    }

}


