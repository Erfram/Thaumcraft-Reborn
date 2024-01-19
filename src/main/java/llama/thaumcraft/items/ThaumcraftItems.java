package llama.thaumcraft.items;

import llama.thaumcraft.Thaumcraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ThaumcraftItems {
    public static final Item CRYSTAL = registerItem("crystal", new CrystalItem(new FabricItemSettings()));
    public static final Item THAUMONOMICON = registerItem("thaumonomicon", new Item(new FabricItemSettings()));

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Thaumcraft.MOD_ID, id), item);
    }
    public static void register() {
        Thaumcraft.LOGGER.debug("Registering items for: "+Thaumcraft.MOD_NAME);
    }
}