package com.mcmostwolf.enchantmentlib.event;


import com.mcmostwolf.enchantmentlib.util.HotKey;
import com.mcmostwolf.enchantmentlib.gui.screen.ChooseEnchantmentScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvent {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (HotKey.OPEN_GUI.get().consumeClick()) {
           // Minecraft.getInstance().setScreen(new ChooseEnchantmentScreen());
        }
    }
}
