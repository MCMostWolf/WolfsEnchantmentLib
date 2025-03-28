package com.mcmostwolf.enchantmentlib.gui.button.enchantconfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class IsCursedButton extends Button{
    public IsCursedButton(int x, int y, int width, int height, Component label, Button.OnPress onPress) {
        super(x, y, width, height, label, onPress, DEFAULT_NARRATION);
    }
    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.fill(getX(), getY(), getX() + width, getY() + height, 0xFF000000);
        guiGraphics.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, 0xFF808080);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable("是否是诅咒"), getX(), getY(), 0xFFFFFF);
    }
}
