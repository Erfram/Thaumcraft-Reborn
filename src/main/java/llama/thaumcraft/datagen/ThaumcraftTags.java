package llama.thaumcraft.datagen;

import llama.thaumcraft.Thaumcraft;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ThaumcraftTags {
    public static class Items {
        public static final TagKey<Item> type = createTag("type");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Thaumcraft.MOD_ID, name));
        }
    }
}