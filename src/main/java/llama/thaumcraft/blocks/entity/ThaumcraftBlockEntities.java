package llama.thaumcraft.blocks.entity;

import llama.thaumcraft.Thaumcraft;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ThaumcraftBlockEntities {
    public static final BlockEntityType<CrucibleBlockEntity> CRUCIBLE_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Thaumcraft.MOD_ID, "crucible_block_entity"),
            FabricBlockEntityTypeBuilder.create(CrucibleBlockEntity::new).build()
    );

    public static void register() {
        Thaumcraft.LOGGER.info("Registering ModBlocks for " + Thaumcraft.MOD_ID);
    }
}
