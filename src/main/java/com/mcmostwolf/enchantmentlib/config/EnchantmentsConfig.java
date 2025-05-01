package com.mcmostwolf.enchantmentlib.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EnchantmentsConfig {
    static Gson gson = new Gson();
    public static void loadConfig(Enchantment enchantment) {
        String[] parts = enchantment.getDescriptionId().split("\\.");
        String modId = parts[1];
        String enchantmentName = parts[2];
        Path configDir = FMLPaths.CONFIGDIR.get().resolve(modId).resolve("enchantments");
        Path configPath = configDir.resolve(enchantmentName + ".json");
        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            else if (!Files.exists(configPath)) {
                createDefaultConfig(configPath, enchantment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultConfig(Path configPath, Enchantment enchantment) throws IOException {
        String[] parts = enchantment.getDescriptionId().split("\\.");
        String modId = parts[1];
        String enchantmentName = parts[2];
        String defaultContent = String.format("""
                        {
                            "enchantmentName": "%s",
                            "isTreasure":%b,
                            "couldFound":%b,
                            "couldEnchantTable":%b,
                            "couldAnvil":%b,
                            "couldTrade":%b,
                            "isCurse":%b,
                            "maxLevel":%d,
                            "quality":%d,
                            "unableCompatibility":[]
                        }
                    """, modId+":"+enchantmentName, enchantment.isTreasureOnly(), enchantment.isDiscoverable(), !enchantment.isTreasureOnly(), true, enchantment.isTradeable(), enchantment.isCurse(), enchantment.getMaxLevel(), getQualityByRandom(enchantment.getRarity()));
        Files.writeString(configPath, defaultContent);
    }
    public static boolean isTreasure(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.isTreasure;
    }
    public static boolean couldFound(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.couldFound;
    }
    public static Integer getMaxLevel(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.maxLevel;
    }

    public static boolean couldEnchantTable(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.couldEnchantTable;
    }

    public static boolean couldAnvil(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.couldAnvil;
    }
    public static boolean couldTrade(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.couldTrade;
    }
    public static boolean isCurse(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.isCurse;
    }
    public static List<String> getUnableCompatibility(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return config.unableCompatibility != null
                ? config.unableCompatibility
                : List.of();
    }

    public static Enchantment.Rarity getRarityByConfig(Enchantment enchantment) {
        EnchantmentConfig config = getEnchantmentConfig(enchantment);
        return switch (config.quality) {
            case 1 -> Enchantment.Rarity.UNCOMMON;
            case 2 -> Enchantment.Rarity.RARE;
            case 3 -> Enchantment.Rarity.VERY_RARE;
            default -> Enchantment.Rarity.COMMON;
        };
    }
    private static int getQualityByRandom(Enchantment.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> 0;
            case UNCOMMON -> 1;
            case RARE -> 2;
            case VERY_RARE -> 3;
        };
    }
    public static int isLoad(Enchantment enchantment) {
        String[] parts = enchantment.getDescriptionId().split("\\.");
        String modId = parts[1];
        String enchantmentName = parts[2];
        Path configDir = FMLPaths.CONFIGDIR.get().resolve(modId).resolve("enchantments");
        Path configPath = configDir.resolve(enchantmentName + ".json");
        if (!Files.exists(configDir)) {
            return 0;
        }
        else if (!Files.exists(configPath)) {
            return 1;
        }
        else {
            return 2;
        }
    }
    private static EnchantmentConfig getEnchantmentConfig(Enchantment enchantment) {
        String[] parts = enchantment.getDescriptionId().split("\\.");
        String modId = parts[1];
        String enchantmentName = parts[2];
        Path configDir = FMLPaths.CONFIGDIR.get().resolve(modId).resolve("enchantments");
        Path configPath = configDir.resolve(enchantmentName + ".json");
        EnchantmentConfig configs = null;
        try {
            String content = Files.readString(configPath);
            configs = gson.fromJson(content, new TypeToken<EnchantmentConfig>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configs;
    }
}