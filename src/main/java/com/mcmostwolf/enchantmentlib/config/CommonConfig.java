package com.mcmostwolf.enchantmentlib.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static ForgeConfigSpec COMMON;
    public static ForgeConfigSpec.BooleanValue LOAD_ALL_CONFIGS;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("附魔库配置");
        builder.push("common");
        LOAD_ALL_CONFIGS = builder.comment("是否加载全部附魔配置(注意，开启后会严重降低加载速度)")
                .define("load_all_configs", false);
        builder.pop();
        COMMON = builder.build();
    }
}
