package ch.skyfy.bettersnowgolem.utils

import ch.skyfy.bettersnowgolem.BetterSnowGolemMod
import net.minecraft.server.network.ServerPlayerEntity
import kotlin.io.path.*

@Suppress("unused")
object ModsUtils {

    fun getPlayerNameWithUUID(spe: ServerPlayerEntity) = "${spe.name.string}#${spe.uuidAsString}"

    fun getPlayerNameFromNameWithUUID(nameWithUUID: String) = if (nameWithUUID.split("#").size == 2) nameWithUUID.split("#")[0] else nameWithUUID

    fun getPlayerUUIDFromNameWithUUID(nameWithUUID: String) = if (nameWithUUID.split("#").size == 2) nameWithUUID.split("#")[1] else BetterSnowGolemMod.PLAYERS_NAMES_AND_UUIDS[nameWithUUID] ?: ""


}