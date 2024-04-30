package llama.thaumcraft.items;

import llama.thaumcraft.magic.Aspect;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.blocks.ThaumcraftBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ThaumcraftItemGroups {
    public static final ItemGroup THAUMCRAFT_GROUP = Registry.register(Registries.ITEM_GROUP,
        new Identifier(Thaumcraft.MOD_ID, "thaumcraft"),
        FabricItemGroup.builder().displayName(Text.translatable("thaumcraft.itemgroup.thaumcraft"))
            .icon(() -> new ItemStack(ThaumcraftItems.CRYSTAL)).entries((displayContext, entries) -> {
                entries.add(((ThaumonomiconItem) ThaumcraftItems.THAUMONOMICON).getCreativeModeTabDisplayStack());
                entries.add(ThaumcraftItems.SALIS_MUNDUS);
                entries.add(ThaumcraftBlocks.CRUCIBLE);

                for(Aspect aspect : Aspect.values()) {
                    entries.add(CrystalHelper.create(aspect));
                }
            }).build()
    );

    public static void register() {
        Thaumcraft.LOGGER.info("Registering Item Groups for " + Thaumcraft.MOD_ID);
    }
}
