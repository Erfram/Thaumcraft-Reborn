package llama.thaumcraft.datagen;

import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ThaumcraftItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ThaumcraftItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ThaumcraftTags.Items.TYPE).add(ThaumcraftItems.CRYSTAL);

        getOrCreateTagBuilder(ThaumcraftTags.Items.SHULKER_BOXES).add(
                Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX,
                Items.GRAY_SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BROWN_SHULKER_BOX,
                Items.RED_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX,
                Items.LIME_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.CYAN_SHULKER_BOX,
                Items.LIGHT_BLUE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.PURPLE_SHULKER_BOX,
                Items.MAGENTA_SHULKER_BOX, Items.PINK_SHULKER_BOX
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.STRIPPED_LOGS).add(
                Items.STRIPPED_OAK_LOG, Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_BIRCH_LOG,
                Items.STRIPPED_JUNGLE_LOG, Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_DARK_OAK_LOG,
                Items.STRIPPED_MANGROVE_LOG, Items.STRIPPED_CHERRY_LOG
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.WOODS).add(
                Items.OAK_WOOD, Items.SPRUCE_WOOD, Items.BIRCH_WOOD,
                Items.JUNGLE_WOOD, Items.ACACIA_WOOD, Items.DARK_OAK_WOOD,
                Items.MANGROVE_WOOD, Items.CHERRY_WOOD
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.STRIPPED_WOODS).add(
                Items.STRIPPED_OAK_WOOD, Items.STRIPPED_SPRUCE_WOOD, Items.STRIPPED_BIRCH_WOOD,
                Items.STRIPPED_JUNGLE_WOOD, Items.STRIPPED_ACACIA_WOOD, Items.STRIPPED_DARK_OAK_WOOD,
                Items.STRIPPED_MANGROVE_WOOD, Items.STRIPPED_CHERRY_WOOD
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_STONE_STAIRS).add(
                Items.STONE_STAIRS, Items.ANDESITE_STAIRS, Items.DIORITE_STAIRS, Items.GRANITE_STAIRS
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_STONE_SLAB).add(
                Items.STONE_SLAB, Items.ANDESITE_SLAB, Items.DIORITE_SLAB, Items.GRANITE_SLAB
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_STONE_WALL).add(
                Items.ANDESITE_WALL, Items.DIORITE_WALL, Items.GRANITE_WALL
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_POLISHED_STONE).add(
                Items.POLISHED_ANDESITE, Items.POLISHED_DIORITE, Items.POLISHED_GRANITE
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_POLISHED_STONE_STAIRS).add(
                Items.POLISHED_ANDESITE_STAIRS, Items.POLISHED_DIORITE_STAIRS, Items.POLISHED_GRANITE_STAIRS
        );

        getOrCreateTagBuilder(ThaumcraftTags.Items.BASE_POLISHED_STONE_SLAB).add(
                Items.POLISHED_ANDESITE_SLAB, Items.POLISHED_DIORITE_SLAB, Items.POLISHED_GRANITE_SLAB
        );
    }
}
