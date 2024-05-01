package llama.thaumcraft;

import llama.thaumcraft.blocks.ThaumcraftBlocks;
import llama.thaumcraft.blocks.entity.ThaumcraftBlockEntities;
import llama.thaumcraft.items.ThaumcraftItemGroups;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thaumcraft implements ModInitializer {
	public static final String MOD_ID = "thaumcraft";
	public static final String VERSION = "0.1";
	public static final String MOD_NAME = "Thaumcraft Reborn";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ThaumcraftBlocks.register();
		ThaumcraftBlockEntities.register();
		ThaumcraftItems.register();
		ThaumcraftItemGroups.register();
		EventHandler.register();
	}
}