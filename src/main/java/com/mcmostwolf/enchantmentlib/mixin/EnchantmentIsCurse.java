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
        BindingCurseEnchantment.class,
        VanishingCurseEnchantment.class
})
public class EnchantmentIsCurse {
    @Inject(method = "isCurse", at = @At("RETURN"), cancellable = true)
    private void injectIsCurse(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.isCurse(location));
        }
    }
}
