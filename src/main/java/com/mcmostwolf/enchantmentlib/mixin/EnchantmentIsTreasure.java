package com.mcmostwolf.enchantmentlib.mixin;

import com.mcmostwolf.enchantmentlib.config.EnchantmentsConfig;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mcmostwolf.enchantmentlib.util.EnchantmentHelper.getLocation;

@Mixin({
        MendingEnchantment.class,
        SoulSpeedEnchantment.class,
        SwiftSneakEnchantment.class,
        FrostWalkerEnchantment.class,
        VanishingCurseEnchantment.class,
        BindingCurseEnchantment.class
})
public class EnchantmentIsTreasure {
    @Inject(method = "isTreasureOnly", at = @At("RETURN"), cancellable = true)
    private void injectIsTreasureOnly(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.isTreasure(location));
        }
    }
}
