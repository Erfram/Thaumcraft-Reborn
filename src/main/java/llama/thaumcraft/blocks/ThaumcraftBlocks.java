package llama.thaumcraft.blocks;

import llama.thaumcraft.Thaumcraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ThaumcraftBlocks {
    public static final Block CRUCIBLE = registerBlock("crucible",
            new CrucibleBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.STONE).nonOpaque().ticksRandomly()));

    private static Block registerBlock(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(Thaumcraft.MOD_ID, id), block);
    }

    private static Item registerBlockItem(String id, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Thaumcraft.MOD_ID, id),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void register() {
        Thaumcraft.LOGGER.info("Registering ModBlocks for " + Thaumcraft.MOD_ID);
    }
}
