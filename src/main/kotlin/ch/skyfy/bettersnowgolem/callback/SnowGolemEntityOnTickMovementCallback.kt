package ch.skyfy.bettersnowgolem.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.passive.SnowGolemEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.TypedActionResult

@FunctionalInterface
fun interface SnowGolemEntityOnTickMovementCallback {

    companion object {
        @JvmField
        val EVENT: Event<SnowGolemEntityOnTickMovementCallback> = EventFactory.createArrayBacked(SnowGolemEntityOnTickMovementCallback::class.java) { listeners ->
            SnowGolemEntityOnTickMovementCallback { snowGolemEntity, damageSource, v ->
                for (listener in listeners) {
                    val result = listener.onDamage(snowGolemEntity, damageSource, v)
                    if (result.result != ActionResult.PASS) {
                        return@SnowGolemEntityOnTickMovementCallback result
                    }
                }
                return@SnowGolemEntityOnTickMovementCallback TypedActionResult.pass(true)
            }
        }
    }

    fun onDamage(snowGolemEntity: SnowGolemEntity, damageSource: DamageSource, v: Float) : TypedActionResult<Boolean>

}