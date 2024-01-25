package llama.thaumcraft;

import llama.thaumcraft.blocks.ThaumcraftBlocks;
import llama.thaumcraft.config.AspectRegistry;
import llama.thaumcraft.config.ItemAspects;
import llama.thaumcraft.items.ThaumcraftItemGroups;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thaumcraft implements ModInitializer {
	public static final String MOD_ID = "thaumcraft";
	public static final String VERSION = "0.1";
	public static final String MOD_NAME = "Thaumcraft Reborn";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemAspects saplings = new ItemAspects(Aspects.HERBA, 15).add(Aspects.VICTUS, 5);

	public static ItemAspects flowers = new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5).add(Aspects.VICTUS, 1);

	public static ItemAspects logs = new ItemAspects(Aspects.HERBA, 20);
	public static ItemAspects stripped_logs = new ItemAspects(Aspects.HERBA, 17);
	public static ItemAspects woods = new ItemAspects(Aspects.HERBA, 22);
	public static ItemAspects stripped_woods = new ItemAspects(Aspects.HERBA, 19);
	public static ItemAspects planks = new ItemAspects(Aspects.HERBA, 3);
	public static ItemAspects stairs = new ItemAspects(Aspects.HERBA, 3);
	public static ItemAspects slabs = new ItemAspects(Aspects.HERBA, 1);
	public static ItemAspects fences = new ItemAspects(Aspects.HERBA, 3);
	public static ItemAspects fences_gates = new ItemAspects(Aspects.HERBA, 7).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5);
	public static ItemAspects doors = new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5);
	public static ItemAspects trapdoors = new ItemAspects(Aspects.HERBA, 6).add(Aspects.MOTUS, 5);
	public static ItemAspects plates = new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5);
	public static ItemAspects buttons = new ItemAspects(Aspects.HERBA, 2).add(Aspects.MACHINA, 5);
	public static ItemAspects signs = new ItemAspects(Aspects.HERBA, 4);
	public static ItemAspects hanging_signs = new ItemAspects(Aspects.HERBA, 10).add(Aspects.METALLUM, 15);
	public static ItemAspects boats = new ItemAspects(Aspects.HERBA, 11).add(Aspects.AQUA, 10).add(Aspects.MOTUS, 15);
	public static ItemAspects chest_boats = new ItemAspects(Aspects.HERBA, 20).add(Aspects.AQUA, 10).add(Aspects.MOTUS, 15);
	public static ItemAspects leaves = new ItemAspects(Aspects.HERBA, 5);
	public static ItemAspects breeds = new ItemAspects(Aspects.TERRA, 5);
	public static ItemAspects polished_breeds = new ItemAspects(Aspects.TERRA, 3);
	public static ItemAspects breeds_stairs = new ItemAspects(Aspects.TERRA, 5);
	public static ItemAspects polished_breeds_stairs = new ItemAspects(Aspects.TERRA, 3);
	public static ItemAspects breeds_slab = new ItemAspects(Aspects.TERRA, 1);
	public static ItemAspects polished_breeds_slab = new ItemAspects(Aspects.TERRA, 1);
	public static ItemAspects breeds_wall = new ItemAspects(Aspects.TERRA, 2);
	public static ItemAspects sandstones = new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15);
	public static ItemAspects sandstone_stairs = new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15);
	public static ItemAspects sandstone_slabs = new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO, 5);
	public static ItemAspects sandstone_walls = new ItemAspects(Aspects.TERRA, 7).add(Aspects.PERDITIO, 7);
	public static ItemAspects chiseled_sandstones = new ItemAspects(Aspects.TERRA, 7).add(Aspects.PERDITIO, 7).add(Aspects.ORDO);
	public static ItemAspects cut_sandstones = new ItemAspects(Aspects.TERRA, 12).add(Aspects.PERDITIO, 12).add(Aspects.ORDO);
	public static ItemAspects cut_sandstone_slabs = new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 3).add(Aspects.ORDO);

	public static ItemAspects shulkerBoxs = new ItemAspects(Aspects.HERBA, 13)
			.add(Aspects.BESTIA, 7)
			.add(Aspects.ALIENIS, 7)
			.add(Aspects.VACOUS, 7)
			.add(Aspects.PRAEMUNIO, 15)
			.add(Aspects.HERBA, 16);

	public static ItemAspects fishs = new ItemAspects(Aspects.VICTUS, 5).add(Aspects.BESTIA, 5).add(Aspects.AQUA, 5);

	@Override
	public void onInitialize() {
		LOGGER.info("Welcome to "+MOD_NAME);

		ThaumcraftItems.register();
		ThaumcraftBlocks.register();
		ThaumcraftItemGroups.register();
		AspectRegistry.register();
		EventHandler.register();
	}
}