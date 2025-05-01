package com.mcmostwolf.enchantmentlib.api;

import com.mcmostwolf.enchantmentlib.config.EnchantmentsConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BasicEnchantment extends Enchantment {
    private final boolean isTreasure;
    private final boolean isDiscoverable;
    private final boolean isTradeable;
    private final boolean isCurse;
    private final int maxLevel;
    protected BasicEnchantment(
            EnchantmentCategory pCategory,
            EquipmentSlot[] pApplicableSlots,
            Enchantment.Rarity pRarity,
            boolean isTreasure,
            boolean isDiscoverable,
            boolean isTradeable,
            boolean isCurse,
            int maxLevel) {
        super(pRarity, pCategory, pApplicableSlots);
        this.isTreasure = isTreasure;
        this.isDiscoverable = isDiscoverable;
        this.isTradeable = isTradeable;
        this.isCurse = isCurse;
        this.maxLevel = maxLevel;
    }
    @Override
    public boolean isTreasureOnly() {
        if ((EnchantmentsConfig.isLoad(this) < 2)) {
            return isTreasure;
        }
        else {
            return EnchantmentsConfig.isTreasure(this);
        }
    }
    @Override
    public boolean isDiscoverable() {
        if ((EnchantmentsConfig.isLoad(this) < 2)) {
            return isDiscoverable;
        }
        else {
            return EnchantmentsConfig.couldFound(this);
        }
    }
    @Override
    public boolean isTradeable() {
        if ((EnchantmentsConfig.isLoad(this) < 2)) {
            return isTradeable;
        }
        else {
            return EnchantmentsConfig.couldTrade(this);
        }
    }
    @Override
    public boolean isCurse() {
        if ((EnchantmentsConfig.isLoad(this) < 2)) {
            return isCurse;
        }
        else {
            return EnchantmentsConfig.isCurse(this);
        }
    }
    @Override
    public int getMaxLevel() {
        if ((EnchantmentsConfig.isLoad(this) < 2)) {
            return maxLevel;
        }
        else {
            return EnchantmentsConfig.getMaxLevel(this);
        }
    }
}
