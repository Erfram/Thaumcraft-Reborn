package llama.thaumcraft.datagen;

import llama.thaumcraft.Thaumcraft;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ThaumcraftTags {
    public static class Items {
        public static final TagKey<Item> TYPE = createTag("type");
        public static final TagKey<Item> SHULKER_BOXES = createTag("shulker_boxes");
        public static final TagKey<Item> STRIPPED_LOGS = createTag("stripped_logs");
        public static final TagKey<Item> WOODS = createTag("woods");
        public static final TagKey<Item> STRIPPED_WOODS = createTag("stripped_woods");
        public static final TagKey<Item> BASE_STONE_STAIRS = createTag("base_stone_stairs");
        public static final TagKey<Item> BASE_STONE_SLAB = createTag("base_stone_slab");
        public static final TagKey<Item> BASE_STONE_WALL = createTag("base_stone_wall");
        public static final TagKey<Item> BASE_POLISHED_STONE = createTag("base_polished_stone");
        public static final TagKey<Item> BASE_POLISHED_STONE_STAIRS = createTag("base_polished_stone_stairs");
        public static final TagKey<Item> BASE_POLISHED_STONE_SLAB = createTag("base_polished_stone_slab");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Thaumcraft.MOD_ID, name));
        }
    }
}