package com.mcmostwolf.enchantmentlib.gui.screen;

import com.mcmostwolf.enchantmentlib.gui.button.enchantconfig.IsCursedButton;
import com.mcmostwolf.enchantmentlib.gui.button.enchantconfig.IsTradeableButton;
import com.mcmostwolf.enchantmentlib.gui.button.enchantconfig.IsTreasureButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

public class SetEnchantmentScreen extends Screen {
    private Enchantment enchantment;
    private boolean isCursed;
    private boolean isTradeable;
    private boolean isTreasure;
    protected SetEnchantmentScreen(Enchantment enchantment) {
        super(Component.empty());
        this.enchantment = enchantment;
        this.isCursed = enchantment.isCurse();
        this.isTradeable = enchantment.isTradeable();
        this.isTreasure = enchantment.isTreasureOnly();
    }
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        guiGraphics.renderItem(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1)), 10, 10, 200, 200);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(enchantment.getDescriptionId()), 10, 40, 0xFFFFFF);
        if (isCursed) {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("enchantmentlib.gui.isCursed"), 120, 100, 0xFFFFFF);
        }
        else {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("enchantmentlib.gui.isNotCursed"), 120, 100, 0xFFFFFF);
        }
        if (isTradeable) {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("enchantmentlib.gui.isTradeable"), 120, 160, 0xFFFFFF);
        }
        else {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("enchantmentlib.gui.isNotTradeable"), 120, 160, 0xFFFFFF);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void init() {
        clearWidgets();
        addRenderableWidget(new IsCursedButton(60, 100, 70, 25, Component.translatable("enchantmentlib.gui.isCursed"), button -> {
            isCursed = !isCursed;
        }));
        addRenderableWidget(new IsTradeableButton(60, 160, 70, 25, Component.translatable("enchantmentlib.gui.isTradeable"), button -> {
            isTradeable = !isTradeable;
        }));
        addRenderableWidget(new IsTreasureButton(60, 220, 70, 25, Component.translatable("enchantmentlib.gui.isTreasure"), button -> {
            isTreasure = !isTreasure;
        }));
    }
}
