package com.mcmostwolf.enchantmentlib.mixin;

import com.mcmostwolf.enchantmentlib.config.EnchantmentsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mcmostwolf.enchantmentlib.util.EnchantmentHelper.getLocation;

@Mixin({Enchantment.class})
public abstract class EnchantmentReload {
    @Inject(method = "getMaxLevel", at = @At(value = "RETURN"), cancellable = true)
    private void injectGetMaxLevel(CallbackInfoReturnable<Integer> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.getMaxLevel(location));
        }
    }
    @Inject(method = "isTreasureOnly", at = @At("RETURN"), cancellable = true)
    private void injectIsTreasureOnly(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.isTreasure(location));
        }
    }

    @Inject(method = "isTradeable", at = @At("RETURN"), cancellable = true)
    private void injectIsTradeable(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.couldTrade(location));
        }
    }

    @Inject(method = "isCurse", at = @At("RETURN"), cancellable = true)
    private void injectIsCurse(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.isCurse(location));
        }
    }

    @Inject(method = "isDiscoverable", at = @At("RETURN"), cancellable = true)
    private void injectIsDiscoverable(CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.couldFound(location));
        }
    }

    @Inject(method = "canApplyAtEnchantingTable", at = @At("RETURN"), cancellable = true, remap = false)
    private void injectCanApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.couldEnchantTable(location) && cir.getReturnValue());
        }
    }

    @Inject(method = "canEnchant", at = @At("RETURN"), cancellable = true)
    private void injectCanEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.couldAnvil(location) && cir.getReturnValue());
        }
    }

    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void injectGetRarity(CallbackInfoReturnable<Enchantment.Rarity> cir) {
        Enchantment enchantment = (Enchantment) (Object) this;
        String location = getLocation(enchantment);
        if (EnchantmentsConfig.isLoad(enchantment) >= 2) {
            cir.setReturnValue(EnchantmentsConfig.getRarityByConfig(location));
        }
    }

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