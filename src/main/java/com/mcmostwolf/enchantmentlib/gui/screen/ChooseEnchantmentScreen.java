package com.mcmostwolf.enchantmentlib.gui.screen;

import com.mcmostwolf.enchantmentlib.gui.button.EnchantmentButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class ChooseEnchantmentScreen extends Screen {
    public ChooseEnchantmentScreen() {
        super(Component.empty());
    }
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
    @Override
    public void init() {
        clearWidgets();
        AtomicInteger enchantId = new AtomicInteger();
        ForgeRegistries.ENCHANTMENTS.getValues().forEach(enchantment -> {
            int rows = enchantId.get()%10;
            int columns = enchantId.get()/10;
            addRenderableWidget(new EnchantmentButton(42 * (rows+2), 28 * (columns+1), 40, 20, Component.translatable(enchantment.getDescriptionId()), button -> {
                minecraft.setScreen(new SetEnchantmentScreen(enchantment));
            }, enchantment));
            enchantId.getAndIncrement();
        });
    }
}
