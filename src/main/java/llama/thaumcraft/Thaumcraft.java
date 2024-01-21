package llama.thaumcraft;

import llama.thaumcraft.blocks.ThaumcraftBlocks;
import llama.thaumcraft.items.ThaumcraftItemGroups;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeCache;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Thaumcraft implements ModInitializer {
	public static final String MOD_ID = "thaumcraft";
	public static final String VERSION = "0.1";
	public static final String MOD_NAME = "Thaumcraft Reborn";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final List<Item> ITEMS = new ArrayList<Item>();

	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to "+MOD_NAME);

		ThaumcraftItems.register();
		ThaumcraftBlocks.register();
		ThaumcraftItemGroups.register();
		EventHandler.register();
	}
}