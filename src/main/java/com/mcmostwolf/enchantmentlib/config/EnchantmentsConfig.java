package com.mcmostwolf.enchantmentlib.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentsConfig {
    private static final Map<String, Boolean> isTreasureMap = new HashMap<>();
    private static final Map<String, Boolean> couldFoundMap = new HashMap<>();
    private static final Map<String, Boolean> couldEnchantTableMap = new HashMap<>();
    private static final Map<String, Boolean> couldAnvilMap = new HashMap<>();
    private static final Map<String, Boolean> couldTradeMap = new HashMap<>();
    private static final Map<String, Boolean> isCurseMap = new HashMap<>();
    private static final Map<String, Integer> maxLevelMap = new HashMap<>();
    private static final Map<String, Integer> qualityMap = new HashMap<>();
    private static final Map<String, List<String>> unableCompatibilityMap = new HashMap<>();
    public static void loadConfig(Enchantment enchantment) {
        Gson gson = new Gson();
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
            String content = Files.readString(configPath);

            List<EnchantmentConfig> configs = gson.fromJson(content, new TypeToken<List<EnchantmentConfig>>(){}.getType());

            for (EnchantmentConfig config : configs) {
                String key = config.enchantmentName;
                isTreasureMap.put(key, config.isTreasure);
                couldFoundMap.put(key, config.couldFound);
                couldEnchantTableMap.put(key, config.couldEnchantTable);
                couldAnvilMap.put(key, config.couldAnvil);
                couldTradeMap.put(key, config.couldTrade);
                isCurseMap.put(key, config.isCurse);
                maxLevelMap.put(key, config.maxLevel);
                qualityMap.put(key, config.quality);
                List<String> compatibility = config.unableCompatibility != null
                        ? config.unableCompatibility
                        : List.of();
                unableCompatibilityMap.put(key, compatibility);
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
                    [
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
                    ]""", modId+":"+enchantmentName, enchantment.isTreasureOnly(), enchantment.isDiscoverable(), !enchantment.isTreasureOnly(), true, enchantment.isTradeable(), enchantment.isCurse(), enchantment.getMaxLevel(), getQualityByRandom(enchantment.getRarity()));
        Files.writeString(configPath, defaultContent);
    }
    public static boolean isTreasure(String enchantmentName) {
        return isTreasureMap.getOrDefault(enchantmentName, false);
    }
    public static boolean couldFound(String enchantmentName) {
        return couldFoundMap.getOrDefault(enchantmentName, false);
    }
    public static Integer getMaxLevel(String enchantmentName) {
        return maxLevelMap.getOrDefault(enchantmentName, 1);
    }

    public static boolean couldEnchantTable(String enchantmentName) {
        return couldEnchantTableMap.getOrDefault(enchantmentName, false);
    }

    public static boolean couldAnvil(String enchantmentName) {
        return couldAnvilMap.getOrDefault(enchantmentName, false);
    }
    public static boolean couldTrade(String enchantmentName) {
        return couldTradeMap.getOrDefault(enchantmentName, false);
    }
    public static boolean isCurse(String enchantmentName) {
        return isCurseMap.getOrDefault(enchantmentName, false);
    }
    public static List<String> getUnableCompatibility(String enchantmentName) {
        return unableCompatibilityMap.getOrDefault(enchantmentName, List.of());
    }

    public static Enchantment.Rarity getRarityByConfig(String enchantmentName) {
        return switch (qualityMap.getOrDefault(enchantmentName, 0)) {
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
}