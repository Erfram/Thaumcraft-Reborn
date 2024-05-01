package llama.thaumcraft.magic;

import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.datagen.ThaumcraftTags;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class AspectRegistry {
    public static final Map<Map<Item, NbtCompound>, Map<Aspect, Integer>> ITEMS = new LinkedHashMap<>();

    private void addAspectStackToItem(Item item, NbtCompound nbtCompound, AspectStack itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, nbtCompound);
        }}, itemAspects.getAspects());
    }

    private void addAspectStackToItem(Item item, AspectStack itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, new NbtCompound());
        }}, itemAspects.getAspects());
    }

    private void addAspectStackToItemForTag(TagKey<Item> tagKey, AspectStack itemAspects) {
        Registries.ITEM.getOrCreateEntryList(tagKey).forEach((tag) -> {
            this.addAspectStackToItem(tag.value(), itemAspects);
        });
    }

    private void addAspectStackToBlockItemForTag(TagKey<Block> tagKey, AspectStack itemAspects) {
        Registries.BLOCK.getOrCreateEntryList(tagKey).forEach((tag) -> {
            this.addAspectStackToItem(tag.value().asItem(), itemAspects);
        });
    }

    public static Map<Aspect, Integer> getAspectsByItem(Item item) {
        Map<Item, NbtCompound> key = new ConcurrentHashMap<>(){{
            put(item, new NbtCompound());
        }};

        return ITEMS.get(key);
    }

    public static List<NbtCompound> getNbtCompoundsByItem(Item item) {
        List<NbtCompound> nbtList = new ArrayList<>();

        for (Map.Entry<Map<Item, NbtCompound>, Map<Aspect, Integer>> entry : ITEMS.entrySet()) {

            Map<Item, NbtCompound> itemMap = entry.getKey();

            if (itemMap.containsKey(item)) {
                nbtList.add(itemMap.get(item));
            }
        }

        return nbtList;
    }

    public static Map<Aspect, Integer> getAspectsByItemStack(ItemStack stack) {
        List<NbtCompound> nbts = getNbtCompoundsByItem(stack.getItem());

        if(nbts.isEmpty()) {
            return null;
        }

        if(stack.hasNbt()) {
            return getAspectsByItem(stack.getItem());
        }

        Map<Item, NbtCompound> key = Collections.singletonMap(stack.getItem(), nbts.get(0));
        for(NbtCompound nbt : nbts) {
            if(hasAllKeys(nbt, stack.getNbt())) {
                key = Collections.singletonMap(stack.getItem(), nbt);
            }
        }
        return ITEMS.get(key);
    }

    private static boolean hasAllKeys(NbtCompound first, NbtCompound second) {
        for (String key : first.getKeys()) {
            if (second.contains(key)) {
                NbtElement valueFirst = first.get(key);

                NbtElement valueSecond = second.get(key);

                if (!valueFirst.equals(valueSecond)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static void register() {
        Thaumcraft.LOGGER.debug("Registering aspects in items for: "+Thaumcraft.MOD_NAME);

        new AspectRegistry().registryItems();
    }

    private static final AspectStack DEFAULT_LOG = new AspectStack(Aspect.HERBA, 20);
    private static final AspectStack DEFAULT_STRIPPED_LOG = new AspectStack(Aspect.HERBA, 17);
    private static final AspectStack DEFAULT_WOOD = new AspectStack(Aspect.HERBA, 22);
    private static final AspectStack DEFAULT_STRIPPED_WOOD = new AspectStack(Aspect.HERBA, 19);
    private static final AspectStack DEFAULT_PLANKS = new AspectStack(Aspect.HERBA, 3);
    private static final AspectStack DEFAULT_STAIRS = new AspectStack(Aspect.HERBA, 3);
    private static final AspectStack DEFAULT_SLAB = new AspectStack(Aspect.HERBA);
    private static final AspectStack DEFAULT_FENCE = new AspectStack(Aspect.HERBA, 3);
    private static final AspectStack DEFAULT_FENCE_GATES = new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5);
    private static final AspectStack DEFAULT_DOOR = new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5);
    private static final AspectStack DEFAULT_TRAPDOOR = new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5);
    private static final AspectStack DEFAULT_PLATE = new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5);
    private static final AspectStack DEFAULT_BUTTON = new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5);
    private static final AspectStack DEFAULT_SIGN = new AspectStack(Aspect.HERBA, 4);
    private static final AspectStack DEFAULT_HANGING_SIGN = new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 15);
    private static final AspectStack DEFAULT_BOAT = new AspectStack(Aspect.HERBA, 11).with(Aspect.AQUA, 10).with(Aspect.MOTUS, 15);
    private static final AspectStack DEFAULT_CHEST_BOAT = new AspectStack(Aspect.HERBA, 20).with(Aspect.AQUA, 10).with(Aspect.MOTUS, 15);
    private static final AspectStack DEFAULT_LEAVES = new AspectStack(Aspect.HERBA, 5);
    private static final AspectStack DEFAULT_BASE_STONE = new AspectStack(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_BASE_POLISHED_STONE = new AspectStack(Aspect.TERRA, 3);
    private static final AspectStack DEFAULT_BASE_STONE_STAIRS = new AspectStack(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_BASE_POLISHED_STONE_STAIRS = new AspectStack(Aspect.TERRA, 3);
    private static final AspectStack DEFAULT_BASE_STONE_SLAB = new AspectStack(Aspect.TERRA, 1);
    private static final AspectStack DEFAULT_BASE_POLISHED_STONE_SLAB = new AspectStack(Aspect.TERRA, 1);
    private static final AspectStack DEFAULT_BASE_STONE_WALL = new AspectStack(Aspect.TERRA, 2);
    private static final AspectStack DEFAULT_SANDSTONE = new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15);
    private static final AspectStack DEFAULT_SANDSTONE_STAIRS = new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15);
    private static final AspectStack DEFAULT_SANDSTONE_SLAB = new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5);
    private static final AspectStack DEFAULT_SANDSTONE_WALL = new AspectStack(Aspect.TERRA, 7).with(Aspect.PERDITIO, 7);
    private static final AspectStack DEFAULT_CHISELED_SANDSTONE = new AspectStack(Aspect.TERRA, 7).with(Aspect.PERDITIO, 7).with(Aspect.ORDO);
    private static final AspectStack DEFAULT_CUT_SANDSTONE = new AspectStack(Aspect.TERRA, 12).with(Aspect.PERDITIO, 12).with(Aspect.ORDO);
    private static final AspectStack DEFAULT_CUT_SANDSTONE_SLAB = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 3).with(Aspect.ORDO);
    private static final AspectStack DEFAULT_BANNER = new AspectStack(Aspect.BESTIA, 49).with(Aspect.FABRICO, 13).with(Aspect.SENSUS, 13);
    private static final AspectStack DEFAULT_CANDLE = new AspectStack(Aspect.BESTIA, 5).with(Aspect.HERBA, 3).with(Aspect.FABRICO).with(Aspect.SENSUS);
    private static final AspectStack DEFAULT_BED = new AspectStack(Aspect.BESTIA, 33).with(Aspect.FABRICO, 11).with(Aspect.HERBA, 6).with(Aspect.SENSUS);
    private static final AspectStack DEFAULT_GLASS_PANE = new AspectStack(Aspect.VITREUS);
    private static final AspectStack DEFAULT_GLASS = new AspectStack(Aspect.VITREUS, 5);
    private static final AspectStack DEFAULT_CONCRETE_POWDER = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2);
    private static final AspectStack DEFAULT_CONCRETE = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2).with(Aspect.AQUA).with(Aspect.ORDO);
    private static final AspectStack DEFAULT_GLAZED_TERRACOTE = new AspectStack(Aspect.AQUA, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS, 2).with(Aspect.SENSUS, 2);
    private static final AspectStack DEFAULT_WOOL = new AspectStack(Aspect.BESTIA, 11).with(Aspect.FABRICO, 3).with(Aspect.SENSUS, 3);
    private static final AspectStack DEFAULT_CARPET = new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO, 1).with(Aspect.SENSUS, 1);
    private static final AspectStack DEFAULT_TERRACOTA = new AspectStack(Aspect.AQUA, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS).with(Aspect.SENSUS);
    private static final AspectStack DEFAULT_SAPLING = new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 5);
    private static final AspectStack DEFAULT_FLOWER = new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5).with(Aspect.VICTUS);
    private static final AspectStack DEFAULT_SHULKER_BOX = new AspectStack(Aspect.HERBA, 13)
            .with(Aspect.BESTIA, 7)
            .with(Aspect.ALIENIS, 7)
            .with(Aspect.VACOUS, 7)
            .with(Aspect.PRAEMUNIO, 15)
            .with(Aspect.HERBA, 16);
    private static final AspectStack DEFAULT_FISH = new AspectStack(Aspect.VICTUS, 5).with(Aspect.BESTIA, 5).with(Aspect.AQUA, 5);
    private static final AspectStack DEFAULT_COAL_ORE = new AspectStack(Aspect.POTENTIA, 15).with(Aspect.IGNIS, 15).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_IRON_ORE = new AspectStack(Aspect.METALLUM, 15).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_COPPER_ORE = new AspectStack(Aspect.METALLUM, 7).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_GOLD_ORE = new AspectStack(Aspect.METALLUM, 10).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_REDSTONE_ORE = new AspectStack(Aspect.POTENTIA, 15).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_EMERALD_ORE = new AspectStack(Aspect.VITREUS, 15).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_LAPIS_ORE = new AspectStack(Aspect.SENSUS, 15).with(Aspect.TERRA, 5);
    private static final AspectStack DEFAULT_DIAMOND_ORE = new AspectStack(Aspect.DESIDERIUM, 15).with(Aspect.VITREUS, 15).with(Aspect.TERRA, 5);

    public void registryItems() {
        /////////////////////// - - - - - TAGS - - - - - //////////////////////////
        this.addAspectStackToItemForTag(ItemTags.SAPLINGS, DEFAULT_SAPLING);
        this.addAspectStackToItemForTag(ItemTags.LOGS, DEFAULT_LOG);
        this.addAspectStackToItemForTag(ItemTags.PLANKS, DEFAULT_PLANKS);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_STAIRS, DEFAULT_STAIRS);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_SLABS, DEFAULT_SLAB);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_FENCES, DEFAULT_FENCE);
        this.addAspectStackToItemForTag(ItemTags.FENCE_GATES, DEFAULT_FENCE_GATES);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_DOORS, DEFAULT_DOOR);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_TRAPDOORS, DEFAULT_TRAPDOOR);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_PRESSURE_PLATES, DEFAULT_PLATE);
        this.addAspectStackToItemForTag(ItemTags.WOODEN_BUTTONS, DEFAULT_BUTTON);
        this.addAspectStackToItemForTag(ItemTags.SIGNS, DEFAULT_SIGN);
        this.addAspectStackToItemForTag(ItemTags.HANGING_SIGNS, DEFAULT_HANGING_SIGN);
        this.addAspectStackToItemForTag(ItemTags.LEAVES, DEFAULT_LEAVES);
        this.addAspectStackToItemForTag(ItemTags.BOATS, DEFAULT_BOAT);
        this.addAspectStackToItemForTag(ItemTags.CHEST_BOATS, DEFAULT_CHEST_BOAT);
        this.addAspectStackToItemForTag(ItemTags.FLOWERS, DEFAULT_FLOWER);
        this.addAspectStackToItemForTag(ItemTags.FISHES, DEFAULT_FISH);
        this.addAspectStackToItemForTag(ItemTags.BEDS, DEFAULT_BED);
        this.addAspectStackToItemForTag(ItemTags.CANDLES, DEFAULT_CANDLE);
        this.addAspectStackToItemForTag(ItemTags.BANNERS, DEFAULT_BANNER);
        this.addAspectStackToItemForTag(ItemTags.WOOL_CARPETS, DEFAULT_CARPET);
        this.addAspectStackToItemForTag(ItemTags.WOOL, DEFAULT_WOOL);
        this.addAspectStackToItemForTag(ItemTags.TERRACOTTA, DEFAULT_TERRACOTA);

        this.addAspectStackToBlockItemForTag(BlockTags.BASE_STONE_OVERWORLD, DEFAULT_BASE_STONE);
        this.addAspectStackToBlockItemForTag(BlockTags.COAL_ORES, DEFAULT_COAL_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.IRON_ORES, DEFAULT_IRON_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.COPPER_ORES, DEFAULT_COPPER_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.GOLD_ORES, DEFAULT_GOLD_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.REDSTONE_ORES, DEFAULT_REDSTONE_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.EMERALD_ORES, DEFAULT_EMERALD_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.LAPIS_ORES, DEFAULT_LAPIS_ORE);
        this.addAspectStackToBlockItemForTag(BlockTags.DIAMOND_ORES, DEFAULT_DIAMOND_ORE);

        this.addAspectStackToItemForTag(ThaumcraftTags.Items.SHULKER_BOXES, DEFAULT_SHULKER_BOX);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.STRIPPED_LOGS, DEFAULT_STRIPPED_LOG);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.STRIPPED_WOODS, DEFAULT_STRIPPED_WOOD);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.WOODS, DEFAULT_WOOD);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_STONE_STAIRS, DEFAULT_BASE_STONE_STAIRS);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_STONE_SLAB, DEFAULT_BASE_STONE_SLAB);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_STONE_WALL, DEFAULT_BASE_STONE_WALL);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_POLISHED_STONE, DEFAULT_BASE_POLISHED_STONE);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_POLISHED_STONE_STAIRS, DEFAULT_BASE_POLISHED_STONE_STAIRS);
        this.addAspectStackToItemForTag(ThaumcraftTags.Items.BASE_POLISHED_STONE_STAIRS, DEFAULT_BASE_POLISHED_STONE_SLAB);

        /////////////////////// - - - - - ORE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.DEEPSLATE_COAL_ORE, DEFAULT_COAL_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_IRON_ORE, DEFAULT_IRON_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_COPPER_ORE, DEFAULT_COPPER_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_REDSTONE_ORE, DEFAULT_REDSTONE_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_GOLD_ORE, DEFAULT_GOLD_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_EMERALD_ORE, DEFAULT_EMERALD_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_LAPIS_ORE, DEFAULT_LAPIS_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.DEEPSLATE_DIAMOND_ORE, DEFAULT_DIAMOND_ORE.add(Aspect.TERRA, 3).with(Aspect.ORDO, 2));

        this.addAspectStackToItem(Items.RAW_IRON, new AspectStack(Aspect.METALLUM, 15).with(Aspect.PERDITIO, 3));
        this.addAspectStackToItem(Items.RAW_IRON_BLOCK, new AspectStack(Aspect.METALLUM, 90).with(Aspect.PERDITIO, 21));

        this.addAspectStackToItem(Items.RAW_COPPER, new AspectStack(Aspect.METALLUM, 7).with(Aspect.PERDITIO, 3));
        this.addAspectStackToItem(Items.RAW_COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 49).with(Aspect.PERDITIO, 21));

        this.addAspectStackToItem(Items.RAW_GOLD, new AspectStack(Aspect.METALLUM, 10).with(Aspect.DESIDERIUM, 10).with(Aspect.PERDITIO, 5));
        this.addAspectStackToItem(Items.RAW_GOLD_BLOCK, new AspectStack(Aspect.METALLUM, 66).with(Aspect.DESIDERIUM, 66).with(Aspect.PERDITIO, 35));


        this.addAspectStackToItem(Items.NETHER_GOLD_ORE, new AspectStack(Aspect.TERRA, 5).with(Aspect.METALLUM, 5).with(Aspect.DESIDERIUM, 3)
                .with(Aspect.IGNIS, 2));
        this.addAspectStackToItem(Items.NETHER_QUARTZ_ORE, new AspectStack(Aspect.VITREUS, 10).with(Aspect.TERRA, 5));
        /////////////////////// - - - - - SIGNS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CRIMSON_SIGN, DEFAULT_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        this.addAspectStackToItem(Items.WARPED_SIGN, DEFAULT_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        /////////////////////// - - - - - HANGING SIGNS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CRIMSON_SIGN, DEFAULT_HANGING_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        this.addAspectStackToItem(Items.WARPED_SIGN, DEFAULT_HANGING_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        /////////////////////// - - - - - LEAVES - - - - - //////////////////////////
        this.addAspectStackToItem(Items.NETHER_WART_BLOCK, DEFAULT_LEAVES.with(Aspect.INFERNO, 3).with(Aspect.IGNIS, 5));
        this.addAspectStackToItem(Items.WARPED_WART_BLOCK, DEFAULT_LEAVES.with(Aspect.INFERNO, 3).with(Aspect.IGNIS, 5));
        /////////////////////// - - - - - FLOWERS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.TORCHFLOWER, DEFAULT_FLOWER.with(Aspect.LUX));
        this.addAspectStackToItem(Items.WITHER_ROSE, DEFAULT_FLOWER.with(Aspect.MORTUS, 5));
        this.addAspectStackToItem(Items.SPORE_BLOSSOM, DEFAULT_FLOWER.with(Aspect.TEMPUS, 5));
        this.addAspectStackToItem(Items.PITCHER_PLANT, DEFAULT_FLOWER.with(Aspect.TEMPUS, 15));
        /////////////////////// - - - - - STONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.STONE_PRESSURE_PLATE, new AspectStack(Aspect.TERRA, 7));
        this.addAspectStackToItem(Items.STONE_BUTTON, new AspectStack(Aspect.TERRA, 3).with(Aspect.MACHINA, 5));
        /////////////////////// - - - - - COBBLESTONES ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.COBBLESTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.COBBLESTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.COBBLESTONE_SLAB, new AspectStack(Aspect.TERRA));
        this.addAspectStackToItem(Items.COBBLESTONE_WALL, new AspectStack(Aspect.TERRA, 3));
        /////////////////////// - - - - - MOSSY COBBLESTONES ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.MOSSY_COBBLESTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 3).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.MOSSY_COBBLESTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 3).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.MOSSY_COBBLESTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.MOSSY_COBBLESTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA, 2));
        /////////////////////// - - - - - SMOOTH ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.SMOOTH_STONE, new AspectStack(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.SMOOTH_STONE_SLAB, new AspectStack(Aspect.TERRA));
        /////////////////////// - - - - - STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.STONE_BRICKS, new AspectStack(Aspect.TERRA, 3));
        this.addAspectStackToItem(Items.CRACKED_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3));
        this.addAspectStackToItem(Items.STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA));
        this.addAspectStackToItem(Items.STONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 2));
        this.addAspectStackToItem(Items.CHISELED_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));
        /////////////////////// - - - - - MOSSY STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.MOSSY_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.MOSSY_STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.MOSSY_STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.MOSSY_STONE_BRICK_WALL, new AspectStack(Aspect.TERRA).with(Aspect.HERBA, 2));
        /////////////////////// - - - - - DEEPSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.DEEPSLATE, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2));

        this.addAspectStackToItem(Items.COBBLED_DEEPSLATE, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.COBBLED_DEEPSLATE_STAIRS, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.COBBLED_DEEPSLATE_SLAB, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.COBBLED_DEEPSLATE_WALL, new AspectStack(Aspect.TERRA, 4).with(Aspect.ORDO));

        this.addAspectStackToItem(Items.CHISELED_DEEPSLATE, new AspectStack(Aspect.TERRA, 4).with(Aspect.ORDO, 4));

        this.addAspectStackToItem(Items.POLISHED_DEEPSLATE, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.POLISHED_DEEPSLATE_STAIRS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.POLISHED_DEEPSLATE_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.POLISHED_DEEPSLATE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));

        this.addAspectStackToItem(Items.DEEPSLATE_BRICKS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.CRACKED_DEEPSLATE_BRICKS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.DEEPSLATE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.DEEPSLATE_BRICK_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.DEEPSLATE_BRICK_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));

        this.addAspectStackToItem(Items.DEEPSLATE_TILES, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.CRACKED_DEEPSLATE_TILES, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.DEEPSLATE_TILE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.DEEPSLATE_TILE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.DEEPSLATE_TILE_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));

        this.addAspectStackToItem(Items.REINFORCED_DEEPSLATE, new AspectStack(Aspect.TERRA, 20).with(Aspect.TEMPUS, 5).with(Aspect.AURAM, 20)
                .with(Aspect.TENEBRAE, 20).with(Aspect.ALIENIS, 20));
        /////////////////////// - - - - - BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.BRICK, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15).with(Aspect.IGNIS, 3));
        this.addAspectStackToItem(Items.BRICK_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15).with(Aspect.IGNIS, 3));
        this.addAspectStackToItem(Items.BRICK_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.BRICK_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.AQUA, 7).with(Aspect.IGNIS, 2));
        /////////////////////// - - - - - MUD ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.MUD, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addAspectStackToItem(Items.PACKED_MUD, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5)
                .with(Aspect.HERBA, 3).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.MUD_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addAspectStackToItem(Items.MUD_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addAspectStackToItem(Items.MUD_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.AQUA));
        this.addAspectStackToItem(Items.MUD_BRICK_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.AQUA, 2));
        /////////////////////// - - - - - SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.SANDSTONE, DEFAULT_SANDSTONE);
        this.addAspectStackToItem(Items.SANDSTONE_STAIRS, DEFAULT_SANDSTONE_STAIRS);
        this.addAspectStackToItem(Items.SANDSTONE_SLAB, DEFAULT_SANDSTONE_SLAB);
        this.addAspectStackToItem(Items.SANDSTONE_WALL, DEFAULT_SANDSTONE_WALL);
        this.addAspectStackToItem(Items.CHISELED_SANDSTONE, DEFAULT_CHISELED_SANDSTONE);
        /////////////////////// - - - - - SMOOTH SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.SMOOTH_SANDSTONE, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.SMOOTH_SANDSTONE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.SMOOTH_SANDSTONE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5).with(Aspect.ORDO));
        /////////////////////// - - - - - CUT SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CUT_SANDSTONE, DEFAULT_CUT_SANDSTONE);
        this.addAspectStackToItem(Items.CUT_SANDSTONE_SLAB, DEFAULT_CUT_SANDSTONE_SLAB);
        /////////////////////// - - - - - RED SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.RED_SANDSTONE, DEFAULT_SANDSTONE);
        this.addAspectStackToItem(Items.RED_SANDSTONE_STAIRS, DEFAULT_SANDSTONE_STAIRS);
        this.addAspectStackToItem(Items.RED_SANDSTONE_SLAB, DEFAULT_SANDSTONE_SLAB);
        this.addAspectStackToItem(Items.RED_SANDSTONE_WALL, DEFAULT_SANDSTONE_WALL);
        this.addAspectStackToItem(Items.CHISELED_RED_SANDSTONE, DEFAULT_CHISELED_SANDSTONE);
        /////////////////////// - - - - - RED SMOOTH SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.SMOOTH_RED_SANDSTONE, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.SMOOTH_RED_SANDSTONE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.SMOOTH_RED_SANDSTONE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5).with(Aspect.ORDO));
        /////////////////////// - - - - - REDCUT SANDSTONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CUT_RED_SANDSTONE, DEFAULT_CUT_SANDSTONE);
        this.addAspectStackToItem(Items.CUT_RED_SANDSTONE_SLAB, DEFAULT_CUT_SANDSTONE_SLAB);
        /////////////////////// - - - - - PRISMARINE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.PRISMARINE, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addAspectStackToItem(Items.PRISMARINE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addAspectStackToItem(Items.PRISMARINE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addAspectStackToItem(Items.PRISMARINE_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.AQUA, 7));
        /////////////////////// - - - - - PRISMARINE BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.PRISMARINE_BRICKS, new AspectStack(Aspect.TERRA, 33).with(Aspect.AQUA, 33));
        this.addAspectStackToItem(Items.PRISMARINE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 33).with(Aspect.AQUA, 33));
        this.addAspectStackToItem(Items.PRISMARINE_BRICK_SLAB, new AspectStack(Aspect.TERRA, 16).with(Aspect.AQUA, 16));
        /////////////////////// - - - - - DARK BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.DARK_PRISMARINE, new AspectStack(Aspect.TERRA, 31).with(Aspect.AQUA, 31).with(Aspect.SENSUS, 3).with(Aspect.BESTIA));
        this.addAspectStackToItem(Items.DARK_PRISMARINE_STAIRS, new AspectStack(Aspect.TERRA, 31).with(Aspect.AQUA, 31).with(Aspect.SENSUS, 3).with(Aspect.BESTIA));
        this.addAspectStackToItem(Items.DARK_PRISMARINE_SLAB, new AspectStack(Aspect.TERRA, 6).with(Aspect.AQUA, 6).with(Aspect.SENSUS).with(Aspect.BESTIA));
        /////////////////////// - - - - - NETHER BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.NETHER_BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 9).with(Aspect.ORDO, 3));
        this.addAspectStackToItem(Items.CRACKED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 10).with(Aspect.ORDO, 3));
        this.addAspectStackToItem(Items.NETHER_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 10).with(Aspect.ORDO, 3));
        this.addAspectStackToItem(Items.NETHER_BRICK_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.NETHER_BRICK_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 7).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.NETHER_BRICK_FENCE, new AspectStack(Aspect.TERRA, 11).with(Aspect.IGNIS, 6).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.CHISELED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 11).with(Aspect.IGNIS, 5).with(Aspect.ORDO, 3));
        /////////////////////// - - - - - RED NETHER BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.RED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 4).with(Aspect.ALKIMIA, 4)
                .with(Aspect.VITIUM, 3).with(Aspect.ORDO).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.RED_NETHER_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 4).with(Aspect.ALKIMIA, 4)
                .with(Aspect.VITIUM, 3).with(Aspect.ORDO).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.RED_NETHER_BRICK_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.ALKIMIA).with(Aspect.VITIUM)
                .with(Aspect.ORDO).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.RED_NETHER_BRICK_WALL, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO));
        /////////////////////// - - - - - BASALT ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.BASALT, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.SMOOTH_BASALT, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.POLISHED_BASALT, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.ORDO));
        /////////////////////// - - - - - BLACK STONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.BLACKSTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.GILDED_BLACKSTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS)
                .with(Aspect.METALLUM, 5).with(Aspect.DESIDERIUM, 5));
        this.addAspectStackToItem(Items.BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.BLACKSTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.BLACKSTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS, 2).with(Aspect.VACOUS).with(Aspect.ORDO));
        /////////////////////// - - - - - POLISHED BLACK STONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CHISELED_POLISHED_BLACKSTONE, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_PRESSURE_PLATE, new AspectStack(Aspect.TERRA, 2).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_BUTTON, new AspectStack(Aspect.TERRA, 2).with(Aspect.MACHINA, 5).with(Aspect.IGNIS).with(Aspect.VACOUS));
        /////////////////////// - - - - - POLISHED BLACK STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_BRICKS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.POLISHED_BLACKSTONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addAspectStackToItem(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS, 3).with(Aspect.VACOUS));
        /////////////////////// - - - - - END STONE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.END_STONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.TENEBRAE, 5));
        this.addAspectStackToItem(Items.END_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        this.addAspectStackToItem(Items.END_STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        this.addAspectStackToItem(Items.END_STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.TENEBRAE));
        this.addAspectStackToItem(Items.END_STONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        /////////////////////// - - - - - PURPUR ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.PURPUR_BLOCK, new AspectStack(Aspect.ALIENIS, 3).with(Aspect.SENSUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.PURPUR_STAIRS, new AspectStack(Aspect.ALIENIS, 3).with(Aspect.SENSUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.PURPUR_SLAB, new AspectStack(Aspect.ALIENIS).with(Aspect.SENSUS).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.PURPUR_PILLAR, new AspectStack(Aspect.ALIENIS).with(Aspect.SENSUS).with(Aspect.HERBA));
        /////////////////////// - - - - - IRON ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.IRON_INGOT, new AspectStack(Aspect.METALLUM, 15));
        this.addAspectStackToItem(Items.IRON_NUGGET, new AspectStack(Aspect.METALLUM));
        this.addAspectStackToItem(Items.CHAIN, new AspectStack(Aspect.METALLUM, 16));
        this.addAspectStackToItem(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, new AspectStack(Aspect.METALLUM, 22).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.IRON_TRAPDOOR, new AspectStack(Aspect.METALLUM, 45));
        this.addAspectStackToItem(Items.IRON_DOOR, new AspectStack(Aspect.METALLUM, 22).with(Aspect.VINCULUM, 5).with(Aspect.MACHINA, 5));
        this.addAspectStackToItem(Items.IRON_BARS, new AspectStack(Aspect.METALLUM, 4));
        this.addAspectStackToItem(Items.IRON_BLOCK, new AspectStack(Aspect.METALLUM, 101));
        this.addAspectStackToItem(Items.IRON_HORSE_ARMOR, new AspectStack(Aspect.METALLUM, 15));
        /////////////////////// - - - - - NETHERITE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.ANCIENT_DEBRIS, new AspectStack(Aspect.METALLUM, 25).with(Aspect.TEMPUS, 15).with(Aspect.IGNIS, 33));
        this.addAspectStackToItem(Items.NETHERITE_SCRAP, new AspectStack(Aspect.METALLUM, 15).with(Aspect.TEMPUS, 10).with(Aspect.IGNIS, 25));
        this.addAspectStackToItem(Items.NETHERITE_INGOT, new AspectStack(Aspect.METALLUM, 75).with(Aspect.TEMPUS, 40).with(Aspect.IGNIS, 50)
                .with(Aspect.DESIDERIUM, 30));
        this.addAspectStackToItem(Items.NETHERITE_BLOCK, new AspectStack(Aspect.METALLUM, 525).with(Aspect.TEMPUS, 280).with(Aspect.IGNIS, 350)
                .with(Aspect.DESIDERIUM, 210));
        /////////////////////// - - - - - QUARTZ ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.QUARTZ_BLOCK, new AspectStack(Aspect.VITREUS, 15));
        this.addAspectStackToItem(Items.QUARTZ_STAIRS, new AspectStack(Aspect.VITREUS, 15));
        this.addAspectStackToItem(Items.QUARTZ_SLAB, new AspectStack(Aspect.VITREUS, 3));
        this.addAspectStackToItem(Items.CHISELED_QUARTZ_BLOCK, new AspectStack(Aspect.VITREUS, 7));
        this.addAspectStackToItem(Items.QUARTZ_BRICKS, new AspectStack(Aspect.VITREUS, 10));
        this.addAspectStackToItem(Items.QUARTZ_PILLAR, new AspectStack(Aspect.VITREUS, 11));
        this.addAspectStackToItem(Items.SMOOTH_QUARTZ, new AspectStack(Aspect.VITREUS, 9).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.SMOOTH_QUARTZ_STAIRS, new AspectStack(Aspect.VITREUS, 9).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.SMOOTH_QUARTZ_SLAB, new AspectStack(Aspect.VITREUS, 3).with(Aspect.IGNIS));
        /////////////////////// - - - - - COPPER ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 50));
        this.addAspectStackToItem(Items.CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.EXPOSED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS));
        this.addAspectStackToItem(Items.EXPOSED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addAspectStackToItem(Items.EXPOSED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addAspectStackToItem(Items.EXPOSED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addAspectStackToItem(Items.WEATHERED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 3));
        this.addAspectStackToItem(Items.WEATHERED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addAspectStackToItem(Items.WEATHERED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addAspectStackToItem(Items.WEATHERED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addAspectStackToItem(Items.OXIDIZED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 5));
        this.addAspectStackToItem(Items.OXIDIZED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        this.addAspectStackToItem(Items.OXIDIZED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        this.addAspectStackToItem(Items.OXIDIZED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        /////////////////////// - - - - - WAXED COPPER ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WAXED_COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 50).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_EXPOSED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_EXPOSED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_WEATHERED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_WEATHERED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_WEATHERED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_OXIDIZED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_OXIDIZED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        /////////////////////// - - - - - BAMBOO ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.BAMBOO, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.BAMBOO_BLOCK, new AspectStack(Aspect.HERBA, 7));
        this.addAspectStackToItem(Items.STRIPPED_BAMBOO_BLOCK, new AspectStack(Aspect.HERBA, 6));
        this.addAspectStackToItem(Items.BAMBOO_PLANKS, new AspectStack(Aspect.HERBA, 2));
        this.addAspectStackToItem(Items.BAMBOO_MOSAIC, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.BAMBOO_STAIRS, new AspectStack(Aspect.HERBA, 7));
        this.addAspectStackToItem(Items.BAMBOO_MOSAIC_STAIRS, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.BAMBOO_SLAB, new AspectStack(Aspect.HERBA, 2));
        this.addAspectStackToItem(Items.BAMBOO_MOSAIC_SLAB, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.BAMBOO_FENCE, new AspectStack(Aspect.HERBA, 4));
        this.addAspectStackToItem(Items.BAMBOO_FENCE_GATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addAspectStackToItem(Items.BAMBOO_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addAspectStackToItem(Items.BAMBOO_TRAPDOOR, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addAspectStackToItem(Items.BAMBOO_PRESSURE_PLATE, new AspectStack(Aspect.HERBA).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.BAMBOO_BUTTON, new AspectStack(Aspect.HERBA).with(Aspect.MACHINA, 5));
        /////////////////////// - - - - - CRIMSON ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CRIMSON_STEM, new AspectStack(Aspect.HERBA, 20).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.CRIMSON_HYPHAE, new AspectStack(Aspect.HERBA, 22).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.STRIPPED_CRIMSON_STEM, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.STRIPPED_CRIMSON_HYPHAE, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.CRIMSON_PLANKS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.CRIMSON_STAIRS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.CRIMSON_SLAB, new AspectStack(Aspect.HERBA).with(Aspect.INFERNO));
        this.addAspectStackToItem(Items.CRIMSON_FENCE, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 3));
        this.addAspectStackToItem(Items.CRIMSON_FENCE_GATE, new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.CRIMSON_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 9));
        this.addAspectStackToItem(Items.CRIMSON_TRAPDOOR, new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5).with(Aspect.INFERNO, 7));
        this.addAspectStackToItem(Items.CRIMSON_PRESSURE_PLATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.CRIMSON_BUTTON, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.INFERNO));
        /////////////////////// - - - - - WARPED ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WARPED_STEM, new AspectStack(Aspect.HERBA, 20).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.WARPED_HYPHAE, new AspectStack(Aspect.HERBA, 22).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.STRIPPED_WARPED_STEM, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.STRIPPED_WARPED_HYPHAE, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.WARPED_PLANKS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.WARPED_STAIRS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 5));
        this.addAspectStackToItem(Items.WARPED_SLAB, new AspectStack(Aspect.HERBA).with(Aspect.INFERNO));
        this.addAspectStackToItem(Items.WARPED_FENCE, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 3));
        this.addAspectStackToItem(Items.WARPED_FENCE_GATE, new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.WARPED_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 9));
        this.addAspectStackToItem(Items.WARPED_TRAPDOOR, new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5).with(Aspect.INFERNO, 7));
        this.addAspectStackToItem(Items.WARPED_PRESSURE_PLATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.WARPED_BUTTON, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.INFERNO));
        /////////////////////// - - - - - WOOL ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_WOOL, DEFAULT_WOOL.without(Aspect.SENSUS));
        /////////////////////// - - - - - CARPET ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_CARPET, DEFAULT_CARPET.without(Aspect.SENSUS));
        /////////////////////// - - - - - TERRACOTTA ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.TERRACOTTA, DEFAULT_TERRACOTA.without(Aspect.SENSUS));
        /////////////////////// - - - - - GLAZED TERRACOTA ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.GRAY_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.BLACK_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.BROWN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.RED_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.ORANGE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.YELLOW_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.LIME_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.GREEN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.CYAN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.LIGHT_BLUE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.BLUE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.PURPLE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.MAGENTA_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addAspectStackToItem(Items.PINK_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        /////////////////////// - - - - - CONCRETE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.LIGHT_GRAY_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.GRAY_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.BLACK_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.BROWN_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.RED_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.ORANGE_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.YELLOW_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.LIME_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.GREEN_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.CYAN_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.LIGHT_BLUE_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.BLUE_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.PURPLE_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.MAGENTA_CONCRETE, DEFAULT_CONCRETE);
        this.addAspectStackToItem(Items.PINK_CONCRETE, DEFAULT_CONCRETE);
        /////////////////////// - - - - - POWDER CONCRETE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.LIGHT_GRAY_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.GRAY_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.BLACK_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.BROWN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.RED_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.ORANGE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.YELLOW_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.LIME_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.GREEN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.CYAN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.LIGHT_BLUE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.BLUE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.PURPLE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.MAGENTA_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addAspectStackToItem(Items.PINK_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        /////////////////////// - - - - - GLASS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.TINTED_GLASS, DEFAULT_GLASS.with(Aspect.TENEBRAE, 2));
        this.addAspectStackToItem(Items.WHITE_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.LIGHT_GRAY_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.GRAY_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.BLACK_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.BROWN_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.RED_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.ORANGE_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.YELLOW_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.LIME_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.GREEN_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.CYAN_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.LIGHT_BLUE_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.BLUE_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.PURPLE_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.MAGENTA_STAINED_GLASS, DEFAULT_GLASS);
        this.addAspectStackToItem(Items.PINK_STAINED_GLASS, DEFAULT_GLASS);
        /////////////////////// - - - - - GLASS PANE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.WHITE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.LIGHT_GRAY_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.GRAY_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.BLACK_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.BROWN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.RED_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.ORANGE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.YELLOW_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.LIME_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.GREEN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.CYAN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.LIGHT_BLUE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.BLUE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.PURPLE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.MAGENTA_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addAspectStackToItem(Items.PINK_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        /////////////////////// - - - - - BED ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_BED, DEFAULT_BED.without(Aspect.SENSUS));
        /////////////////////// - - - - - CANDLE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.CANDLE, DEFAULT_CANDLE.without(Aspect.SENSUS));
        /////////////////////// - - - - - BANNER ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.WHITE_BANNER, DEFAULT_BANNER.without(Aspect.SENSUS));
        /////////////////////// - - - - - AMETHYST ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.AMETHYST_BLOCK, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO));
        this.addAspectStackToItem(Items.BUDDING_AMETHYST, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.SMALL_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 3).with(Aspect.PRAECANTATIO).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.MEDIUM_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 5).with(Aspect.PRAECANTATIO, 2).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.LARGE_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO, 3).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.AMETHYST_CLUSTER, new AspectStack(Aspect.VITREUS, 20).with(Aspect.PRAECANTATIO, 5).with(Aspect.VICTUS, 2));
        /////////////////////// - - - - - SEEDS ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.PUMPKIN_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.MELON_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.TORCHFLOWER_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.PITCHER_POD, new AspectStack(Aspect.HERBA, 4).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.WHEAT_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.BEETROOT_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        /////////////////////// - - - - - CORAL ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.TUBE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.BRAIN_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.BUBBLE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.FIRE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.HORN_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));

        this.addAspectStackToItem(Items.DEAD_TUBE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.DEAD_BRAIN_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.DEAD_BUBBLE_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.DEAD_FIRE_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.DEAD_HORN_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));

        this.addAspectStackToItem(Items.TUBE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.BRAIN_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.BUBBLE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.FIRE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.HORN_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));

        this.addAspectStackToItem(Items.DEAD_TUBE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_BRAIN_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_BUBBLE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_FIRE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_HORN_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));

        this.addAspectStackToItem(Items.TUBE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.BRAIN_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.BUBBLE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.FIRE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.HORN_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));

        this.addAspectStackToItem(Items.DEAD_TUBE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_BRAIN_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_BUBBLE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_FIRE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.DEAD_HORN_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        /////////////////////// - - - - - BEE ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.BEE_NEST, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 15));
        this.addAspectStackToItem(Items.HONEYCOMB, new AspectStack(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.HONEYCOMB_BLOCK, new AspectStack(Aspect.HERBA, 10));
        this.addAspectStackToItem(Items.HONEY_BOTTLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.VICTUS, 3).with(Aspect.AQUA, 2).with(Aspect.DESIDERIUM));
        this.addAspectStackToItem(Items.HONEY_BLOCK, new AspectStack(Aspect.HERBA, 30).with(Aspect.VICTUS, 9).with(Aspect.AQUA, 6).with(Aspect.DESIDERIUM, 3));
        /////////////////////// - - - - - SKULK ITEMS - - - - - //////////////////////////
        this.addAspectStackToItem(Items.SCULK, new AspectStack(Aspect.ECHO, 5).with(Aspect.TENEBRAE, 5));
        this.addAspectStackToItem(Items.SCULK_VEIN, new AspectStack(Aspect.ECHO).with(Aspect.TENEBRAE));
        this.addAspectStackToItem(Items.SCULK_CATALYST, new AspectStack(Aspect.ECHO, 15).with(Aspect.TENEBRAE, 15).with(Aspect.VITIUM, 10));
        this.addAspectStackToItem(Items.SCULK_SHRIEKER, new AspectStack(Aspect.ECHO, 25).with(Aspect.TENEBRAE, 17).with(Aspect.VITIUM, 12));
        this.addAspectStackToItem(Items.SCULK_SENSOR, new AspectStack(Aspect.ECHO, 17).with(Aspect.TENEBRAE, 10).with(Aspect.VITIUM, 3));






        this.addAspectStackToItem(Items.GLOW_ITEM_FRAME, new AspectStack(Aspect.HERBA, 6).with(Aspect.BESTIA, 3).with(Aspect.PRAEMUNIO, 3).with(Aspect.SENSUS, 3).with(Aspect.PRAECANTATIO).with(Aspect.AQUA).with(Aspect.BESTIA));
        this.addAspectStackToItem(Items.CHISELED_BOOKSHELF, new AspectStack(Aspect.HERBA, 18));
        this.addAspectStackToItem(Items.GLOW_INK_SAC, new AspectStack(Aspect.SENSUS, 3).with(Aspect.PRAECANTATIO, 2).with(Aspect.AQUA).with(Aspect.BESTIA));
        this.addAspectStackToItem(Items.ENDER_EYE, new AspectStack(Aspect.MOTUS, 11).with(Aspect.SENSUS, 10).with(Aspect.ALIENIS, 7).with(Aspect.PRAECANTATIO, 5).with(Aspect.IGNIS, 3));
        this.addAspectStackToItem(Items.ENDER_CHEST, new AspectStack(Aspect.IGNIS, 33).with(Aspect.TERRA, 30).with(Aspect.TENEBRAE, 30).with(Aspect.VACOUS, 20).with(Aspect.MOTUS, 20)
                .with(Aspect.PERMUTATIO, 10).with(Aspect.SENSUS, 7));
        this.addAspectStackToItem(Items.BOOKSHELF, new AspectStack(Aspect.HERBA, 27).with(Aspect.COGNITIO, 17).with(Aspect.AQUA, 9).with(Aspect.BESTIA, 6).with(Aspect.PRAEMUNIO, 6)
                .with(Aspect.AER, 4));
        this.addAspectStackToItem(Items.PAINTING, new AspectStack(Aspect.BESTIA, 11).with(Aspect.HERBA, 6).with(Aspect.FABRICO, 3));
        this.addAspectStackToItem(Items.ITEM_FRAME, new AspectStack(Aspect.HERBA, 6).with(Aspect.BESTIA, 3).with(Aspect.PRAEMUNIO, 3));
        this.addAspectStackToItem(Items.ARMOR_STAND, new AspectStack(Aspect.HERBA, 4));
        this.addAspectStackToItem(Items.DECORATED_POT, new AspectStack(Aspect.AQUA, 13).with(Aspect.TERRA, 13).with(Aspect.IGNIS, 3).with(Aspect.VACOUS, 7).with(Aspect.TEMPUS));
        this.addAspectStackToItem(Items.FLOWER_POT, new AspectStack(Aspect.AQUA, 11).with(Aspect.TERRA, 11).with(Aspect.HERBA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS, 2));
        this.addAspectStackToItem(Items.LIGHTNING_ROD, new AspectStack(Aspect.METALLUM, 18).with(Aspect.SENSUS, 2));
        this.addAspectStackToItem(Items.BEEHIVE, new AspectStack(Aspect.HERBA, 23));
        this.addAspectStackToItem(Items.SCAFFOLDING, new AspectStack(Aspect.BESTIA, 3).with(Aspect.HERBA).with(Aspect.FABRICO));
        this.addAspectStackToItem(Items.LADDER, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.LODESTONE, new AspectStack(Aspect.METALLUM, 60).with(Aspect.IGNIS, 40).with(Aspect.TEMPUS, 30).with(Aspect.DESIDERIUM, 20)
                .with(Aspect.TERRA, 18).with(Aspect.ORDO, 6));
        this.addAspectStackToItem(Items.CONDUIT, new AspectStack(Aspect.TEMPUS, 120).with(Aspect.AQUA, 80).with(Aspect.PRAECANTATIO, 15));
        this.addAspectStackToItem(Items.HEART_OF_THE_SEA, new AspectStack(Aspect.TEMPUS, 15).with(Aspect.AQUA, 30).with(Aspect.PRAECANTATIO, 20));
        this.addAspectStackToItem(Items.BEACON, new AspectStack(Aspect.PRAECANTATIO, 25).with(Aspect.VITREUS, 18).with(Aspect.AURAM, 17).with(Aspect.ORDO, 15).with(Aspect.IGNIS, 11)
                .with(Aspect.TENEBRAE, 11).with(Aspect.PERMUTATIO, 10));
        this.addAspectStackToItem(Items.NAUTILUS_SHELL, new AspectStack(Aspect.TEMPUS, 25).with(Aspect.AQUA, 20));
        this.addAspectStackToItem(Items.BELL, new AspectStack(Aspect.METALLUM, 50).with(Aspect.DESIDERIUM, 50).with(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.COPPER_INGOT, new AspectStack(Aspect.METALLUM, 7));
        this.addAspectStackToItem(Items.CAULDRON, new AspectStack(Aspect.METALLUM, 60));
        this.addAspectStackToItem(Items.BREWING_STAND, new AspectStack(Aspect.ALKIMIA, 25).with(Aspect.FABRICO, 15).with(Aspect.IGNIS, 11)
                .with(Aspect.TERRA, 11).with(Aspect.MOTUS, 3).with(Aspect.PERDITIO, 2));
        this.addAspectStackToItem(Items.END_CRYSTAL, new AspectStack(Aspect.VITREUS, 26).with(Aspect.ALKIMIA, 9).with(Aspect.MOTUS, 8)
                .with(Aspect.SENSUS, 7).with(Aspect.SPIRITUS, 7).with(Aspect.ALIENIS, 5).with(Aspect.EXANIMIS, 3));
        this.addAspectStackToItem(Items.ENCHANTING_TABLE, new AspectStack(Aspect.PRAECANTATIO, 25).with(Aspect.VITREUS, 22).with(Aspect.DESIDERIUM, 22)
                .with(Aspect.FABRICO, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS, 15).with(Aspect.TENEBRAE, 15));
        this.addAspectStackToItem(Items.LEATHER, new AspectStack(Aspect.BESTIA, 5).with(Aspect.PRAEMUNIO, 5));
        this.addAspectStackToItem(Items.BOOK, new AspectStack(Aspect.HERBA, 6).with(Aspect.AQUA, 4).with(Aspect.COGNITIO, 4).with(Aspect.BESTIA, 3)
                .with(Aspect.AER, 2).with(Aspect.PRAEMUNIO, 3));
        this.addAspectStackToItem(Items.DIAMOND, new AspectStack(Aspect.VITREUS, 15).with(Aspect.DESIDERIUM, 15));
        this.addAspectStackToItem(Items.JUKEBOX, new AspectStack(Aspect.SENSUS, 18).with(Aspect.HERBA, 18).with(Aspect.AER, 15)
                .with(Aspect.VITREUS, 11).with(Aspect.DESIDERIUM, 11).with(Aspect.MACHINA, 10));
        this.addAspectStackToItem(Items.NOTE_BLOCK, new AspectStack(Aspect.SENSUS, 18).with(Aspect.HERBA, 18).with(Aspect.AER, 15)
                .with(Aspect.MACHINA, 10).with(Aspect.POTENTIA, 7));
        this.addAspectStackToItem(Items.COMPOSTER, new AspectStack(Aspect.HERBA, 5));
        this.addAspectStackToItem(Items.ANVIL, new AspectStack(Aspect.METALLUM, 272));
        this.addAspectStackToItem(Items.CHIPPED_ANVIL, new AspectStack(Aspect.METALLUM, 150).with(Aspect.PERDITIO, 10));
        this.addAspectStackToItem(Items.DAMAGED_ANVIL, new AspectStack(Aspect.METALLUM, 75).with(Aspect.PERDITIO, 30));
        this.addAspectStackToItem(Items.CHARCOAL, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.IGNIS, 12));
        this.addAspectStackToItem(Items.COAL, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.IGNIS, 10));
        this.addAspectStackToItem(Items.SOUL_CAMPFIRE, new AspectStack(Aspect.HERBA, 35).with(Aspect.POTENTIA, 8).with(Aspect.IGNIS, 7)
                .with(Aspect.SPIRITUS, 4));
        this.addAspectStackToItem(Items.CAMPFIRE, new AspectStack(Aspect.HERBA, 35).with(Aspect.POTENTIA, 8).with(Aspect.IGNIS, 7));
        this.addAspectStackToItem(Items.BLAST_FURNACE, new AspectStack(Aspect.METALLUM, 55).with(Aspect.TERRA, 40).with(Aspect.IGNIS, 10)
                .with(Aspect.PERDITIO, 6));
        this.addAspectStackToItem(Items.SMOKER, new AspectStack(Aspect.TERRA, 30).with(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 6)
                .with(Aspect.HERBA, 50).with(Aspect.SENSUS, 2));
        this.addAspectStackToItem(Items.FURNACE, new AspectStack(Aspect.TERRA, 30).with(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 6));
        this.addAspectStackToItem(Items.LOOM, new AspectStack(Aspect.HERBA, 4).with(Aspect.FABRICO).with(Aspect.BESTIA, 4));
        this.addAspectStackToItem(Items.STICK, new AspectStack(Aspect.HERBA));
        this.addAspectStackToItem(Items.GRINDSTONE, new AspectStack(Aspect.TERRA).with(Aspect.HERBA, 6).with(Aspect.SENSUS, 3));
        this.addAspectStackToItem(Items.SMITHING_TABLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addAspectStackToItem(Items.FLETCHING_TABLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 15).with(Aspect.INSTRUMENTUM, 12)
                .with(Aspect.TERRA, 5).with(Aspect.IGNIS, 15).with(Aspect.SENSUS, 3));
        this.addAspectStackToItem(Items.CARTOGRAPHY_TABLE, new AspectStack(Aspect.HERBA, 14).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addAspectStackToItem(Items.STONECUTTER, new AspectStack(Aspect.TERRA, 10).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addAspectStackToItem(Items.CRAFTING_TABLE, new AspectStack(Aspect.FABRICO, 20).with(Aspect.HERBA, 9));
        this.addAspectStackToItem(Items.PAPER, new AspectStack(Aspect.HERBA, 3).with(Aspect.COGNITIO, 2).with(Aspect.AQUA, 2).with(Aspect.AER));
        this.addAspectStackToItem(Items.SHROOMLIGHT, new AspectStack(Aspect.LUX, 30).with(Aspect.HERBA, 7).with(Aspect.IGNIS, 5).with(Aspect.INFERNO, 3));
        this.addAspectStackToItem(Items.GLOWSTONE, new AspectStack(Aspect.LUX, 30).with(Aspect.SENSUS, 15));
        this.addAspectStackToItem(Items.REDSTONE_LAMP, new AspectStack(Aspect.POTENTIA, 20).with(Aspect.LUX, 22).with(Aspect.SENSUS, 11));
        this.addAspectStackToItem(Items.END_ROD, new AspectStack(Aspect.LUX, 5).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.SOUL_LANTERN, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.SPIRITUS).with(Aspect.METALLUM));
        this.addAspectStackToItem(Items.LANTERN, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.METALLUM));
        this.addAspectStackToItem(Items.REDSTONE_TORCH, new AspectStack(Aspect.POTENTIA, 7));
        this.addAspectStackToItem(Items.SOUL_TORCH, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.SPIRITUS));
        this.addAspectStackToItem(Items.TORCH, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.OCHRE_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addAspectStackToItem(Items.VERDANT_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addAspectStackToItem(Items.PEARLESCENT_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addAspectStackToItem(Items.SLIME_BLOCK, new AspectStack(Aspect.AQUA, 33).with(Aspect.VICTUS, 33).with(Aspect.ALKIMIA, 6));
        this.addAspectStackToItem(Items.HAY_BLOCK, new AspectStack(Aspect.HERBA, 33).with(Aspect.VICTUS, 33));
        this.addAspectStackToItem(Items.JACK_O_LANTERN, new AspectStack(Aspect.HERBA, 20).with(Aspect.LUX, 2));
        this.addAspectStackToItem(Items.CARVED_PUMPKIN, new AspectStack(Aspect.HERBA, 20));
        this.addAspectStackToItem(Items.PUMPKIN, new AspectStack(Aspect.HERBA, 10));
        this.addAspectStackToItem(Items.MELON, new AspectStack(Aspect.HERBA, 10));
        this.addAspectStackToItem(Items.SPONGE, new AspectStack(Aspect.TERRA, 5).with(Aspect.VINCULUM, 5).with(Aspect.VACOUS, 5));
        this.addAspectStackToItem(Items.WET_SPONGE, new AspectStack(Aspect.TERRA, 5).with(Aspect.VINCULUM, 5).with(Aspect.AQUA, 5));
        this.addAspectStackToItem(Items.DRIED_KELP_BLOCK, new AspectStack(Aspect.HERBA, 8).with(Aspect.IGNIS, 7));
        this.addAspectStackToItem(Items.DRIED_KELP, new AspectStack(Aspect.HERBA).with(Aspect.IGNIS));
        this.addAspectStackToItem(Items.KELP, new AspectStack(Aspect.HERBA).with(Aspect.AQUA));
        this.addAspectStackToItem(Items.SEA_PICKLE, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.LUX, 2));
        this.addAspectStackToItem(Items.SEAGRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA));
        this.addAspectStackToItem(Items.SWEET_BERRIES, new AspectStack(Aspect.HERBA, 7).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.GLOW_BERRIES, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS, 3).with(Aspect.LUX, 2));
        this.addAspectStackToItem(Items.SNIFFER_EGG, new AspectStack(Aspect.VICTUS, 15).with(Aspect.TEMPUS, 10).with(Aspect.HUMANUS, 3));
        this.addAspectStackToItem(Items.TURTLE_EGG, new AspectStack(Aspect.VICTUS, 5).with(Aspect.HUMANUS));
        this.addAspectStackToItem(Items.FROGSPAWN, new AspectStack(Aspect.VICTUS, 5).with(Aspect.HUMANUS));
        this.addAspectStackToItem(Items.HANGING_ROOTS, new AspectStack(Aspect.HERBA, 3));
        this.addAspectStackToItem(Items.GLOW_LICHEN, new AspectStack(Aspect.HERBA, 5).with(Aspect.LUX));
        this.addAspectStackToItem(Items.SMALL_DRIPLEAF, new AspectStack(Aspect.HERBA, 2));
        this.addAspectStackToItem(Items.BIG_DRIPLEAF, new AspectStack(Aspect.HERBA, 5));
        this.addAspectStackToItem(Items.TWISTING_VINES, new AspectStack(Aspect.HERBA, 5).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.WEEPING_VINES, new AspectStack(Aspect.HERBA, 5).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.CRIMSON_ROOTS, new AspectStack(Aspect.HERBA, 3).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.WARPED_ROOTS, new AspectStack(Aspect.HERBA, 3).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addAspectStackToItem(Items.DEAD_BUSH, new AspectStack(Aspect.HERBA, 5).with(Aspect.PERDITIO));
        this.addAspectStackToItem(Items.FERN, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addAspectStackToItem(Items.SHORT_GRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addAspectStackToItem(Items.CRIMSON_FUNGUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2).with(Aspect.IGNIS, 5));
        this.addAspectStackToItem(Items.WARPED_FUNGUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2).with(Aspect.IGNIS, 5));
        this.addAspectStackToItem(Items.FLOWERING_AZALEA, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 7));
        this.addAspectStackToItem(Items.AZALEA, new AspectStack(Aspect.HERBA, 10).with(Aspect.VICTUS, 3));
        this.addAspectStackToItem(Items.RED_MUSHROOM_BLOCK, new AspectStack(Aspect.HERBA, 15));
        this.addAspectStackToItem(Items.BROWN_MUSHROOM_BLOCK, new AspectStack(Aspect.HERBA, 10));
        this.addAspectStackToItem(Items.MUSHROOM_STEM, new AspectStack(Aspect.HERBA, 10));
        this.addAspectStackToItem(Items.MUDDY_MANGROVE_ROOTS, new AspectStack(Aspect.HERBA, 15).with(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addAspectStackToItem(Items.MANGROVE_ROOTS, new AspectStack(Aspect.HERBA, 15));
        this.addAspectStackToItem(Items.BONE_BLOCK, new AspectStack(Aspect.SENSUS, 33));
        this.addAspectStackToItem(Items.SOUL_SAND, new AspectStack(Aspect.TERRA, 3).with(Aspect.SPIRITUS, 3)
                .with(Aspect.VINCULUM));
        this.addAspectStackToItem(Items.SOUL_SOIL, new AspectStack(Aspect.TERRA, 3).with(Aspect.SPIRITUS, 3)
                .with(Aspect.VINCULUM).with(Aspect.VOLATUS));
        this.addAspectStackToItem(Items.WARPED_NYLIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.HERBA).with(Aspect.INFERNO));
        this.addAspectStackToItem(Items.CRIMSON_NYLIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.HERBA).with(Aspect.INFERNO));
        this.addAspectStackToItem(Items.CRYING_OBSIDIAN, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 5).with(Aspect.TENEBRAE, 5)
                .with(Aspect.PRAECANTATIO));
        this.addAspectStackToItem(Items.MAGMA_BLOCK, new AspectStack(Aspect.IGNIS, 10).with(Aspect.TERRA, 5).with(Aspect.INFERNO));
        this.addAspectStackToItem(Items.POINTED_DRIPSTONE, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO).with(Aspect.AQUA));
        this.addAspectStackToItem(Items.DRIPSTONE_BLOCK, new AspectStack(Aspect.TERRA, 10).with(Aspect.ORDO, 5).with(Aspect.AQUA, 3));
        this.addAspectStackToItem(Items.CALCITE, new AspectStack(Aspect.TERRA, 20).with(Aspect.ORDO, 5));
        this.addAspectStackToItem(Items.MOSS_CARPET, new AspectStack(Aspect.TERRA).with(Aspect.HERBA).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.MOSS_BLOCK, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 5).with(Aspect.VICTUS, 5));
        this.addAspectStackToItem(Items.SNOW, new AspectStack(Aspect.GELUM));
        this.addAspectStackToItem(Items.SNOW_BLOCK, new AspectStack(Aspect.GELUM, 3));
        this.addAspectStackToItem(Items.BLUE_ICE, new AspectStack(Aspect.GELUM, 30));
        this.addAspectStackToItem(Items.PACKED_ICE, new AspectStack(Aspect.GELUM, 25));
        this.addAspectStackToItem(Items.CLAY, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addAspectStackToItem(Items.ROOTED_DIRT, new AspectStack(Aspect.TERRA, 4));
        this.addAspectStackToItem(Items.COARSE_DIRT, new AspectStack(Aspect.TERRA, 3));
        this.addAspectStackToItem(Items.MYCELIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA).with(Aspect.VITIUM));
        this.addAspectStackToItem(Items.STRING, new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO));
        this.addAspectStackToItem(Items.DIAMOND_BLOCK, new AspectStack(Aspect.VITREUS, 101).with(Aspect.DESIDERIUM, 10));
        this.addAspectStackToItem(Items.LAPIS_BLOCK, new AspectStack(Aspect.SENSUS, 33).with(Aspect.TERRA, 13).with(Aspect.DESIDERIUM, 13));
        this.addAspectStackToItem(Items.EMERALD_BLOCK, new AspectStack(Aspect.VITREUS, 101).with(Aspect.DESIDERIUM, 67));
        this.addAspectStackToItem(Items.REDSTONE_BLOCK, new AspectStack(Aspect.DESIDERIUM, 67).with(Aspect.POTENTIA, 67));
        this.addAspectStackToItem(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, new AspectStack(Aspect.METALLUM, 15).with(Aspect.DESIDERIUM, 15)
                .with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.GOLD_BLOCK, new AspectStack(Aspect.METALLUM, 67).with(Aspect.DESIDERIUM, 67));
        this.addAspectStackToItem(Items.COAL_BLOCK, new AspectStack(Aspect.POTENTIA, 67).with(Aspect.IGNIS, 67));
        this.addAspectStackToItem(Items.NETHERRACK, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2));
        this.addAspectStackToItem(Items.PRISMARINE_SHARD, new AspectStack(Aspect.AQUA, 5)
                .with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.PRISMARINE_CRYSTALS, new AspectStack(Aspect.AQUA, 5)
                .with(Aspect.LUX, 5).with(Aspect.VITREUS, 5));
        this.addAspectStackToItem(Items.SEA_LANTERN, new AspectStack(Aspect.AQUA, 33).with(Aspect.VITREUS, 18).with(Aspect.LUX, 18)
                .with(Aspect.TERRA, 15));
        this.addAspectStackToItem(Items.TUFF, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.CHEST, new AspectStack(Aspect.HERBA, 18));
        this.addAspectStackToItem(Items.GRASS_BLOCK, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 2));
        this.addAspectStackToItem(Items.DIRT, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 2));
        this.addAspectStackToItem(Items.PODZOL, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA));
        this.addAspectStackToItem(Items.BEDROCK, new AspectStack(Aspect.VACOUS, 25).with(Aspect.PERDITIO, 25)
                .with(Aspect.TERRA, 25).with(Aspect.TENEBRAE, 25));
        this.addAspectStackToItem(Items.SAND, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.RED_SAND, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.GRAVEL, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 2));
        this.addAspectStackToItem(Items.COBWEB, new AspectStack(Aspect.VINCULUM, 5).with(Aspect.BESTIA));
        this.addAspectStackToItem(Items.BROWN_MUSHROOM, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2)
                .with(Aspect.TERRA, 2));
        this.addAspectStackToItem(Items.RED_MUSHROOM, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2)
                .with(Aspect.IGNIS, 2));
        this.addAspectStackToItem(Items.SUGAR_CANE, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3)
                .with(Aspect.AER, 2));
        this.addAspectStackToItem(Items.OBSIDIAN, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 5)
                .with(Aspect.TENEBRAE, 5));
        this.addAspectStackToItem(Items.CHORUS_PLANT, new AspectStack(Aspect.ALIENIS, 5).with(Aspect.HERBA, 5));
        this.addAspectStackToItem(Items.CHORUS_FLOWER, new AspectStack(Aspect.ALIENIS, 5).with(Aspect.HERBA, 5)
                .with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.ICE, new AspectStack(Aspect.GELUM, 20));
        this.addAspectStackToItem(Items.CACTUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 5)
                .with(Aspect.AVERSIO));
        this.addAspectStackToItem(Items.NETHERRACK, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2));
        this.addAspectStackToItem(Items.VINE, new AspectStack(Aspect.HERBA, 5));
        this.addAspectStackToItem(Items.LILY_PAD, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA));
        this.addAspectStackToItem(Items.END_PORTAL_FRAME, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.MOTUS, 10)
                .with(Aspect.PRAECANTATIO, 5).with(Aspect.ALIENIS, 10));
        this.addAspectStackToItem(Items.DRAGON_EGG, new AspectStack(Aspect.ALIENIS, 15).with(Aspect.MOTUS, 15)
                .with(Aspect.TENEBRAE, 15).with(Aspect.PRAECANTATIO, 5).with(Aspect.BESTIA, 15));
        this.addAspectStackToItem(Items.DIRT_PATH, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 2)
                .with(Aspect.ORDO, 2));
        this.addAspectStackToItem(Items.SUNFLOWER, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.LILAC, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.ROSE_BUSH, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.PEONY, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addAspectStackToItem(Items.TALL_GRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addAspectStackToItem(Items.LARGE_FERN, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addAspectStackToItem(Items.RED_CONCRETE, new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2)
                .with(Aspect.AQUA).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.BLACK_CONCRETE, new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2)
                .with(Aspect.AQUA).with(Aspect.ORDO));
        this.addAspectStackToItem(Items.SADDLE, new AspectStack(Aspect.BESTIA, 10).with(Aspect.MOTUS, 10)
                .with(Aspect.ORDO, 5));
        this.addAspectStackToItem(Items.ELYTRA, new AspectStack(Aspect.MOTUS, 15).with(Aspect.VOLATUS, 20));
        this.addAspectStackToItem(Items.APPLE, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS, 5));
        this.addAspectStackToItem(Items.GOLD_INGOT, new AspectStack(Aspect.METALLUM, 15).with(Aspect.DESIDERIUM, 15));
        this.addAspectStackToItem(Items.STRING, new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO));
        this.addAspectStackToItem(Items.FEATHER, new AspectStack(Aspect.AER, 5).with(Aspect.VOLATUS, 5));
        this.addAspectStackToItem(Items.GUNPOWDER, new AspectStack(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 10)
                .with(Aspect.ALKIMIA, 5));
        this.addAspectStackToItem(Items.CHAINMAIL_HELMET, new AspectStack(Aspect.METALLUM, 42).with(Aspect.PRAEMUNIO, 8));
        this.addAspectStackToItem(Items.CHAINMAIL_CHESTPLATE, new AspectStack(Aspect.METALLUM, 67).with(Aspect.PRAEMUNIO, 20));
        this.addAspectStackToItem(Items.CHAINMAIL_LEGGINGS, new AspectStack(Aspect.METALLUM, 58).with(Aspect.PRAEMUNIO, 16));
        this.addAspectStackToItem(Items.CHAINMAIL_BOOTS, new AspectStack(Aspect.METALLUM, 33).with(Aspect.PRAEMUNIO, 4));
        this.addAspectStackToItem(Items.FLINT, new AspectStack(Aspect.METALLUM, 11).with(Aspect.IGNIS, 10)
                .with(Aspect.TERRA, 3).with(Aspect.INSTRUMENTUM, 8));
        this.addAspectStackToItem(Items.PORKCHOP, new AspectStack(Aspect.BESTIA, 5).with(Aspect.VICTUS, 5)
                .with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.ENCHANTED_GOLDEN_APPLE, new AspectStack(Aspect.VICTUS, 15)
                .with(Aspect.PRAEMUNIO, 15));
        this.addAspectStackToItem(Items.WATER_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.AQUA, 20)
                .with(Aspect.VACOUS, 5));
        this.addAspectStackToItem(Items.LAVA_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.IGNIS, 15)
                .with(Aspect.VACOUS, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.SNOWBALL, new AspectStack(Aspect.GELUM));
        this.addAspectStackToItem(Items.MILK_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.AQUA, 5)
                .with(Aspect.VACOUS, 5).with(Aspect.VICTUS, 10).with(Aspect.BESTIA, 5));
        this.addAspectStackToItem(Items.CLAY_BALL, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addAspectStackToItem(Items.EGG, new AspectStack(Aspect.VICTUS, 5).with(Aspect.BESTIA, 5));
        this.addAspectStackToItem(Items.GLOWSTONE_DUST, new AspectStack(Aspect.LUX, 10).with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.INK_SAC, new AspectStack(Aspect.SENSUS, 5).with(Aspect.AQUA, 2)
                .with(Aspect.BESTIA, 2));
        this.addAspectStackToItem(Items.COCOA_BEANS, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.POTENTIA, 2).with(Aspect.DESIDERIUM, 2));
        this.addAspectStackToItem(Items.ORANGE_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.MAGENTA_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addAspectStackToItem(Items.LIGHT_BLUE_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.YELLOW_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addAspectStackToItem(Items.PINK_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addAspectStackToItem(Items.LIGHT_GRAY_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.RED_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addAspectStackToItem(Items.BONE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA).with(Aspect.VICTUS, 2).with(Aspect.MORTUS));
        this.addAspectStackToItem(Items.SUGAR, new AspectStack(Aspect.HERBA, 3)
                .with(Aspect.AQUA, 2).with(Aspect.AER).with(Aspect.POTENTIA)
                .with(Aspect.DESIDERIUM));
        this.addAspectStackToItem(Items.BEEF, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.CHICKEN, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.AER, 5));
        this.addAspectStackToItem(Items.ROTTEN_FLESH, new AspectStack(Aspect.VICTUS, 5)
                .with(Aspect.PERDITIO, 5).with(Aspect.HUMANUS, 5));
        this.addAspectStackToItem(Items.ENDER_PEARL, new AspectStack(Aspect.MOTUS, 15)
                .with(Aspect.ALIENIS, 10));
        this.addAspectStackToItem(Items.BLAZE_ROD, new AspectStack(Aspect.IGNIS, 15)
                .with(Aspect.MOTUS, 10));
        this.addAspectStackToItem(Items.GHAST_TEAR, new AspectStack(Aspect.IGNIS, 6)
                .with(Aspect.PERDITIO, 2).with(Aspect.POTENTIA, 2).with(Aspect.ALKIMIA, 2));
        this.addAspectStackToItem(Items.NETHER_WART, new AspectStack(Aspect.ALKIMIA, 3)
                .with(Aspect.HERBA).with(Aspect.VITIUM, 2));
        this.addAspectStackToItem(Items.SPIDER_EYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.BESTIA, 5).with(Aspect.MORTUS, 5));
        this.addAspectStackToItem(Items.EXPERIENCE_BOTTLE, new AspectStack(Aspect.COGNITIO, 20));
        this.addAspectStackToItem(Items.WRITTEN_BOOK, new AspectStack(Aspect.COGNITIO, 3)
                .with(Aspect.AQUA, 4).with(Aspect.HERBA, 4).with(Aspect.AER, 5)
                .with(Aspect.BESTIA, 3).with(Aspect.SENSUS, 3).with(Aspect.VOLATUS, 3));
        this.addAspectStackToItem(Items.CARROT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.SENSUS, 5));
        this.addAspectStackToItem(Items.POTATO, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.POISONOUS_POTATO, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.MORTUS, 5));
        this.addAspectStackToItem(Items.SKELETON_SKULL, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.EXANIMIS, 10));
        this.addAspectStackToItem(Items.WITHER_SKELETON_SKULL, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.EXANIMIS, 10));
        this.addAspectStackToItem(Items.PLAYER_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.HUMANUS, 10));
        this.addAspectStackToItem(Items.ZOMBIE_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.HUMANUS, 10));
        this.addAspectStackToItem(Items.CREEPER_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.PERDITIO, 5).with(Aspect.IGNIS, 5));
        this.addAspectStackToItem(Items.DRAGON_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.TENEBRAE, 10).with(Aspect.IGNIS, 10));
        this.addAspectStackToItem(Items.NETHER_STAR, new AspectStack(Aspect.ORDO, 20)
                .with(Aspect.ALIENIS, 20).with(Aspect.PRAECANTATIO, 20).with(Aspect.AURAM, 20));
        this.addAspectStackToItem(Items.RABBIT, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.RABBIT_STEW, new AspectStack(Aspect.HERBA, 12)
                .with(Aspect.VICTUS, 11).with(Aspect.BESTIA, 3).with(Aspect.SENSUS, 3)
                .with(Aspect.VACOUS, 3).with(Aspect.IGNIS).with(Aspect.TENEBRAE));
        this.addAspectStackToItem(Items.RABBIT_FOOT, new AspectStack(Aspect.MOTUS, 10)
                .with(Aspect.BESTIA, 5).with(Aspect.PRAEMUNIO, 5).with(Aspect.ALKIMIA, 5));
        this.addAspectStackToItem(Items.RABBIT_HIDE, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.PRAEMUNIO, 2));
        this.addAspectStackToItem(Items.GOLDEN_HORSE_ARMOR, new AspectStack(Aspect.METALLUM, 10)
                .with(Aspect.PRAEMUNIO, 15).with(Aspect.BESTIA, 5));
        this.addAspectStackToItem(Items.DIAMOND_HORSE_ARMOR, new AspectStack(Aspect.PRAEMUNIO, 20)
                .with(Aspect.BESTIA, 5).with(Aspect.VITREUS, 15));
        this.addAspectStackToItem(Items.NAME_TAG, new AspectStack(Aspect.BESTIA, 10)
                .with(Aspect.COGNITIO, 10));
        this.addAspectStackToItem(Items.MUTTON, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.CHORUS_FRUIT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.SENSUS, 5).with(Aspect.ALIENIS, 5));
        this.addAspectStackToItem(Items.BEETROOT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.DESIDERIUM, 5));
        this.addAspectStackToItem(Items.DRAGON_BREATH, new AspectStack(Aspect.IGNIS, 10)
                .with(Aspect.PERDITIO, 10).with(Aspect.TENEBRAE, 10).with(Aspect.ALKIMIA, 10));
        this.addAspectStackToItem(Items.TOTEM_OF_UNDYING, new AspectStack(Aspect.VICTUS, 25)
                .with(Aspect.ORDO, 10).with(Aspect.PERDITIO, 10).with(Aspect.EXANIMIS, 10));
        this.addAspectStackToItem(Items.SHULKER_SHELL, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VACOUS, 5).with(Aspect.ALIENIS, 5).with(Aspect.PRAEMUNIO, 10));
        this.addAspectStackToItem(Items.MUSIC_DISC_13, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.AQUA, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_CAT, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.BESTIA, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_BLOCKS, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.INSTRUMENTUM, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_CHIRP, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_FAR, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.ALIENIS, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_MALL, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.HUMANUS, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_MELLOHI, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.FABRICO, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_STAL, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.TENEBRAE, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_STRAD, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.POTENTIA, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_WARD, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.VICTUS, 5));
        this.addAspectStackToItem(Items.MUSIC_DISC_11, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10));
        this.addAspectStackToItem(Items.MUSIC_DISC_WAIT, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.VINCULUM, 5));
        this.addAspectStackToItem(ThaumcraftItems.THAUMONOMICON, new AspectStack(Aspect.AER)
                .with(Aspect.TERRA, 2).with(Aspect.IGNIS, 3).with(Aspect.AQUA, 4)
                .with(Aspect.ORDO, 5).with(Aspect.PERDITIO, 6).with(Aspect.VACOUS, 7)
                .with(Aspect.LUX, 8).with(Aspect.MOTUS, 9).with(Aspect.GELUM, 10)
                .with(Aspect.VITREUS, 11).with(Aspect.METALLUM, 12).with(Aspect.VICTUS, 13)
                .with(Aspect.MORTUS, 14).with(Aspect.POTENTIA, 15).with(Aspect.PERMUTATIO, 16)
                .with(Aspect.PRAECANTATIO, 17).with(Aspect.AURAM, 18).with(Aspect.ALKIMIA, 19)
                .with(Aspect.VITIUM, 20).with(Aspect.TENEBRAE, 21).with(Aspect.ALIENIS, 22)
                .with(Aspect.VOLATUS, 23).with(Aspect.HERBA, 24).with(Aspect.INSTRUMENTUM, 25)
                .with(Aspect.FABRICO, 26).with(Aspect.MACHINA, 27).with(Aspect.VINCULUM, 28)
                .with(Aspect.SPIRITUS, 29).with(Aspect.COGNITIO, 30).with(Aspect.SENSUS, 31)
                .with(Aspect.AVERSIO, 32).with(Aspect.PRAEMUNIO, 33).with(Aspect.DESIDERIUM, 34)
                .with(Aspect.EXANIMIS, 35).with(Aspect.BESTIA, 36).with(Aspect.HUMANUS, 37)
                .with(Aspect.ECHO, 38).with(Aspect.INFERNO, 39).with(Aspect.TEMPUS, 40));

        for(Aspect aspect : Aspect.values()) {
            this.addAspectStackToItem(ThaumcraftItems.CRYSTAL, CrystalHelper.buildType(aspect), new AspectStack(aspect));
        }
    }
}