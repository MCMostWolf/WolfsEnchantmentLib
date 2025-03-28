package com.mcmostwolf.enchantmentlib.util;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class HotKey {
    public static final Lazy<KeyMapping> OPEN_GUI = Lazy.of(() -> new KeyMapping(
            "key.mcmostwolf.enchantmentlib.open_gui",
            GLFW.GLFW_KEY_B,
            "key.mcmostwolf.enchantmentlib"
    ));
}
