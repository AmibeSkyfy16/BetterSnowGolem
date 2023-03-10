package ch.skyfy.bettersnowgolem

import ch.skyfy.bettersnowgolem.callback.SnowGolemEntityOnTickMovementCallback
import ch.skyfy.bettersnowgolem.commands.ReloadFilesCmd
import ch.skyfy.bettersnowgolem.config.AvailableBlocks
import ch.skyfy.bettersnowgolem.config.Configs
import ch.skyfy.bettersnowgolem.config.NearConfig
import ch.skyfy.bettersnowgolem.config.Operator
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

    init {
        ConfigManager.loadConfigs(arrayOf(Configs::class.java))
    }

    override fun onInitialize() {
        registerCommands()

        Configs.AVAILABLE_BLOCKS.updateIterable(AvailableBlocks::allowedBlocks) {
            it.clear()
            it.addAll(Registries.BLOCK.ids.map { id -> Registries.BLOCK.get(id).translationKey })
        }

        SnowGolemEntityOnTickMovementCallback.EVENT.register { snowGolemEntity, damageSource, v ->
            if (Configs.CONFIG.serializableData.standingAllowedBlocks.none { it == snowGolemEntity.world.getBlockState(snowGolemEntity.blockPos.down()).block.translationKey })
                return@register TypedActionResult.pass(snowGolemEntity.damage(damageSource, v))

//            val root = Configs.CONFIG.serializableData.nearConfig
//            Configs.CONFIG.serializableData.nearConfig.childs.forEach { cond ->
//
//            }

            TypedActionResult.pass(true)
        }

    }

    fun isAllowed(nearConfig: NearConfig, rootResult: Boolean  = false) : Boolean{
        var rootResult2 = rootResult
        if(nearConfig.name.contains("blue_ice")){
            rootResult2 = true
        }

        if(nearConfig.childs.any { it.operator == Operator.AND }){
            if(nearConfig.childs.filter { it.operator == Operator.AND }.any { isAllowed(it.nearConfig, rootResult2) }){
                rootResult2 = rootResult && true
            }else{
                rootResult2 = rootResult && false
            }
        }else{

            return rootResult2
        }
        return rootResult2
    }

    private fun registerCommands() = CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
        ReloadFilesCmd.register(dispatcher)
    }

}


