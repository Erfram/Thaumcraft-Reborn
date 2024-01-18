package llama.thaumcraft.items;

import llama.thaumcraft.Aspect;
import llama.thaumcraft.Thaumcraft;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ThaumcraftItemGroups {
    public static final ItemGroup THAUMCRAFT_GROUP = Registry.register(Registries.ITEM_GROUP,
        new Identifier(Thaumcraft.MOD_ID, "thaumcraft"),
        FabricItemGroup.builder().displayName(Text.translatable("thaumcraft.itemgroup.thaumcraft"))
                .icon(() -> new ItemStack(ThaumcraftItems.CRYSTAL)).entries((displayContext, entries) -> {
                    entries.add(ThaumcraftItems.CRYSTAL);
                    entries.add(CrystalItem.create(Aspect.AER));
                    entries.add(CrystalItem.create(Aspect.IGNIS));
                    entries.add(CrystalItem.create(Aspect.TERRA));
                    entries.add(CrystalItem.create(Aspect.AQUA));
                    entries.add(CrystalItem.create(Aspect.ORDO));
                    entries.add(CrystalItem.create(Aspect.PERDITIO));

                    entries.add(Items.DIAMOND);
                }).build()
    );

    public static void register() {
        Thaumcraft.LOGGER.info("Registering Item Groups for " + Thaumcraft.MOD_ID);
    }
}