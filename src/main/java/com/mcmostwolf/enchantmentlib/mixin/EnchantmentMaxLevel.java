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
        ArrowDamageEnchantment.class,
        ArrowKnockbackEnchantment.class,
        ArrowPiercingEnchantment.class,
        DamageEnchantment.class,
        DigDurabilityEnchantment.class,
        DiggingEnchantment.class,
        FireAspectEnchantment.class,
        FishingSpeedEnchantment.class,
        FrostWalkerEnchantment.class,
        KnockbackEnchantment.class,
        LootBonusEnchantment.class,
        OxygenEnchantment.class,
        ProtectionEnchantment.class,
        QuickChargeEnchantment.class,
        SoulSpeedEnchantment.class,
        SweepingEdgeEnchantment.class,
        SwiftSneakEnchantment.class,
        ThornsEnchantment.class,
        TridentImpalerEnchantment.class,
        TridentLoyaltyEnchantment.class,
        TridentRiptideEnchantment.class,
        WaterWalkerEnchantment.class,
})
public class EnchantmentMaxLevel {
    @Inject(method = "getMaxLevel", at = @At(value = "RETURN"), cancellable = true)
    private void injectGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.getMaxLevel(location));
        }
    }
}
