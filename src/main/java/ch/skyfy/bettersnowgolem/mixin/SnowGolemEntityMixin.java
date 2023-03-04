package ch.skyfy.bettersnowgolem.mixin;

import ch.skyfy.bettersnowgolem.callback.SnowGolemEntityOnTickMovementCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {

    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/SnowGolemEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    public boolean onTickMovement(SnowGolemEntity instance, DamageSource damageSource, float v){
        return SnowGolemEntityOnTickMovementCallback.EVENT.invoker().onDamage(instance, damageSource, v).getValue();
    }

}
