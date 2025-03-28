package com.mcmostwolf.enchantmentlib.jei;


import com.mcmostwolf.enchantmentlib.api.IToggleEnchantment;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.library.plugins.vanilla.anvil.AnvilRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mcmostwolf.enchantmentlib.WolfEnchantmentLibMain.MODID;

@JeiPlugin
public class JEIView implements IModPlugin {
    public static final RecipeType<WrappedEnchantment> REGISTRY = RecipeType.create(MODID, "ue_enchantments", WrappedEnchantment.class);
    public static final RecipeType<FilterEntry> FILTER = RecipeType.create(MODID, "ue_enchantment_filters", FilterEntry.class);
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "core");
    }
    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        List<ItemStack> items = new ObjectArrayList<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
                ItemStack stack = new ItemStack(item);
                items.add(stack);
        }
        items.removeIf(ItemStack::isEmpty);
        List<WrappedEnchantment> enchantments = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues()).parallelStream().filter(this::isEnabled).map(T -> new WrappedEnchantment(T, items)).collect(Collectors.toCollection(ObjectArrayList::new));
        enchantments.sort(null);
        registration.addRecipes(REGISTRY, enchantments);
        List<FilterEntry> filters = new ObjectArrayList<>();
        List<ItemStack> temp = new ArrayList<>(List.copyOf(items));
        for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues().stream().toList()) {
            for (ItemStack itemStack : items) {
                if (!enchantment.canEnchant(itemStack)) {
                    temp.remove(itemStack);
                }
            }
            filters.add(new FilterEntry(enchantment, temp, Component.translatable(enchantment.getDescriptionId()+".desc")));
            temp = new ArrayList<>(List.copyOf(items));
        }
        registration.addRecipes(FILTER, filters);
    }
    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {

    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new EnchantmentCategory(registry.getJeiHelpers().getGuiHelper(), REGISTRY));
        registry.addRecipeCategories(new FilterCategory(registry.getJeiHelpers().getGuiHelper(), FILTER));
    }


    public boolean isEnabled(Enchantment ench)
    {
        return !(ench instanceof IToggleEnchantment && !((IToggleEnchantment)ench).isEnabled());
    }
}
