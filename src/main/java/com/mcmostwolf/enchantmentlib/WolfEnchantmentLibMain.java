package com.mcmostwolf.enchantmentlib;

import com.mcmostwolf.enchantmentlib.config.EnchantmentsConfig;
import com.mcmostwolf.enchantmentlib.event.ClientEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

import static com.mcmostwolf.enchantmentlib.util.HotKey.OPEN_GUI;

@Mod(WolfEnchantmentLibMain.MODID)
public class WolfEnchantmentLibMain {
    public static final String MODID = "wolfenchantmentlib";

    public WolfEnchantmentLibMain(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup));
    }
    @OnlyIn(Dist.CLIENT)
    private void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvent());
        Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, OPEN_GUI.get());
    }
    private void onCommonSetup(FMLCommonSetupEvent event){
        ForgeRegistries.ENCHANTMENTS.getValues().forEach(EnchantmentsConfig::loadConfig);
    }
}
