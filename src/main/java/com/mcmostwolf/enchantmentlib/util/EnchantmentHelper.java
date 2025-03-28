package com.mcmostwolf.enchantmentlib.util;

import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentHelper {
    public static String getLocation(Enchantment enchantment) {
        String descriptionId = enchantment.getDescriptionId();
        if (!descriptionId.contains(".")) {
            return "";
        }
        String[] parts = descriptionId.split("\\.");
        if (parts.length < 3) {
            return "";
        }
        String modId = parts[1];
        String enchantmentName = parts[2];
        return modId + ":" + enchantmentName;
    }
}
