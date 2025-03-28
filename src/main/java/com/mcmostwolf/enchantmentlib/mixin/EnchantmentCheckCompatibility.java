package com.mcmostwolf.enchantmentlib.mixin;

import com.mcmostwolf.enchantmentlib.config.EnchantmentsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mcmostwolf.enchantmentlib.util.EnchantmentHelper.getLocation;

@Mixin(DamageEnchantment.class)
public class EnchantmentCheckCompatibility {
    @Inject(method = "checkCompatibility", at = @At("RETURN"), cancellable = true)
    private void injectCheckCompatibility(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            boolean result = true;
            for (String otherLocation : EnchantmentsConfig.getUnableCompatibility(location)) {
                if (other == ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(otherLocation))) {
                    result = false;
                    break;
                }
            }
            cir.setReturnValue(result);
        }
    }
}
