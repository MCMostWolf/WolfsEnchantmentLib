package com.mcmostwolf.enchantmentlib.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EnchantmentButton extends Button {
    private Enchantment enchantment;
    public EnchantmentButton(int x, int y, int width, int height, Component label, Button.OnPress onPress, Enchantment enchantment) {
        super(x, y, width, height, label, onPress, DEFAULT_NARRATION);
        this.enchantment = enchantment;
    }
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.renderItem(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1)), getX(), getY());
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(enchantment.getDescriptionId()), getX() - 8 + width / 2, getY() + height / 2 + 4, 0xFFFFFF);
    }
}
