package llama.thaumcraft.magic;

import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagPacketSerializer;

import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AspectRegistry {
    public static final Map<Map<Item, NbtCompound>, Map<Aspect, Integer>> ITEMS = new LinkedHashMap<>();

    private void addItemAspect(Item item, NbtCompound nbtCompound, AspectStack itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, nbtCompound);
        }}, itemAspects.getAspects());
    }

    private void addItemAspect(Item item, AspectStack itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, new NbtCompound());
        }}, itemAspects.getAspects());
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

    public static final AspectStack DEFAULT_LOG = new AspectStack(Aspect.HERBA, 20);
    public static final AspectStack DEFAULT_STRIPPED_LOG = new AspectStack(Aspect.HERBA, 17);
    public static final AspectStack DEFAULT_WOOD = new AspectStack(Aspect.HERBA, 22);
    public static final AspectStack DEFAULT_STRIPPED_WOOD = new AspectStack(Aspect.HERBA, 19);
    public static final AspectStack DEFAULT_PLANK = new AspectStack(Aspect.HERBA, 3);
    public static final AspectStack DEFAULT_STAIRS = new AspectStack(Aspect.HERBA, 3);
    public static final AspectStack DEFAULT_SLAB = new AspectStack(Aspect.HERBA);
    public static final AspectStack DEFAULT_FENCE = new AspectStack(Aspect.HERBA, 3);
    public static final AspectStack DEFAULT_FENCE_GATES = new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5);
    public static final AspectStack DEFAULT_DOOR = new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5);
    public static final AspectStack DEFAULT_TRAPDOOR = new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5);
    public static final AspectStack DEFAULT_PLATE = new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5);
    public static final AspectStack DEFAULT_BUTTON = new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5);
    public static final AspectStack DEFAULT_SIGN = new AspectStack(Aspect.HERBA, 4);
    public static final AspectStack DEFAULT_HANGING_SIGN = new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 15);
    public static final AspectStack DEFAULT_BOAT = new AspectStack(Aspect.HERBA, 11).with(Aspect.AQUA, 10).with(Aspect.MOTUS, 15);
    public static final AspectStack DEFAULT_CHEST_BOAT = new AspectStack(Aspect.HERBA, 20).with(Aspect.AQUA, 10).with(Aspect.MOTUS, 15);
    public static final AspectStack DEFAULT_LEAVES = new AspectStack(Aspect.HERBA, 5);
    public static final AspectStack DEFAULT_BREED = new AspectStack(Aspect.TERRA, 5);
    public static final AspectStack DEFAULT_POLISHED_BREED = new AspectStack(Aspect.TERRA, 3);
    public static final AspectStack DEFAULT_BREED_STAIRS = new AspectStack(Aspect.TERRA, 5);
    public static final AspectStack DEFAULT_POLISHED_BREED_STAIRS = new AspectStack(Aspect.TERRA, 3);
    public static final AspectStack DEFAULT_BREED_SLAB = new AspectStack(Aspect.TERRA, 1);
    public static final AspectStack DEFAULT_POLISHED_BREED_SLAB = new AspectStack(Aspect.TERRA, 1);
    public static final AspectStack DEFAULT_BREED_WALL = new AspectStack(Aspect.TERRA, 2);
    public static final AspectStack DEFAULT_SANDSTONE = new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15);
    public static final AspectStack DEFAULT_SANDSTONE_STAIRS = new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15);
    public static final AspectStack DEFAULT_SANDSTONE_SLAB = new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5);
    public static final AspectStack DEFAULT_SANDSTONE_WALL = new AspectStack(Aspect.TERRA, 7).with(Aspect.PERDITIO, 7);
    public static final AspectStack DEFAULT_CHISELED_SANDSTONE = new AspectStack(Aspect.TERRA, 7).with(Aspect.PERDITIO, 7).with(Aspect.ORDO);
    public static final AspectStack DEFAULT_CUT_SANDSTONE = new AspectStack(Aspect.TERRA, 12).with(Aspect.PERDITIO, 12).with(Aspect.ORDO);
    public static final AspectStack DEFAULT_CUT_SANDSTONE_SLAB = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 3).with(Aspect.ORDO);
    public static final AspectStack DEFAULT_BANNER = new AspectStack(Aspect.BESTIA, 49).with(Aspect.FABRICO, 13).with(Aspect.SENSUS, 13);
    public static final AspectStack DEFAULT_CANDLE = new AspectStack(Aspect.BESTIA, 5).with(Aspect.HERBA, 3).with(Aspect.FABRICO).with(Aspect.SENSUS);
    public static final AspectStack DEFAULT_BED = new AspectStack(Aspect.BESTIA, 33).with(Aspect.FABRICO, 11).with(Aspect.HERBA, 6).with(Aspect.SENSUS);
    public static final AspectStack DEFAULT_GLASS_PANE = new AspectStack(Aspect.VITREUS);
    public static final AspectStack DEFAULT_GLASS = new AspectStack(Aspect.VITREUS, 5);
    public static final AspectStack DEFAULT_CONCRETE_POWDER = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2);
    public static final AspectStack DEFAULT_CONCRETE = new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2).with(Aspect.AQUA).with(Aspect.ORDO);
    public static final AspectStack DEFAULT_GLAZED_TERRACOTE = new AspectStack(Aspect.AQUA, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS, 2).with(Aspect.SENSUS, 2);
    public static final AspectStack DEFAULT_WOOL = new AspectStack(Aspect.BESTIA, 11).with(Aspect.FABRICO, 3).with(Aspect.SENSUS, 3);
    public static final AspectStack DEFAULT_CARPET = new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO, 1).with(Aspect.SENSUS, 1);
    public static final AspectStack DEFAULT_TERRACOTE = new AspectStack(Aspect.AQUA, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS).with(Aspect.SENSUS);
    public static AspectStack DEFAULT_SAPLING = new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 5);
    public static AspectStack DEFAULT_FLOWER = new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5).with(Aspect.VICTUS);
    public static AspectStack DEFAULT_SHULKER_BOX = new AspectStack(Aspect.HERBA, 13)
            .with(Aspect.BESTIA, 7)
            .with(Aspect.ALIENIS, 7)
            .with(Aspect.VACOUS, 7)
            .with(Aspect.PRAEMUNIO, 15)
            .with(Aspect.HERBA, 16);
    public static AspectStack DEFAULT_FISH = new AspectStack(Aspect.VICTUS, 5).with(Aspect.BESTIA, 5).with(Aspect.AQUA, 5);

    public void registryItems() {
        /////////////////////// - - - - - SAPLINGS - - - - - //////////////////////////
        Registries.ITEM.forEach((item) -> {
            AspectStack aspectStack = null;
            //ItemTags.SAPLINGS
            if (item.getDefaultStack().isIn(ItemTags.SAPLINGS)) {
                aspectStack = DEFAULT_SAPLING;
            }

            if (item.getDefaultStack().isIn(ItemTags.LOGS)) {
                aspectStack = DEFAULT_LOG;
            }

            this.addItemAspect(item, aspectStack);
        });

        this.addItemAspect(Items.OAK_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.SPRUCE_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.BIRCH_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.JUNGLE_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.ACACIA_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.DARK_OAK_SAPLING, DEFAULT_SAPLING);
        this.addItemAspect(Items.MANGROVE_PROPAGULE, DEFAULT_SAPLING);
        this.addItemAspect(Items.CHERRY_SAPLING, DEFAULT_SAPLING);
        /////////////////////// - - - - - LOGS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.SPRUCE_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.BIRCH_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.JUNGLE_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.ACACIA_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.DARK_OAK_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.MANGROVE_LOG, DEFAULT_LOG);
        this.addItemAspect(Items.CHERRY_LOG, DEFAULT_LOG);
        /////////////////////// - - - - - STRIPPED LOGS - - - - - //////////////////////////
        this.addItemAspect(Items.STRIPPED_OAK_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_SPRUCE_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_BIRCH_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_JUNGLE_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_ACACIA_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_DARK_OAK_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_MANGROVE_LOG, DEFAULT_STRIPPED_LOG);
        this.addItemAspect(Items.STRIPPED_CHERRY_LOG, DEFAULT_STRIPPED_LOG);
        /////////////////////// - - - - - WOODS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.SPRUCE_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.BIRCH_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.JUNGLE_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.ACACIA_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.DARK_OAK_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.MANGROVE_WOOD, DEFAULT_WOOD);
        this.addItemAspect(Items.CHERRY_WOOD, DEFAULT_WOOD);
        /////////////////////// - - - - - STRIPPED WOODS - - - - - //////////////////////////
        this.addItemAspect(Items.STRIPPED_OAK_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_SPRUCE_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_BIRCH_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_JUNGLE_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_ACACIA_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_DARK_OAK_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_MANGROVE_WOOD, DEFAULT_STRIPPED_WOOD);
        this.addItemAspect(Items.STRIPPED_CHERRY_WOOD, DEFAULT_STRIPPED_WOOD);
        /////////////////////// - - - - - PLANKS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.SPRUCE_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.BIRCH_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.JUNGLE_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.ACACIA_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.DARK_OAK_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.MANGROVE_PLANKS, DEFAULT_PLANK);
        this.addItemAspect(Items.CHERRY_PLANKS, DEFAULT_PLANK);
        /////////////////////// - - - - - STAIRS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.SPRUCE_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.BIRCH_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.JUNGLE_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.ACACIA_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.DARK_OAK_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.MANGROVE_STAIRS, DEFAULT_STAIRS);
        this.addItemAspect(Items.CHERRY_STAIRS, DEFAULT_STAIRS);
        /////////////////////// - - - - - SLABS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.SPRUCE_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.BIRCH_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.JUNGLE_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.ACACIA_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.DARK_OAK_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.MANGROVE_SLAB, DEFAULT_SLAB);
        this.addItemAspect(Items.CHERRY_SLAB, DEFAULT_SLAB);
        /////////////////////// - - - - - FENCE - - - - -
        this.addItemAspect(Items.OAK_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.SPRUCE_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.BIRCH_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.JUNGLE_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.ACACIA_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.DARK_OAK_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.MANGROVE_FENCE, DEFAULT_FENCE);
        this.addItemAspect(Items.CHERRY_FENCE, DEFAULT_FENCE);
        /////////////////////// - - - - - FENCE GATE - - - - -
        this.addItemAspect(Items.OAK_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.SPRUCE_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.BIRCH_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.JUNGLE_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.ACACIA_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.DARK_OAK_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.MANGROVE_FENCE_GATE, DEFAULT_FENCE_GATES);
        this.addItemAspect(Items.CHERRY_FENCE_GATE, DEFAULT_FENCE_GATES);
        /////////////////////// - - - - - DOORS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.SPRUCE_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.BIRCH_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.JUNGLE_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.ACACIA_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.DARK_OAK_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.MANGROVE_DOOR, DEFAULT_DOOR);
        this.addItemAspect(Items.CHERRY_DOOR, DEFAULT_DOOR);
        /////////////////////// - - - - - TRAPDOORS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.SPRUCE_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.BIRCH_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.JUNGLE_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.ACACIA_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.DARK_OAK_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.MANGROVE_TRAPDOOR, DEFAULT_TRAPDOOR);
        this.addItemAspect(Items.CHERRY_TRAPDOOR, DEFAULT_TRAPDOOR);
        /////////////////////// - - - - - PLATE - - - - -
        this.addItemAspect(Items.OAK_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.SPRUCE_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.BIRCH_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.JUNGLE_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.ACACIA_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.DARK_OAK_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.MANGROVE_PRESSURE_PLATE, DEFAULT_PLATE);
        this.addItemAspect(Items.CHERRY_PRESSURE_PLATE, DEFAULT_PLATE);
        /////////////////////// - - - - - BUTTONS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.SPRUCE_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.BIRCH_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.JUNGLE_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.ACACIA_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.DARK_OAK_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.MANGROVE_BUTTON, DEFAULT_BUTTON);
        this.addItemAspect(Items.CHERRY_BUTTON, DEFAULT_BUTTON);
        /////////////////////// - - - - - SIGNS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.SPRUCE_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.BIRCH_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.JUNGLE_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.ACACIA_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.DARK_OAK_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.MANGROVE_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.CHERRY_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.BAMBOO_SIGN, DEFAULT_SIGN);
        this.addItemAspect(Items.CRIMSON_SIGN, DEFAULT_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        this.addItemAspect(Items.WARPED_SIGN, DEFAULT_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        /////////////////////// - - - - - HANGING SIGNS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.SPRUCE_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.BIRCH_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.JUNGLE_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.ACACIA_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.DARK_OAK_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.MANGROVE_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.CHERRY_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.BAMBOO_HANGING_SIGN, DEFAULT_HANGING_SIGN);
        this.addItemAspect(Items.CRIMSON_SIGN, DEFAULT_HANGING_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        this.addItemAspect(Items.WARPED_SIGN, DEFAULT_HANGING_SIGN.with(Aspect.INFERNO).with(Aspect.IGNIS, 3));
        /////////////////////// - - - - - LEAVES - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.SPRUCE_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.BIRCH_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.JUNGLE_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.ACACIA_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.DARK_OAK_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.MANGROVE_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.CHERRY_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.AZALEA_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.FLOWERING_AZALEA_LEAVES, DEFAULT_LEAVES);
        this.addItemAspect(Items.NETHER_WART_BLOCK, DEFAULT_LEAVES.with(Aspect.INFERNO, 3).with(Aspect.IGNIS, 5));
        this.addItemAspect(Items.WARPED_WART_BLOCK, DEFAULT_LEAVES.with(Aspect.INFERNO, 3).with(Aspect.IGNIS, 5));
        /////////////////////// - - - - - BOATS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.SPRUCE_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.BIRCH_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.JUNGLE_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.ACACIA_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.DARK_OAK_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.MANGROVE_BOAT, DEFAULT_BOAT);
        this.addItemAspect(Items.CHERRY_BOAT, DEFAULT_BOAT);
        /////////////////////// - - - - - CHEST BOATS - - - - - //////////////////////////
        this.addItemAspect(Items.OAK_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.SPRUCE_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.BIRCH_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.JUNGLE_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.ACACIA_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.DARK_OAK_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.MANGROVE_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        this.addItemAspect(Items.CHERRY_CHEST_BOAT, DEFAULT_CHEST_BOAT);
        /////////////////////// - - - - - FLOWERS - - - - - //////////////////////////
        this.addItemAspect(Items.DANDELION, DEFAULT_FLOWER);
        this.addItemAspect(Items.POPPY, DEFAULT_FLOWER);
        this.addItemAspect(Items.BLUE_ORCHID, DEFAULT_FLOWER);
        this.addItemAspect(Items.ALLIUM, DEFAULT_FLOWER);
        this.addItemAspect(Items.AZURE_BLUET, DEFAULT_FLOWER);
        this.addItemAspect(Items.RED_TULIP, DEFAULT_FLOWER);
        this.addItemAspect(Items.ORANGE_TULIP, DEFAULT_FLOWER);
        this.addItemAspect(Items.WHITE_TULIP, DEFAULT_FLOWER);
        this.addItemAspect(Items.PINK_TULIP, DEFAULT_FLOWER);
        this.addItemAspect(Items.OXEYE_DAISY, DEFAULT_FLOWER);
        this.addItemAspect(Items.CORNFLOWER, DEFAULT_FLOWER);
        this.addItemAspect(Items.LILY_OF_THE_VALLEY, DEFAULT_FLOWER);
        this.addItemAspect(Items.PINK_PETALS, DEFAULT_FLOWER);
        this.addItemAspect(Items.TORCHFLOWER, DEFAULT_FLOWER.with(Aspect.LUX));
        this.addItemAspect(Items.WITHER_ROSE, DEFAULT_FLOWER.with(Aspect.MORTUS, 5));
        this.addItemAspect(Items.SPORE_BLOSSOM, DEFAULT_FLOWER.with(Aspect.TEMPUS, 5));
        this.addItemAspect(Items.PITCHER_PLANT, DEFAULT_FLOWER.with(Aspect.TEMPUS, 15));
        /////////////////////// - - - - - SHULKER_BOXS - - - - - //////////////////////////
        this.addItemAspect(Items.SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.WHITE_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.LIGHT_GRAY_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.GRAY_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.BLACK_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.BROWN_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.RED_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.ORANGE_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.YELLOW_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.LIME_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.GREEN_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.CYAN_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.LIGHT_BLUE_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.BLUE_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.PURPLE_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.MAGENTA_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        this.addItemAspect(Items.PINK_SHULKER_BOX, DEFAULT_SHULKER_BOX);
        /////////////////////// - - - - - FISHS - - - - - //////////////////////////
        this.addItemAspect(Items.COD, DEFAULT_FISH);
        this.addItemAspect(Items.SALMON, DEFAULT_FISH);
        this.addItemAspect(Items.TROPICAL_FISH, DEFAULT_FISH);
        this.addItemAspect(Items.PUFFERFISH, DEFAULT_FISH);
        /////////////////////// - - - - - STONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.STONE, new AspectStack(Aspect.TERRA, 5));
        this.addItemAspect(Items.STONE_STAIRS, new AspectStack(Aspect.TERRA, 5));
        this.addItemAspect(Items.STONE_SLAB, new AspectStack(Aspect.TERRA));
        this.addItemAspect(Items.STONE_PRESSURE_PLATE, new AspectStack(Aspect.TERRA, 7));
        this.addItemAspect(Items.STONE_BUTTON, new AspectStack(Aspect.TERRA, 3).with(Aspect.MACHINA, 5));
        /////////////////////// - - - - - COBBLESTONES ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.COBBLESTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO));
        this.addItemAspect(Items.COBBLESTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO));
        this.addItemAspect(Items.COBBLESTONE_SLAB, new AspectStack(Aspect.TERRA));
        this.addItemAspect(Items.COBBLESTONE_WALL, new AspectStack(Aspect.TERRA, 3));
        /////////////////////// - - - - - MOSSY COBBLESTONES ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.MOSSY_COBBLESTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 3).with(Aspect.PERDITIO));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 3).with(Aspect.PERDITIO));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.HERBA));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA, 2));
        /////////////////////// - - - - - SMOOTH ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.SMOOTH_STONE, new AspectStack(Aspect.TERRA, 5));
        this.addItemAspect(Items.SMOOTH_STONE_SLAB, new AspectStack(Aspect.TERRA));
        /////////////////////// - - - - - STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.STONE_BRICKS, new AspectStack(Aspect.TERRA, 3));
        this.addItemAspect(Items.CRACKED_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS));
        this.addItemAspect(Items.STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3));
        this.addItemAspect(Items.STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA));
        this.addItemAspect(Items.STONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 2));
        this.addItemAspect(Items.CHISELED_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));
        /////////////////////// - - - - - MOSSY STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.MOSSY_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_WALL, new AspectStack(Aspect.TERRA).with(Aspect.HERBA, 2));
        /////////////////////// - - - - - BREEDS ITEMS - - - - - //////////////////////////
        //GRANITE
        this.addItemAspect(Items.GRANITE, DEFAULT_BREED);
        this.addItemAspect(Items.GRANITE_STAIRS, DEFAULT_BREED_STAIRS);
        this.addItemAspect(Items.GRANITE_SLAB, DEFAULT_BREED_SLAB);
        this.addItemAspect(Items.GRANITE_WALL, DEFAULT_BREED_WALL);
        this.addItemAspect(Items.POLISHED_GRANITE, DEFAULT_POLISHED_BREED);
        this.addItemAspect(Items.POLISHED_GRANITE_STAIRS, DEFAULT_POLISHED_BREED_STAIRS);
        this.addItemAspect(Items.POLISHED_GRANITE_SLAB, DEFAULT_POLISHED_BREED_SLAB);
        //DIORITE
        this.addItemAspect(Items.DIORITE, DEFAULT_BREED);
        this.addItemAspect(Items.DIORITE_STAIRS, DEFAULT_BREED_STAIRS);
        this.addItemAspect(Items.DIORITE_SLAB, DEFAULT_BREED_SLAB);
        this.addItemAspect(Items.DIORITE_WALL, DEFAULT_BREED_WALL);
        this.addItemAspect(Items.POLISHED_DIORITE, DEFAULT_POLISHED_BREED);
        this.addItemAspect(Items.POLISHED_DIORITE_STAIRS, DEFAULT_POLISHED_BREED_STAIRS);
        this.addItemAspect(Items.POLISHED_DIORITE_SLAB, DEFAULT_POLISHED_BREED_SLAB);
        //ANDESITE
        this.addItemAspect(Items.ANDESITE, DEFAULT_BREED);
        this.addItemAspect(Items.ANDESITE_STAIRS, DEFAULT_BREED_STAIRS);
        this.addItemAspect(Items.ANDESITE_SLAB, DEFAULT_BREED_SLAB);
        this.addItemAspect(Items.ANDESITE_WALL, DEFAULT_BREED_WALL);
        this.addItemAspect(Items.POLISHED_ANDESITE, DEFAULT_POLISHED_BREED);
        this.addItemAspect(Items.POLISHED_ANDESITE_STAIRS, DEFAULT_POLISHED_BREED_STAIRS);
        this.addItemAspect(Items.POLISHED_ANDESITE_SLAB, DEFAULT_POLISHED_BREED_SLAB);

        /////////////////////// - - - - - DEEPSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.DEEPSLATE, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2));

        this.addItemAspect(Items.COBBLED_DEEPSLATE, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2).with(Aspect.PERDITIO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_STAIRS, new AspectStack(Aspect.TERRA, 8).with(Aspect.ORDO, 2).with(Aspect.PERDITIO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_SLAB, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_WALL, new AspectStack(Aspect.TERRA, 4).with(Aspect.ORDO));

        this.addItemAspect(Items.CHISELED_DEEPSLATE, new AspectStack(Aspect.TERRA, 4).with(Aspect.ORDO, 4));

        this.addItemAspect(Items.POLISHED_DEEPSLATE, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_STAIRS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));

        this.addItemAspect(Items.DEEPSLATE_BRICKS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO));
        this.addItemAspect(Items.CRACKED_DEEPSLATE_BRICKS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO, 2).with(Aspect.IGNIS));
        this.addItemAspect(Items.DEEPSLATE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 6).with(Aspect.ORDO));
        this.addItemAspect(Items.DEEPSLATE_BRICK_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));
        this.addItemAspect(Items.DEEPSLATE_BRICK_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO));

        this.addItemAspect(Items.DEEPSLATE_TILES, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addItemAspect(Items.CRACKED_DEEPSLATE_TILES, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO).with(Aspect.IGNIS));
        this.addItemAspect(Items.DEEPSLATE_TILE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addItemAspect(Items.DEEPSLATE_TILE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.ORDO));
        this.addItemAspect(Items.DEEPSLATE_TILE_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.ORDO));

        this.addItemAspect(Items.REINFORCED_DEEPSLATE, new AspectStack(Aspect.TERRA, 20).with(Aspect.TEMPUS, 5).with(Aspect.AURAM, 20)
                .with(Aspect.TENEBRAE, 20).with(Aspect.ALIENIS, 20));
        /////////////////////// - - - - - BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.BRICK, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5).with(Aspect.IGNIS));
        this.addItemAspect(Items.BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15).with(Aspect.IGNIS, 3));
        this.addItemAspect(Items.BRICK_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15).with(Aspect.IGNIS, 3));
        this.addItemAspect(Items.BRICK_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5).with(Aspect.IGNIS));
        this.addItemAspect(Items.BRICK_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.AQUA, 7).with(Aspect.IGNIS, 2));
        /////////////////////// - - - - - MUD ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.MUD, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addItemAspect(Items.PACKED_MUD, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5)
                .with(Aspect.HERBA, 3).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.MUD_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addItemAspect(Items.MUD_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addItemAspect(Items.MUD_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.AQUA));
        this.addItemAspect(Items.MUD_BRICK_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.AQUA, 2));
        /////////////////////// - - - - - SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.SANDSTONE, DEFAULT_SANDSTONE);
        this.addItemAspect(Items.SANDSTONE_STAIRS, DEFAULT_SANDSTONE_STAIRS);
        this.addItemAspect(Items.SANDSTONE_SLAB, DEFAULT_SANDSTONE_SLAB);
        this.addItemAspect(Items.SANDSTONE_WALL, DEFAULT_SANDSTONE_WALL);
        this.addItemAspect(Items.CHISELED_SANDSTONE, DEFAULT_CHISELED_SANDSTONE);
        /////////////////////// - - - - - SMOOTH SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.SMOOTH_SANDSTONE, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addItemAspect(Items.SMOOTH_SANDSTONE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addItemAspect(Items.SMOOTH_SANDSTONE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5).with(Aspect.ORDO));
        /////////////////////// - - - - - CUT SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.CUT_SANDSTONE, DEFAULT_CUT_SANDSTONE);
        this.addItemAspect(Items.CUT_SANDSTONE_SLAB, DEFAULT_CUT_SANDSTONE_SLAB);
        /////////////////////// - - - - - RED SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.RED_SANDSTONE, DEFAULT_SANDSTONE);
        this.addItemAspect(Items.RED_SANDSTONE_STAIRS, DEFAULT_SANDSTONE_STAIRS);
        this.addItemAspect(Items.RED_SANDSTONE_SLAB, DEFAULT_SANDSTONE_SLAB);
        this.addItemAspect(Items.RED_SANDSTONE_WALL, DEFAULT_SANDSTONE_WALL);
        this.addItemAspect(Items.CHISELED_RED_SANDSTONE, DEFAULT_CHISELED_SANDSTONE);
        /////////////////////// - - - - - RED SMOOTH SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.PERDITIO, 15).with(Aspect.ORDO));
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.PERDITIO, 5).with(Aspect.ORDO));
        /////////////////////// - - - - - REDCUT SANDSTONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.CUT_RED_SANDSTONE, DEFAULT_CUT_SANDSTONE);
        this.addItemAspect(Items.CUT_RED_SANDSTONE_SLAB, DEFAULT_CUT_SANDSTONE_SLAB);
        /////////////////////// - - - - - PRISMARINE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.PRISMARINE, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addItemAspect(Items.PRISMARINE_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addItemAspect(Items.PRISMARINE_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addItemAspect(Items.PRISMARINE_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.AQUA, 7));
        /////////////////////// - - - - - PRISMARINE BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.PRISMARINE_BRICKS, new AspectStack(Aspect.TERRA, 33).with(Aspect.AQUA, 33));
        this.addItemAspect(Items.PRISMARINE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 33).with(Aspect.AQUA, 33));
        this.addItemAspect(Items.PRISMARINE_BRICK_SLAB, new AspectStack(Aspect.TERRA, 16).with(Aspect.AQUA, 16));
        /////////////////////// - - - - - DARK BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.DARK_PRISMARINE, new AspectStack(Aspect.TERRA, 31).with(Aspect.AQUA, 31).with(Aspect.SENSUS, 3).with(Aspect.BESTIA));
        this.addItemAspect(Items.DARK_PRISMARINE_STAIRS, new AspectStack(Aspect.TERRA, 31).with(Aspect.AQUA, 31).with(Aspect.SENSUS, 3).with(Aspect.BESTIA));
        this.addItemAspect(Items.DARK_PRISMARINE_SLAB, new AspectStack(Aspect.TERRA, 6).with(Aspect.AQUA, 6).with(Aspect.SENSUS).with(Aspect.BESTIA));
        /////////////////////// - - - - - NETHER BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.NETHER_BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 9).with(Aspect.ORDO, 3));
        this.addItemAspect(Items.CRACKED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 10).with(Aspect.ORDO, 3));
        this.addItemAspect(Items.NETHER_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 15).with(Aspect.IGNIS, 10).with(Aspect.ORDO, 3));
        this.addItemAspect(Items.NETHER_BRICK_SLAB, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO));
        this.addItemAspect(Items.NETHER_BRICK_WALL, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 7).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.NETHER_BRICK_FENCE, new AspectStack(Aspect.TERRA, 11).with(Aspect.IGNIS, 6).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.CHISELED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 11).with(Aspect.IGNIS, 5).with(Aspect.ORDO, 3));
        /////////////////////// - - - - - RED NETHER BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.RED_NETHER_BRICKS, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 4).with(Aspect.ALKIMIA, 4)
                .with(Aspect.VITIUM, 3).with(Aspect.ORDO).with(Aspect.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 7).with(Aspect.IGNIS, 4).with(Aspect.ALKIMIA, 4)
                .with(Aspect.VITIUM, 3).with(Aspect.ORDO).with(Aspect.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_SLAB, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.ALKIMIA).with(Aspect.VITIUM)
                .with(Aspect.ORDO).with(Aspect.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_WALL, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO));
        /////////////////////// - - - - - BASALT ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.BASALT, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.SMOOTH_BASALT, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 3).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.POLISHED_BASALT, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.ORDO));
        /////////////////////// - - - - - BLACK STONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.BLACKSTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addItemAspect(Items.GILDED_BLACKSTONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS)
                .with(Aspect.METALLUM, 5).with(Aspect.DESIDERIUM, 5));
        this.addItemAspect(Items.BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addItemAspect(Items.BLACKSTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS).with(Aspect.ORDO));
        this.addItemAspect(Items.BLACKSTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS, 2).with(Aspect.VACOUS).with(Aspect.ORDO));
        /////////////////////// - - - - - POLISHED BLACK STONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.CHISELED_POLISHED_BLACKSTONE, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_PRESSURE_PLATE, new AspectStack(Aspect.TERRA, 2).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BUTTON, new AspectStack(Aspect.TERRA, 2).with(Aspect.MACHINA, 5).with(Aspect.IGNIS).with(Aspect.VACOUS));
        /////////////////////// - - - - - POLISHED BLACK STONE BRICKS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICKS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.IGNIS).with(Aspect.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS, 2).with(Aspect.VACOUS));
        this.addItemAspect(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, new AspectStack(Aspect.TERRA, 2).with(Aspect.IGNIS, 3).with(Aspect.VACOUS));
        /////////////////////// - - - - - END STONE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.END_STONE, new AspectStack(Aspect.TERRA, 5).with(Aspect.TENEBRAE, 5));
        this.addItemAspect(Items.END_STONE_BRICKS, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        this.addItemAspect(Items.END_STONE_BRICK_STAIRS, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        this.addItemAspect(Items.END_STONE_BRICK_SLAB, new AspectStack(Aspect.TERRA).with(Aspect.TENEBRAE));
        this.addItemAspect(Items.END_STONE_BRICK_WALL, new AspectStack(Aspect.TERRA, 3).with(Aspect.TENEBRAE, 3));
        /////////////////////// - - - - - PURPUR ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.PURPUR_BLOCK, new AspectStack(Aspect.ALIENIS, 3).with(Aspect.SENSUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.PURPUR_STAIRS, new AspectStack(Aspect.ALIENIS, 3).with(Aspect.SENSUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.PURPUR_SLAB, new AspectStack(Aspect.ALIENIS).with(Aspect.SENSUS).with(Aspect.HERBA));
        this.addItemAspect(Items.PURPUR_PILLAR, new AspectStack(Aspect.ALIENIS).with(Aspect.SENSUS).with(Aspect.HERBA));
        /////////////////////// - - - - - IRON ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.IRON_INGOT, new AspectStack(Aspect.METALLUM, 15));
        this.addItemAspect(Items.IRON_NUGGET, new AspectStack(Aspect.METALLUM));
        this.addItemAspect(Items.CHAIN, new AspectStack(Aspect.METALLUM, 16));
        this.addItemAspect(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, new AspectStack(Aspect.METALLUM, 22).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.IRON_TRAPDOOR, new AspectStack(Aspect.METALLUM, 45));
        this.addItemAspect(Items.IRON_DOOR, new AspectStack(Aspect.METALLUM, 22).with(Aspect.VINCULUM, 5).with(Aspect.MACHINA, 5));
        this.addItemAspect(Items.IRON_BARS, new AspectStack(Aspect.METALLUM, 4));
        this.addItemAspect(Items.IRON_BLOCK, new AspectStack(Aspect.METALLUM, 101));
        this.addItemAspect(Items.IRON_HORSE_ARMOR, new AspectStack(Aspect.METALLUM, 15));
        /////////////////////// - - - - - NETHERITE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.ANCIENT_DEBRIS, new AspectStack(Aspect.METALLUM, 25).with(Aspect.TEMPUS, 15).with(Aspect.IGNIS, 33));
        this.addItemAspect(Items.NETHERITE_SCRAP, new AspectStack(Aspect.METALLUM, 15).with(Aspect.TEMPUS, 10).with(Aspect.IGNIS, 25));
        this.addItemAspect(Items.NETHERITE_INGOT, new AspectStack(Aspect.METALLUM, 75).with(Aspect.TEMPUS, 40).with(Aspect.IGNIS, 50)
                .with(Aspect.DESIDERIUM, 30));
        this.addItemAspect(Items.NETHERITE_BLOCK, new AspectStack(Aspect.METALLUM, 525).with(Aspect.TEMPUS, 280).with(Aspect.IGNIS, 350)
                .with(Aspect.DESIDERIUM, 210));
        /////////////////////// - - - - - QUARTZ ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.QUARTZ_BLOCK, new AspectStack(Aspect.VITREUS, 15));
        this.addItemAspect(Items.QUARTZ_STAIRS, new AspectStack(Aspect.VITREUS, 15));
        this.addItemAspect(Items.QUARTZ_SLAB, new AspectStack(Aspect.VITREUS, 3));
        this.addItemAspect(Items.CHISELED_QUARTZ_BLOCK, new AspectStack(Aspect.VITREUS, 7));
        this.addItemAspect(Items.QUARTZ_BRICKS, new AspectStack(Aspect.VITREUS, 10));
        this.addItemAspect(Items.QUARTZ_PILLAR, new AspectStack(Aspect.VITREUS, 11));
        this.addItemAspect(Items.SMOOTH_QUARTZ, new AspectStack(Aspect.VITREUS, 9).with(Aspect.IGNIS));
        this.addItemAspect(Items.SMOOTH_QUARTZ_STAIRS, new AspectStack(Aspect.VITREUS, 9).with(Aspect.IGNIS));
        this.addItemAspect(Items.SMOOTH_QUARTZ_SLAB, new AspectStack(Aspect.VITREUS, 3).with(Aspect.IGNIS));
        /////////////////////// - - - - - COPPER ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 50));
        this.addItemAspect(Items.CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO));
        this.addItemAspect(Items.CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO));
        this.addItemAspect(Items.CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO));
        this.addItemAspect(Items.EXPOSED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS));
        this.addItemAspect(Items.WEATHERED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 3));
        this.addItemAspect(Items.OXIDIZED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 5));
        /////////////////////// - - - - - WAXED COPPER ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WAXED_COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 50).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 3).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_COPPER, new AspectStack(Aspect.METALLUM, 50).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS, new AspectStack(Aspect.METALLUM, 35).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, new AspectStack(Aspect.METALLUM, 7).with(Aspect.ORDO).with(Aspect.TEMPUS, 5).with(Aspect.HERBA, 3));
        /////////////////////// - - - - - BAMBOO ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.BAMBOO, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.BAMBOO_BLOCK, new AspectStack(Aspect.HERBA, 7));
        this.addItemAspect(Items.STRIPPED_BAMBOO_BLOCK, new AspectStack(Aspect.HERBA, 6));
        this.addItemAspect(Items.BAMBOO_PLANKS, new AspectStack(Aspect.HERBA, 2));
        this.addItemAspect(Items.BAMBOO_MOSAIC, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.BAMBOO_STAIRS, new AspectStack(Aspect.HERBA, 7));
        this.addItemAspect(Items.BAMBOO_MOSAIC_STAIRS, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.BAMBOO_SLAB, new AspectStack(Aspect.HERBA, 2));
        this.addItemAspect(Items.BAMBOO_MOSAIC_SLAB, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.BAMBOO_FENCE, new AspectStack(Aspect.HERBA, 4));
        this.addItemAspect(Items.BAMBOO_FENCE_GATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_TRAPDOOR, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_PRESSURE_PLATE, new AspectStack(Aspect.HERBA).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.BAMBOO_BUTTON, new AspectStack(Aspect.HERBA).with(Aspect.MACHINA, 5));
        /////////////////////// - - - - - CRIMSON ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.CRIMSON_STEM, new AspectStack(Aspect.HERBA, 20).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_HYPHAE, new AspectStack(Aspect.HERBA, 22).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_CRIMSON_STEM, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_CRIMSON_HYPHAE, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_PLANKS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_STAIRS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_SLAB, new AspectStack(Aspect.HERBA).with(Aspect.INFERNO));
        this.addItemAspect(Items.CRIMSON_FENCE, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 3));
        this.addItemAspect(Items.CRIMSON_FENCE_GATE, new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 9));
        this.addItemAspect(Items.CRIMSON_TRAPDOOR, new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5).with(Aspect.INFERNO, 7));
        this.addItemAspect(Items.CRIMSON_PRESSURE_PLATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_BUTTON, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.INFERNO));
        /////////////////////// - - - - - WARPED ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WARPED_STEM, new AspectStack(Aspect.HERBA, 20).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.WARPED_HYPHAE, new AspectStack(Aspect.HERBA, 22).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_WARPED_STEM, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_WARPED_HYPHAE, new AspectStack(Aspect.HERBA, 19).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.WARPED_PLANKS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.WARPED_STAIRS, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 5));
        this.addItemAspect(Items.WARPED_SLAB, new AspectStack(Aspect.HERBA).with(Aspect.INFERNO));
        this.addItemAspect(Items.WARPED_FENCE, new AspectStack(Aspect.HERBA, 3).with(Aspect.INFERNO, 3));
        this.addItemAspect(Items.WARPED_FENCE_GATE, new AspectStack(Aspect.HERBA, 7).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.WARPED_DOOR, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.VINCULUM, 5).with(Aspect.INFERNO, 9));
        this.addItemAspect(Items.WARPED_TRAPDOOR, new AspectStack(Aspect.HERBA, 6).with(Aspect.MOTUS, 5).with(Aspect.INFERNO, 7));
        this.addItemAspect(Items.WARPED_PRESSURE_PLATE, new AspectStack(Aspect.HERBA, 4).with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.WARPED_BUTTON, new AspectStack(Aspect.HERBA, 2).with(Aspect.MACHINA, 5).with(Aspect.INFERNO));
        /////////////////////// - - - - - WOOL ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_WOOL, new AspectStack(Aspect.BESTIA, 15).with(Aspect.FABRICO, 5));
        this.addItemAspect(Items.LIGHT_GRAY_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.GRAY_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.BLACK_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.BROWN_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.RED_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.ORANGE_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.YELLOW_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.LIME_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.GREEN_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.CYAN_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.LIGHT_BLUE_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.BLUE_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.PURPLE_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.MAGENTA_WOOL, DEFAULT_WOOL);
        this.addItemAspect(Items.PINK_WOOL, DEFAULT_WOOL);
        /////////////////////// - - - - - CARPET ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_CARPET, new AspectStack(Aspect.BESTIA, 7).with(Aspect.FABRICO, 2));
        this.addItemAspect(Items.LIGHT_GRAY_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.GRAY_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.BLACK_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.BROWN_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.RED_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.ORANGE_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.YELLOW_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.LIME_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.GREEN_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.CYAN_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.LIGHT_BLUE_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.BLUE_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.PURPLE_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.MAGENTA_CARPET, DEFAULT_CARPET);
        this.addItemAspect(Items.PINK_CARPET, DEFAULT_CARPET);
        /////////////////////// - - - - - TERRACOTTA ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.TERRACOTTA, new AspectStack(Aspect.AQUA, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS));
        this.addItemAspect(Items.WHITE_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.LIGHT_GRAY_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.GRAY_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.BLACK_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.BROWN_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.RED_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.ORANGE_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.YELLOW_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.LIME_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.GREEN_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.CYAN_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.LIGHT_BLUE_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.BLUE_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.PURPLE_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.MAGENTA_TERRACOTTA, DEFAULT_TERRACOTE);
        this.addItemAspect(Items.PINK_TERRACOTTA, DEFAULT_TERRACOTE);
        /////////////////////// - - - - - GLAZED TERRACOTA ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.GRAY_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.BLACK_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.BROWN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.RED_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.ORANGE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.YELLOW_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.LIME_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.GREEN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.CYAN_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.LIGHT_BLUE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.BLUE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.PURPLE_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.MAGENTA_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        this.addItemAspect(Items.PINK_GLAZED_TERRACOTTA, DEFAULT_GLAZED_TERRACOTE);
        /////////////////////// - - - - - CONCRETE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.LIGHT_GRAY_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.GRAY_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.BLACK_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.BROWN_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.RED_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.ORANGE_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.YELLOW_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.LIME_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.GREEN_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.CYAN_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.LIGHT_BLUE_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.BLUE_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.PURPLE_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.MAGENTA_CONCRETE, DEFAULT_CONCRETE);
        this.addItemAspect(Items.PINK_CONCRETE, DEFAULT_CONCRETE);
        /////////////////////// - - - - - POWDER CONCRETE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.LIGHT_GRAY_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.GRAY_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.BLACK_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.BROWN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.RED_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.ORANGE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.YELLOW_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.LIME_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.GREEN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.CYAN_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.LIGHT_BLUE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.BLUE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.PURPLE_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.MAGENTA_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        this.addItemAspect(Items.PINK_CONCRETE_POWDER, DEFAULT_CONCRETE_POWDER);
        /////////////////////// - - - - - GLASS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.TINTED_GLASS, DEFAULT_GLASS.with(Aspect.TENEBRAE, 2));
        this.addItemAspect(Items.WHITE_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.LIGHT_GRAY_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.GRAY_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.BLACK_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.BROWN_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.RED_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.ORANGE_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.YELLOW_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.LIME_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.GREEN_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.CYAN_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.LIGHT_BLUE_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.BLUE_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.PURPLE_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.MAGENTA_STAINED_GLASS, DEFAULT_GLASS);
        this.addItemAspect(Items.PINK_STAINED_GLASS, DEFAULT_GLASS);
        /////////////////////// - - - - - GLASS PANE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.WHITE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.LIGHT_GRAY_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.GRAY_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.BLACK_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.BROWN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.RED_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.ORANGE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.YELLOW_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.LIME_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.GREEN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.CYAN_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.LIGHT_BLUE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.BLUE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.PURPLE_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.MAGENTA_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        this.addItemAspect(Items.PINK_STAINED_GLASS_PANE, DEFAULT_GLASS_PANE);
        /////////////////////// - - - - - BED ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_BED, new AspectStack(Aspect.BESTIA, 33).with(Aspect.FABRICO, 11).with(Aspect.HERBA, 6));
        this.addItemAspect(Items.LIGHT_GRAY_BED, DEFAULT_BED);
        this.addItemAspect(Items.GRAY_BED, DEFAULT_BED);
        this.addItemAspect(Items.BLACK_BED, DEFAULT_BED);
        this.addItemAspect(Items.BROWN_BED, DEFAULT_BED);
        this.addItemAspect(Items.RED_BED, DEFAULT_BED);
        this.addItemAspect(Items.ORANGE_BED, DEFAULT_BED);
        this.addItemAspect(Items.YELLOW_BED, DEFAULT_BED);
        this.addItemAspect(Items.LIME_BED, DEFAULT_BED);
        this.addItemAspect(Items.GREEN_BED, DEFAULT_BED);
        this.addItemAspect(Items.CYAN_BED, DEFAULT_BED);
        this.addItemAspect(Items.LIGHT_BLUE_BED, DEFAULT_BED);
        this.addItemAspect(Items.BLUE_BED, DEFAULT_BED);
        this.addItemAspect(Items.PURPLE_BED, DEFAULT_BED);
        this.addItemAspect(Items.MAGENTA_BED, DEFAULT_BED);
        this.addItemAspect(Items.PINK_BED, DEFAULT_BED);
        /////////////////////// - - - - - CANDLE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.CANDLE, new AspectStack(Aspect.BESTIA, 5).with(Aspect.HERBA, 3).with(Aspect.FABRICO));
        this.addItemAspect(Items.WHITE_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.LIGHT_GRAY_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.GRAY_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.BLACK_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.BROWN_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.RED_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.ORANGE_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.YELLOW_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.LIME_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.GREEN_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.CYAN_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.LIGHT_BLUE_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.BLUE_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.PURPLE_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.MAGENTA_CANDLE, DEFAULT_CANDLE);
        this.addItemAspect(Items.PINK_CANDLE, DEFAULT_CANDLE);
        /////////////////////// - - - - - BANNER ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.WHITE_BANNER, new AspectStack(Aspect.BESTIA, 67).with(Aspect.FABRICO, 22));
        this.addItemAspect(Items.LIGHT_GRAY_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.GRAY_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.BLACK_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.BROWN_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.RED_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.ORANGE_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.YELLOW_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.LIME_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.GREEN_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.CYAN_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.LIGHT_BLUE_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.BLUE_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.PURPLE_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.MAGENTA_BANNER, DEFAULT_BANNER);
        this.addItemAspect(Items.PINK_BANNER, DEFAULT_BANNER);
        /////////////////////// - - - - - ORE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.COAL_ORE, new AspectStack(Aspect.POTENTIA, 15).with(Aspect.IGNIS, 15).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_COAL_ORE, new AspectStack(Aspect.POTENTIA, 15).with(Aspect.IGNIS, 15).with(Aspect.TERRA, 8).with(Aspect.ORDO, 2));

        this.addItemAspect(Items.IRON_ORE, new AspectStack(Aspect.METALLUM, 15).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_IRON_ORE, new AspectStack(Aspect.METALLUM, 15).with(Aspect.TERRA, 8).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.RAW_IRON, new AspectStack(Aspect.METALLUM, 15).with(Aspect.PERDITIO, 3));
        this.addItemAspect(Items.RAW_IRON_BLOCK, new AspectStack(Aspect.METALLUM, 90).with(Aspect.PERDITIO, 21));

        this.addItemAspect(Items.COPPER_ORE, new AspectStack(Aspect.METALLUM, 7).with(Aspect.TERRA, 8));
        this.addItemAspect(Items.DEEPSLATE_COPPER_ORE, new AspectStack(Aspect.METALLUM, 7).with(Aspect.TERRA, 8).with(Aspect.ORDO, 2));
        this.addItemAspect(Items.RAW_COPPER, new AspectStack(Aspect.METALLUM, 7).with(Aspect.PERDITIO, 3));
        this.addItemAspect(Items.RAW_COPPER_BLOCK, new AspectStack(Aspect.METALLUM, 49).with(Aspect.PERDITIO, 21));

        this.addItemAspect(Items.GOLD_ORE, new AspectStack(Aspect.METALLUM, 10).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_GOLD_ORE, new AspectStack(Aspect.METALLUM, 10).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 8)
                .with(Aspect.ORDO, 2));
        this.addItemAspect(Items.RAW_GOLD, new AspectStack(Aspect.METALLUM, 10).with(Aspect.DESIDERIUM, 10).with(Aspect.PERDITIO, 5));
        this.addItemAspect(Items.RAW_GOLD_BLOCK, new AspectStack(Aspect.METALLUM, 66).with(Aspect.DESIDERIUM, 66).with(Aspect.PERDITIO, 35));

        this.addItemAspect(Items.REDSTONE_ORE, new AspectStack(Aspect.POTENTIA, 15).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_REDSTONE_ORE, new AspectStack(Aspect.POTENTIA, 15).with(Aspect.TERRA, 8).with(Aspect.ORDO, 2));

        this.addItemAspect(Items.EMERALD_ORE, new AspectStack(Aspect.VITREUS, 15).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_EMERALD_ORE, new AspectStack(Aspect.VITREUS, 15).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 8)
                .with(Aspect.ORDO, 2));

        this.addItemAspect(Items.LAPIS_ORE, new AspectStack(Aspect.SENSUS, 15).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_LAPIS_ORE, new AspectStack(Aspect.SENSUS, 15).with(Aspect.TERRA, 8).with(Aspect.ORDO, 2));

        this.addItemAspect(Items.DIAMOND_ORE, new AspectStack(Aspect.DESIDERIUM, 15).with(Aspect.VITREUS, 15).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_DIAMOND_ORE, new AspectStack(Aspect.DESIDERIUM, 15).with(Aspect.VITREUS, 15).with(Aspect.TERRA, 8)
                .with(Aspect.ORDO, 2));

        this.addItemAspect(Items.NETHER_GOLD_ORE, new AspectStack(Aspect.TERRA, 5).with(Aspect.METALLUM, 5).with(Aspect.DESIDERIUM, 3)
                .with(Aspect.IGNIS, 2));
        this.addItemAspect(Items.NETHER_QUARTZ_ORE, new AspectStack(Aspect.VITREUS, 10).with(Aspect.TERRA, 5));
        /////////////////////// - - - - - AMETHYST ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.AMETHYST_BLOCK, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO));
        this.addItemAspect(Items.BUDDING_AMETHYST, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.SMALL_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 3).with(Aspect.PRAECANTATIO).with(Aspect.VICTUS));
        this.addItemAspect(Items.MEDIUM_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 5).with(Aspect.PRAECANTATIO, 2).with(Aspect.VICTUS));
        this.addItemAspect(Items.LARGE_AMETHYST_BUD, new AspectStack(Aspect.VITREUS, 15).with(Aspect.PRAECANTATIO, 3).with(Aspect.VICTUS));
        this.addItemAspect(Items.AMETHYST_CLUSTER, new AspectStack(Aspect.VITREUS, 20).with(Aspect.PRAECANTATIO, 5).with(Aspect.VICTUS, 2));
        /////////////////////// - - - - - SEEDS ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.PUMPKIN_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addItemAspect(Items.MELON_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addItemAspect(Items.TORCHFLOWER_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addItemAspect(Items.PITCHER_POD, new AspectStack(Aspect.HERBA, 4).with(Aspect.VICTUS));
        this.addItemAspect(Items.WHEAT_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        this.addItemAspect(Items.BEETROOT_SEEDS, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS));
        /////////////////////// - - - - - CORAL ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.TUBE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.BRAIN_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.BUBBLE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.FIRE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.HORN_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.AQUA, 10).with(Aspect.VICTUS, 3));

        this.addItemAspect(Items.DEAD_TUBE_CORAL_BLOCK, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.DEAD_FIRE_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.DEAD_HORN_CORAL_BLOCK, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS, 3));

        this.addItemAspect(Items.TUBE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addItemAspect(Items.BRAIN_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addItemAspect(Items.BUBBLE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addItemAspect(Items.FIRE_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));
        this.addItemAspect(Items.HORN_CORAL, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.VICTUS));

        this.addItemAspect(Items.DEAD_TUBE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_FIRE_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_HORN_CORAL, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));

        this.addItemAspect(Items.TUBE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addItemAspect(Items.BRAIN_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addItemAspect(Items.BUBBLE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addItemAspect(Items.FIRE_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));
        this.addItemAspect(Items.HORN_CORAL_FAN, new AspectStack(Aspect.HERBA, 3).with(Aspect.AQUA, 2).with(Aspect.VICTUS));

        this.addItemAspect(Items.DEAD_TUBE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_FIRE_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.DEAD_HORN_CORAL_FAN, new AspectStack(Aspect.HERBA).with(Aspect.VICTUS));
        /////////////////////// - - - - - BEE ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.BEE_NEST, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 15));
        this.addItemAspect(Items.HONEYCOMB, new AspectStack(Aspect.HERBA, 3));
        this.addItemAspect(Items.HONEYCOMB_BLOCK, new AspectStack(Aspect.HERBA, 10));
        this.addItemAspect(Items.HONEY_BOTTLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.VICTUS, 3).with(Aspect.AQUA, 2).with(Aspect.DESIDERIUM));
        this.addItemAspect(Items.HONEY_BLOCK, new AspectStack(Aspect.HERBA, 30).with(Aspect.VICTUS, 9).with(Aspect.AQUA, 6).with(Aspect.DESIDERIUM, 3));
        /////////////////////// - - - - - SKULK ITEMS - - - - - //////////////////////////
        this.addItemAspect(Items.SCULK, new AspectStack(Aspect.ECHO, 5).with(Aspect.TENEBRAE, 5));
        this.addItemAspect(Items.SCULK_VEIN, new AspectStack(Aspect.ECHO).with(Aspect.TENEBRAE));
        this.addItemAspect(Items.SCULK_CATALYST, new AspectStack(Aspect.ECHO, 15).with(Aspect.TENEBRAE, 15).with(Aspect.VITIUM, 10));
        this.addItemAspect(Items.SCULK_SHRIEKER, new AspectStack(Aspect.ECHO, 25).with(Aspect.TENEBRAE, 17).with(Aspect.VITIUM, 12));
        this.addItemAspect(Items.SCULK_SENSOR, new AspectStack(Aspect.ECHO, 17).with(Aspect.TENEBRAE, 10).with(Aspect.VITIUM, 3));






        this.addItemAspect(Items.GLOW_ITEM_FRAME, new AspectStack(Aspect.HERBA, 6).with(Aspect.BESTIA, 3).with(Aspect.PRAEMUNIO, 3).with(Aspect.SENSUS, 3).with(Aspect.PRAECANTATIO).with(Aspect.AQUA).with(Aspect.BESTIA));
        this.addItemAspect(Items.CHISELED_BOOKSHELF, new AspectStack(Aspect.HERBA, 18));
        this.addItemAspect(Items.GLOW_INK_SAC, new AspectStack(Aspect.SENSUS, 3).with(Aspect.PRAECANTATIO, 2).with(Aspect.AQUA).with(Aspect.BESTIA));
        this.addItemAspect(Items.ENDER_EYE, new AspectStack(Aspect.MOTUS, 11).with(Aspect.SENSUS, 10).with(Aspect.ALIENIS, 7).with(Aspect.PRAECANTATIO, 5).with(Aspect.IGNIS, 3));
        this.addItemAspect(Items.ENDER_CHEST, new AspectStack(Aspect.IGNIS, 33).with(Aspect.TERRA, 30).with(Aspect.TENEBRAE, 30).with(Aspect.VACOUS, 20).with(Aspect.MOTUS, 20)
                .with(Aspect.PERMUTATIO, 10).with(Aspect.SENSUS, 7));
        this.addItemAspect(Items.BOOKSHELF, new AspectStack(Aspect.HERBA, 27).with(Aspect.COGNITIO, 17).with(Aspect.AQUA, 9).with(Aspect.BESTIA, 6).with(Aspect.PRAEMUNIO, 6)
                .with(Aspect.AER, 4));
        this.addItemAspect(Items.PAINTING, new AspectStack(Aspect.BESTIA, 11).with(Aspect.HERBA, 6).with(Aspect.FABRICO, 3));
        this.addItemAspect(Items.ITEM_FRAME, new AspectStack(Aspect.HERBA, 6).with(Aspect.BESTIA, 3).with(Aspect.PRAEMUNIO, 3));
        this.addItemAspect(Items.ARMOR_STAND, new AspectStack(Aspect.HERBA, 4));
        this.addItemAspect(Items.DECORATED_POT, new AspectStack(Aspect.AQUA, 13).with(Aspect.TERRA, 13).with(Aspect.IGNIS, 3).with(Aspect.VACOUS, 7).with(Aspect.TEMPUS));
        this.addItemAspect(Items.FLOWER_POT, new AspectStack(Aspect.AQUA, 11).with(Aspect.TERRA, 11).with(Aspect.HERBA, 5).with(Aspect.IGNIS, 2).with(Aspect.VACOUS, 2));
        this.addItemAspect(Items.LIGHTNING_ROD, new AspectStack(Aspect.METALLUM, 18).with(Aspect.SENSUS, 2));
        this.addItemAspect(Items.BEEHIVE, new AspectStack(Aspect.HERBA, 23));
        this.addItemAspect(Items.SCAFFOLDING, new AspectStack(Aspect.BESTIA, 3).with(Aspect.HERBA).with(Aspect.FABRICO));
        this.addItemAspect(Items.LADDER, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.LODESTONE, new AspectStack(Aspect.METALLUM, 60).with(Aspect.IGNIS, 40).with(Aspect.TEMPUS, 30).with(Aspect.DESIDERIUM, 20)
                .with(Aspect.TERRA, 18).with(Aspect.ORDO, 6));
        this.addItemAspect(Items.CONDUIT, new AspectStack(Aspect.TEMPUS, 120).with(Aspect.AQUA, 80).with(Aspect.PRAECANTATIO, 15));
        this.addItemAspect(Items.HEART_OF_THE_SEA, new AspectStack(Aspect.TEMPUS, 15).with(Aspect.AQUA, 30).with(Aspect.PRAECANTATIO, 20));
        this.addItemAspect(Items.BEACON, new AspectStack(Aspect.PRAECANTATIO, 25).with(Aspect.VITREUS, 18).with(Aspect.AURAM, 17).with(Aspect.ORDO, 15).with(Aspect.IGNIS, 11)
                .with(Aspect.TENEBRAE, 11).with(Aspect.PERMUTATIO, 10));
        this.addItemAspect(Items.NAUTILUS_SHELL, new AspectStack(Aspect.TEMPUS, 25).with(Aspect.AQUA, 20));
        this.addItemAspect(Items.BELL, new AspectStack(Aspect.METALLUM, 50).with(Aspect.DESIDERIUM, 50).with(Aspect.HERBA, 3));
        this.addItemAspect(Items.COPPER_INGOT, new AspectStack(Aspect.METALLUM, 7));
        this.addItemAspect(Items.CAULDRON, new AspectStack(Aspect.METALLUM, 60));
        this.addItemAspect(Items.BREWING_STAND, new AspectStack(Aspect.ALKIMIA, 25).with(Aspect.FABRICO, 15).with(Aspect.IGNIS, 11)
                .with(Aspect.TERRA, 11).with(Aspect.MOTUS, 3).with(Aspect.PERDITIO, 2));
        this.addItemAspect(Items.END_CRYSTAL, new AspectStack(Aspect.VITREUS, 26).with(Aspect.ALKIMIA, 9).with(Aspect.MOTUS, 8)
                .with(Aspect.SENSUS, 7).with(Aspect.SPIRITUS, 7).with(Aspect.ALIENIS, 5).with(Aspect.EXANIMIS, 3));
        this.addItemAspect(Items.ENCHANTING_TABLE, new AspectStack(Aspect.PRAECANTATIO, 25).with(Aspect.VITREUS, 22).with(Aspect.DESIDERIUM, 22)
                .with(Aspect.FABRICO, 15).with(Aspect.TERRA, 15).with(Aspect.IGNIS, 15).with(Aspect.TENEBRAE, 15));
        this.addItemAspect(Items.LEATHER, new AspectStack(Aspect.BESTIA, 5).with(Aspect.PRAEMUNIO, 5));
        this.addItemAspect(Items.BOOK, new AspectStack(Aspect.HERBA, 6).with(Aspect.AQUA, 4).with(Aspect.COGNITIO, 4).with(Aspect.BESTIA, 3)
                .with(Aspect.AER, 2).with(Aspect.PRAEMUNIO, 3));
        this.addItemAspect(Items.DIAMOND, new AspectStack(Aspect.VITREUS, 15).with(Aspect.DESIDERIUM, 15));
        this.addItemAspect(Items.JUKEBOX, new AspectStack(Aspect.SENSUS, 18).with(Aspect.HERBA, 18).with(Aspect.AER, 15)
                .with(Aspect.VITREUS, 11).with(Aspect.DESIDERIUM, 11).with(Aspect.MACHINA, 10));
        this.addItemAspect(Items.NOTE_BLOCK, new AspectStack(Aspect.SENSUS, 18).with(Aspect.HERBA, 18).with(Aspect.AER, 15)
                .with(Aspect.MACHINA, 10).with(Aspect.POTENTIA, 7));
        this.addItemAspect(Items.COMPOSTER, new AspectStack(Aspect.HERBA, 5));
        this.addItemAspect(Items.ANVIL, new AspectStack(Aspect.METALLUM, 272));
        this.addItemAspect(Items.CHIPPED_ANVIL, new AspectStack(Aspect.METALLUM, 150).with(Aspect.PERDITIO, 10));
        this.addItemAspect(Items.DAMAGED_ANVIL, new AspectStack(Aspect.METALLUM, 75).with(Aspect.PERDITIO, 30));
        this.addItemAspect(Items.CHARCOAL, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.IGNIS, 12));
        this.addItemAspect(Items.COAL, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.IGNIS, 10));
        this.addItemAspect(Items.SOUL_CAMPFIRE, new AspectStack(Aspect.HERBA, 35).with(Aspect.POTENTIA, 8).with(Aspect.IGNIS, 7)
                .with(Aspect.SPIRITUS, 4));
        this.addItemAspect(Items.CAMPFIRE, new AspectStack(Aspect.HERBA, 35).with(Aspect.POTENTIA, 8).with(Aspect.IGNIS, 7));
        this.addItemAspect(Items.BLAST_FURNACE, new AspectStack(Aspect.METALLUM, 55).with(Aspect.TERRA, 40).with(Aspect.IGNIS, 10)
                .with(Aspect.PERDITIO, 6));
        this.addItemAspect(Items.SMOKER, new AspectStack(Aspect.TERRA, 30).with(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 6)
                .with(Aspect.HERBA, 50).with(Aspect.SENSUS, 2));
        this.addItemAspect(Items.FURNACE, new AspectStack(Aspect.TERRA, 30).with(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 6));
        this.addItemAspect(Items.LOOM, new AspectStack(Aspect.HERBA, 4).with(Aspect.FABRICO).with(Aspect.BESTIA, 4));
        this.addItemAspect(Items.STICK, new AspectStack(Aspect.HERBA));
        this.addItemAspect(Items.GRINDSTONE, new AspectStack(Aspect.TERRA).with(Aspect.HERBA, 6).with(Aspect.SENSUS, 3));
        this.addItemAspect(Items.SMITHING_TABLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addItemAspect(Items.FLETCHING_TABLE, new AspectStack(Aspect.HERBA, 10).with(Aspect.METALLUM, 15).with(Aspect.INSTRUMENTUM, 12)
                .with(Aspect.TERRA, 5).with(Aspect.IGNIS, 15).with(Aspect.SENSUS, 3));
        this.addItemAspect(Items.CARTOGRAPHY_TABLE, new AspectStack(Aspect.HERBA, 14).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addItemAspect(Items.STONECUTTER, new AspectStack(Aspect.TERRA, 10).with(Aspect.METALLUM, 10).with(Aspect.SENSUS, 3));
        this.addItemAspect(Items.CRAFTING_TABLE, new AspectStack(Aspect.FABRICO, 20).with(Aspect.HERBA, 9));
        this.addItemAspect(Items.PAPER, new AspectStack(Aspect.HERBA, 3).with(Aspect.COGNITIO, 2).with(Aspect.AQUA, 2).with(Aspect.AER));
        this.addItemAspect(Items.SHROOMLIGHT, new AspectStack(Aspect.LUX, 30).with(Aspect.HERBA, 7).with(Aspect.IGNIS, 5).with(Aspect.INFERNO, 3));
        this.addItemAspect(Items.GLOWSTONE, new AspectStack(Aspect.LUX, 30).with(Aspect.SENSUS, 15));
        this.addItemAspect(Items.REDSTONE_LAMP, new AspectStack(Aspect.POTENTIA, 20).with(Aspect.LUX, 22).with(Aspect.SENSUS, 11));
        this.addItemAspect(Items.END_ROD, new AspectStack(Aspect.LUX, 5).with(Aspect.IGNIS));
        this.addItemAspect(Items.SOUL_LANTERN, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.SPIRITUS).with(Aspect.METALLUM));
        this.addItemAspect(Items.LANTERN, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.METALLUM));
        this.addItemAspect(Items.REDSTONE_TORCH, new AspectStack(Aspect.POTENTIA, 7));
        this.addItemAspect(Items.SOUL_TORCH, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS).with(Aspect.SPIRITUS));
        this.addItemAspect(Items.TORCH, new AspectStack(Aspect.LUX, 5).with(Aspect.POTENTIA).with(Aspect.IGNIS));
        this.addItemAspect(Items.OCHRE_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addItemAspect(Items.VERDANT_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addItemAspect(Items.PEARLESCENT_FROGLIGHT, new AspectStack(Aspect.LUX, 15).with(Aspect.VICTUS, 3).with(Aspect.ALKIMIA, 8));
        this.addItemAspect(Items.SLIME_BLOCK, new AspectStack(Aspect.AQUA, 33).with(Aspect.VICTUS, 33).with(Aspect.ALKIMIA, 6));
        this.addItemAspect(Items.HAY_BLOCK, new AspectStack(Aspect.HERBA, 33).with(Aspect.VICTUS, 33));
        this.addItemAspect(Items.JACK_O_LANTERN, new AspectStack(Aspect.HERBA, 20).with(Aspect.LUX, 2));
        this.addItemAspect(Items.CARVED_PUMPKIN, new AspectStack(Aspect.HERBA, 20));
        this.addItemAspect(Items.PUMPKIN, new AspectStack(Aspect.HERBA, 10));
        this.addItemAspect(Items.MELON, new AspectStack(Aspect.HERBA, 10));
        this.addItemAspect(Items.SPONGE, new AspectStack(Aspect.TERRA, 5).with(Aspect.VINCULUM, 5).with(Aspect.VACOUS, 5));
        this.addItemAspect(Items.WET_SPONGE, new AspectStack(Aspect.TERRA, 5).with(Aspect.VINCULUM, 5).with(Aspect.AQUA, 5));
        this.addItemAspect(Items.DRIED_KELP_BLOCK, new AspectStack(Aspect.HERBA, 8).with(Aspect.IGNIS, 7));
        this.addItemAspect(Items.DRIED_KELP, new AspectStack(Aspect.HERBA).with(Aspect.IGNIS));
        this.addItemAspect(Items.KELP, new AspectStack(Aspect.HERBA).with(Aspect.AQUA));
        this.addItemAspect(Items.SEA_PICKLE, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3).with(Aspect.LUX, 2));
        this.addItemAspect(Items.SEAGRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA));
        this.addItemAspect(Items.SWEET_BERRIES, new AspectStack(Aspect.HERBA, 7).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.GLOW_BERRIES, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS, 3).with(Aspect.LUX, 2));
        this.addItemAspect(Items.SNIFFER_EGG, new AspectStack(Aspect.VICTUS, 15).with(Aspect.TEMPUS, 10).with(Aspect.HUMANUS, 3));
        this.addItemAspect(Items.TURTLE_EGG, new AspectStack(Aspect.VICTUS, 5).with(Aspect.HUMANUS));
        this.addItemAspect(Items.FROGSPAWN, new AspectStack(Aspect.VICTUS, 5).with(Aspect.HUMANUS));
        this.addItemAspect(Items.HANGING_ROOTS, new AspectStack(Aspect.HERBA, 3));
        this.addItemAspect(Items.GLOW_LICHEN, new AspectStack(Aspect.HERBA, 5).with(Aspect.LUX));
        this.addItemAspect(Items.SMALL_DRIPLEAF, new AspectStack(Aspect.HERBA, 2));
        this.addItemAspect(Items.BIG_DRIPLEAF, new AspectStack(Aspect.HERBA, 5));
        this.addItemAspect(Items.TWISTING_VINES, new AspectStack(Aspect.HERBA, 5).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.WEEPING_VINES, new AspectStack(Aspect.HERBA, 5).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_ROOTS, new AspectStack(Aspect.HERBA, 3).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.WARPED_ROOTS, new AspectStack(Aspect.HERBA, 3).with(Aspect.IGNIS, 3).with(Aspect.INFERNO, 2));
        this.addItemAspect(Items.DEAD_BUSH, new AspectStack(Aspect.HERBA, 5).with(Aspect.PERDITIO));
        this.addItemAspect(Items.FERN, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addItemAspect(Items.SHORT_GRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addItemAspect(Items.CRIMSON_FUNGUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2).with(Aspect.IGNIS, 5));
        this.addItemAspect(Items.WARPED_FUNGUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2).with(Aspect.IGNIS, 5));
        this.addItemAspect(Items.FLOWERING_AZALEA, new AspectStack(Aspect.HERBA, 15).with(Aspect.VICTUS, 7));
        this.addItemAspect(Items.AZALEA, new AspectStack(Aspect.HERBA, 10).with(Aspect.VICTUS, 3));
        this.addItemAspect(Items.RED_MUSHROOM_BLOCK, new AspectStack(Aspect.HERBA, 15));
        this.addItemAspect(Items.BROWN_MUSHROOM_BLOCK, new AspectStack(Aspect.HERBA, 10));
        this.addItemAspect(Items.MUSHROOM_STEM, new AspectStack(Aspect.HERBA, 10));
        this.addItemAspect(Items.MUDDY_MANGROVE_ROOTS, new AspectStack(Aspect.HERBA, 15).with(Aspect.TERRA, 3).with(Aspect.AQUA, 3));
        this.addItemAspect(Items.MANGROVE_ROOTS, new AspectStack(Aspect.HERBA, 15));
        this.addItemAspect(Items.BONE_BLOCK, new AspectStack(Aspect.SENSUS, 33));
        this.addItemAspect(Items.SOUL_SAND, new AspectStack(Aspect.TERRA, 3).with(Aspect.SPIRITUS, 3)
                .with(Aspect.VINCULUM));
        this.addItemAspect(Items.SOUL_SOIL, new AspectStack(Aspect.TERRA, 3).with(Aspect.SPIRITUS, 3)
                .with(Aspect.VINCULUM).with(Aspect.VOLATUS));
        this.addItemAspect(Items.WARPED_NYLIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.HERBA).with(Aspect.INFERNO));
        this.addItemAspect(Items.CRIMSON_NYLIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2).with(Aspect.HERBA).with(Aspect.INFERNO));
        this.addItemAspect(Items.CRYING_OBSIDIAN, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 5).with(Aspect.TENEBRAE, 5)
                .with(Aspect.PRAECANTATIO));
        this.addItemAspect(Items.MAGMA_BLOCK, new AspectStack(Aspect.IGNIS, 10).with(Aspect.TERRA, 5).with(Aspect.INFERNO));
        this.addItemAspect(Items.POINTED_DRIPSTONE, new AspectStack(Aspect.TERRA, 3).with(Aspect.ORDO).with(Aspect.AQUA));
        this.addItemAspect(Items.DRIPSTONE_BLOCK, new AspectStack(Aspect.TERRA, 10).with(Aspect.ORDO, 5).with(Aspect.AQUA, 3));
        this.addItemAspect(Items.CALCITE, new AspectStack(Aspect.TERRA, 20).with(Aspect.ORDO, 5));
        this.addItemAspect(Items.MOSS_CARPET, new AspectStack(Aspect.TERRA).with(Aspect.HERBA).with(Aspect.VICTUS));
        this.addItemAspect(Items.MOSS_BLOCK, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 5).with(Aspect.VICTUS, 5));
        this.addItemAspect(Items.SNOW, new AspectStack(Aspect.GELUM));
        this.addItemAspect(Items.SNOW_BLOCK, new AspectStack(Aspect.GELUM, 3));
        this.addItemAspect(Items.BLUE_ICE, new AspectStack(Aspect.GELUM, 30));
        this.addItemAspect(Items.PACKED_ICE, new AspectStack(Aspect.GELUM, 25));
        this.addItemAspect(Items.CLAY, new AspectStack(Aspect.TERRA, 15).with(Aspect.AQUA, 15));
        this.addItemAspect(Items.ROOTED_DIRT, new AspectStack(Aspect.TERRA, 4));
        this.addItemAspect(Items.COARSE_DIRT, new AspectStack(Aspect.TERRA, 3));
        this.addItemAspect(Items.MYCELIUM, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA).with(Aspect.VITIUM));
        this.addItemAspect(Items.STRING, new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO));
        this.addItemAspect(Items.DIAMOND_BLOCK, new AspectStack(Aspect.VITREUS, 101).with(Aspect.DESIDERIUM, 10));
        this.addItemAspect(Items.LAPIS_BLOCK, new AspectStack(Aspect.SENSUS, 33).with(Aspect.TERRA, 13).with(Aspect.DESIDERIUM, 13));
        this.addItemAspect(Items.EMERALD_BLOCK, new AspectStack(Aspect.VITREUS, 101).with(Aspect.DESIDERIUM, 67));
        this.addItemAspect(Items.REDSTONE_BLOCK, new AspectStack(Aspect.DESIDERIUM, 67).with(Aspect.POTENTIA, 67));
        this.addItemAspect(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, new AspectStack(Aspect.METALLUM, 15).with(Aspect.DESIDERIUM, 15)
                .with(Aspect.MACHINA, 5).with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.GOLD_BLOCK, new AspectStack(Aspect.METALLUM, 67).with(Aspect.DESIDERIUM, 67));
        this.addItemAspect(Items.COAL_BLOCK, new AspectStack(Aspect.POTENTIA, 67).with(Aspect.IGNIS, 67));
        this.addItemAspect(Items.NETHERRACK, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2));
        this.addItemAspect(Items.PRISMARINE_SHARD, new AspectStack(Aspect.AQUA, 5)
                .with(Aspect.TERRA, 5));
        this.addItemAspect(Items.PRISMARINE_CRYSTALS, new AspectStack(Aspect.AQUA, 5)
                .with(Aspect.LUX, 5).with(Aspect.VITREUS, 5));
        this.addItemAspect(Items.SEA_LANTERN, new AspectStack(Aspect.AQUA, 33).with(Aspect.VITREUS, 18).with(Aspect.LUX, 18)
                .with(Aspect.TERRA, 15));
        this.addItemAspect(Items.TUFF, new AspectStack(Aspect.TERRA, 5).with(Aspect.ORDO));
        this.addItemAspect(Items.CHEST, new AspectStack(Aspect.HERBA, 18));
        this.addItemAspect(Items.GRASS_BLOCK, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 2));
        this.addItemAspect(Items.DIRT, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA, 2));
        this.addItemAspect(Items.PODZOL, new AspectStack(Aspect.TERRA, 5).with(Aspect.HERBA));
        this.addItemAspect(Items.BEDROCK, new AspectStack(Aspect.VACOUS, 25).with(Aspect.PERDITIO, 25)
                .with(Aspect.TERRA, 25).with(Aspect.TENEBRAE, 25));
        this.addItemAspect(Items.SAND, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.RED_SAND, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.GRAVEL, new AspectStack(Aspect.PERDITIO, 5).with(Aspect.TERRA, 2));
        this.addItemAspect(Items.COBWEB, new AspectStack(Aspect.VINCULUM, 5).with(Aspect.BESTIA));
        this.addItemAspect(Items.BROWN_MUSHROOM, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2)
                .with(Aspect.TERRA, 2));
        this.addItemAspect(Items.RED_MUSHROOM, new AspectStack(Aspect.HERBA, 5).with(Aspect.TENEBRAE, 2)
                .with(Aspect.IGNIS, 2));
        this.addItemAspect(Items.SUGAR_CANE, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 3)
                .with(Aspect.AER, 2));
        this.addItemAspect(Items.OBSIDIAN, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 5)
                .with(Aspect.TENEBRAE, 5));
        this.addItemAspect(Items.CHORUS_PLANT, new AspectStack(Aspect.ALIENIS, 5).with(Aspect.HERBA, 5));
        this.addItemAspect(Items.CHORUS_FLOWER, new AspectStack(Aspect.ALIENIS, 5).with(Aspect.HERBA, 5)
                .with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.ICE, new AspectStack(Aspect.GELUM, 20));
        this.addItemAspect(Items.CACTUS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA, 5)
                .with(Aspect.AVERSIO));
        this.addItemAspect(Items.NETHERRACK, new AspectStack(Aspect.TERRA, 5).with(Aspect.IGNIS, 2));
        this.addItemAspect(Items.VINE, new AspectStack(Aspect.HERBA, 5));
        this.addItemAspect(Items.LILY_PAD, new AspectStack(Aspect.HERBA, 5).with(Aspect.AQUA));
        this.addItemAspect(Items.END_PORTAL_FRAME, new AspectStack(Aspect.POTENTIA, 10).with(Aspect.MOTUS, 10)
                .with(Aspect.PRAECANTATIO, 5).with(Aspect.ALIENIS, 10));
        this.addItemAspect(Items.DRAGON_EGG, new AspectStack(Aspect.ALIENIS, 15).with(Aspect.MOTUS, 15)
                .with(Aspect.TENEBRAE, 15).with(Aspect.PRAECANTATIO, 5).with(Aspect.BESTIA, 15));
        this.addItemAspect(Items.DIRT_PATH, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 2)
                .with(Aspect.ORDO, 2));
        this.addItemAspect(Items.SUNFLOWER, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addItemAspect(Items.LILAC, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addItemAspect(Items.ROSE_BUSH, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addItemAspect(Items.PEONY, new AspectStack(Aspect.HERBA, 5).with(Aspect.SENSUS, 5)
                .with(Aspect.AER).with(Aspect.VICTUS));
        this.addItemAspect(Items.TALL_GRASS, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addItemAspect(Items.LARGE_FERN, new AspectStack(Aspect.HERBA, 5).with(Aspect.AER));
        this.addItemAspect(Items.RED_CONCRETE, new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2)
                .with(Aspect.AQUA).with(Aspect.ORDO));
        this.addItemAspect(Items.BLACK_CONCRETE, new AspectStack(Aspect.TERRA, 3).with(Aspect.PERDITIO, 2)
                .with(Aspect.AQUA).with(Aspect.ORDO));
        this.addItemAspect(Items.SADDLE, new AspectStack(Aspect.BESTIA, 10).with(Aspect.MOTUS, 10)
                .with(Aspect.ORDO, 5));
        this.addItemAspect(Items.ELYTRA, new AspectStack(Aspect.MOTUS, 15).with(Aspect.VOLATUS, 20));
        this.addItemAspect(Items.APPLE, new AspectStack(Aspect.HERBA, 5).with(Aspect.VICTUS, 5));
        this.addItemAspect(Items.GOLD_INGOT, new AspectStack(Aspect.METALLUM, 15).with(Aspect.DESIDERIUM, 15));
        this.addItemAspect(Items.STRING, new AspectStack(Aspect.BESTIA, 5).with(Aspect.FABRICO));
        this.addItemAspect(Items.FEATHER, new AspectStack(Aspect.AER, 5).with(Aspect.VOLATUS, 5));
        this.addItemAspect(Items.GUNPOWDER, new AspectStack(Aspect.IGNIS, 10).with(Aspect.PERDITIO, 10)
                .with(Aspect.ALKIMIA, 5));
        this.addItemAspect(Items.CHAINMAIL_HELMET, new AspectStack(Aspect.METALLUM, 42).with(Aspect.PRAEMUNIO, 8));
        this.addItemAspect(Items.CHAINMAIL_CHESTPLATE, new AspectStack(Aspect.METALLUM, 67).with(Aspect.PRAEMUNIO, 20));
        this.addItemAspect(Items.CHAINMAIL_LEGGINGS, new AspectStack(Aspect.METALLUM, 58).with(Aspect.PRAEMUNIO, 16));
        this.addItemAspect(Items.CHAINMAIL_BOOTS, new AspectStack(Aspect.METALLUM, 33).with(Aspect.PRAEMUNIO, 4));
        this.addItemAspect(Items.FLINT, new AspectStack(Aspect.METALLUM, 11).with(Aspect.IGNIS, 10)
                .with(Aspect.TERRA, 3).with(Aspect.INSTRUMENTUM, 8));
        this.addItemAspect(Items.PORKCHOP, new AspectStack(Aspect.BESTIA, 5).with(Aspect.VICTUS, 5)
                .with(Aspect.TERRA, 5));
        this.addItemAspect(Items.ENCHANTED_GOLDEN_APPLE, new AspectStack(Aspect.VICTUS, 15)
                .with(Aspect.PRAEMUNIO, 15));
        this.addItemAspect(Items.WATER_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.AQUA, 20)
                .with(Aspect.VACOUS, 5));
        this.addItemAspect(Items.LAVA_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.IGNIS, 15)
                .with(Aspect.VACOUS, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.SNOWBALL, new AspectStack(Aspect.GELUM));
        this.addItemAspect(Items.MILK_BUCKET, new AspectStack(Aspect.METALLUM, 33).with(Aspect.AQUA, 5)
                .with(Aspect.VACOUS, 5).with(Aspect.VICTUS, 10).with(Aspect.BESTIA, 5));
        this.addItemAspect(Items.CLAY_BALL, new AspectStack(Aspect.TERRA, 5).with(Aspect.AQUA, 5));
        this.addItemAspect(Items.EGG, new AspectStack(Aspect.VICTUS, 5).with(Aspect.BESTIA, 5));
        this.addItemAspect(Items.GLOWSTONE_DUST, new AspectStack(Aspect.LUX, 10).with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.INK_SAC, new AspectStack(Aspect.SENSUS, 5).with(Aspect.AQUA, 2)
                .with(Aspect.BESTIA, 2));
        this.addItemAspect(Items.COCOA_BEANS, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.POTENTIA, 2).with(Aspect.DESIDERIUM, 2));
        this.addItemAspect(Items.ORANGE_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addItemAspect(Items.MAGENTA_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addItemAspect(Items.LIGHT_BLUE_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addItemAspect(Items.YELLOW_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addItemAspect(Items.PINK_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addItemAspect(Items.LIGHT_GRAY_DYE, new AspectStack(Aspect.SENSUS, 5));
        this.addItemAspect(Items.RED_DYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA));
        this.addItemAspect(Items.BONE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.HERBA).with(Aspect.VICTUS, 2).with(Aspect.MORTUS));
        this.addItemAspect(Items.SUGAR, new AspectStack(Aspect.HERBA, 3)
                .with(Aspect.AQUA, 2).with(Aspect.AER).with(Aspect.POTENTIA)
                .with(Aspect.DESIDERIUM));
        this.addItemAspect(Items.BEEF, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.CHICKEN, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.AER, 5));
        this.addItemAspect(Items.ROTTEN_FLESH, new AspectStack(Aspect.VICTUS, 5)
                .with(Aspect.PERDITIO, 5).with(Aspect.HUMANUS, 5));
        this.addItemAspect(Items.ENDER_PEARL, new AspectStack(Aspect.MOTUS, 15)
                .with(Aspect.ALIENIS, 10));
        this.addItemAspect(Items.BLAZE_ROD, new AspectStack(Aspect.IGNIS, 15)
                .with(Aspect.MOTUS, 10));
        this.addItemAspect(Items.GHAST_TEAR, new AspectStack(Aspect.IGNIS, 6)
                .with(Aspect.PERDITIO, 2).with(Aspect.POTENTIA, 2).with(Aspect.ALKIMIA, 2));
        this.addItemAspect(Items.NETHER_WART, new AspectStack(Aspect.ALKIMIA, 3)
                .with(Aspect.HERBA).with(Aspect.VITIUM, 2));
        this.addItemAspect(Items.SPIDER_EYE, new AspectStack(Aspect.SENSUS, 5)
                .with(Aspect.BESTIA, 5).with(Aspect.MORTUS, 5));
        this.addItemAspect(Items.EXPERIENCE_BOTTLE, new AspectStack(Aspect.COGNITIO, 20));
        this.addItemAspect(Items.WRITTEN_BOOK, new AspectStack(Aspect.COGNITIO, 3)
                .with(Aspect.AQUA, 4).with(Aspect.HERBA, 4).with(Aspect.AER, 5)
                .with(Aspect.BESTIA, 3).with(Aspect.SENSUS, 3).with(Aspect.VOLATUS, 3));
        this.addItemAspect(Items.CARROT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.SENSUS, 5));
        this.addItemAspect(Items.POTATO, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.POISONOUS_POTATO, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.MORTUS, 5));
        this.addItemAspect(Items.SKELETON_SKULL, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.EXANIMIS, 10));
        this.addItemAspect(Items.WITHER_SKELETON_SKULL, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.EXANIMIS, 10));
        this.addItemAspect(Items.PLAYER_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.HUMANUS, 10));
        this.addItemAspect(Items.ZOMBIE_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.HUMANUS, 10));
        this.addItemAspect(Items.CREEPER_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.PERDITIO, 5).with(Aspect.IGNIS, 5));
        this.addItemAspect(Items.DRAGON_HEAD, new AspectStack(Aspect.MORTUS, 10)
                .with(Aspect.SPIRITUS, 10).with(Aspect.TENEBRAE, 10).with(Aspect.IGNIS, 10));
        this.addItemAspect(Items.NETHER_STAR, new AspectStack(Aspect.ORDO, 20)
                .with(Aspect.ALIENIS, 20).with(Aspect.PRAECANTATIO, 20).with(Aspect.AURAM, 20));
        this.addItemAspect(Items.RABBIT, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.RABBIT_STEW, new AspectStack(Aspect.HERBA, 12)
                .with(Aspect.VICTUS, 11).with(Aspect.BESTIA, 3).with(Aspect.SENSUS, 3)
                .with(Aspect.VACOUS, 3).with(Aspect.IGNIS).with(Aspect.TENEBRAE));
        this.addItemAspect(Items.RABBIT_FOOT, new AspectStack(Aspect.MOTUS, 10)
                .with(Aspect.BESTIA, 5).with(Aspect.PRAEMUNIO, 5).with(Aspect.ALKIMIA, 5));
        this.addItemAspect(Items.RABBIT_HIDE, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.PRAEMUNIO, 2));
        this.addItemAspect(Items.GOLDEN_HORSE_ARMOR, new AspectStack(Aspect.METALLUM, 10)
                .with(Aspect.PRAEMUNIO, 15).with(Aspect.BESTIA, 5));
        this.addItemAspect(Items.DIAMOND_HORSE_ARMOR, new AspectStack(Aspect.PRAEMUNIO, 20)
                .with(Aspect.BESTIA, 5).with(Aspect.VITREUS, 15));
        this.addItemAspect(Items.NAME_TAG, new AspectStack(Aspect.BESTIA, 10)
                .with(Aspect.COGNITIO, 10));
        this.addItemAspect(Items.MUTTON, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.CHORUS_FRUIT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.SENSUS, 5).with(Aspect.ALIENIS, 5));
        this.addItemAspect(Items.BEETROOT, new AspectStack(Aspect.HERBA, 5)
                .with(Aspect.VICTUS, 5).with(Aspect.DESIDERIUM, 5));
        this.addItemAspect(Items.DRAGON_BREATH, new AspectStack(Aspect.IGNIS, 10)
                .with(Aspect.PERDITIO, 10).with(Aspect.TENEBRAE, 10).with(Aspect.ALKIMIA, 10));
        this.addItemAspect(Items.TOTEM_OF_UNDYING, new AspectStack(Aspect.VICTUS, 25)
                .with(Aspect.ORDO, 10).with(Aspect.PERDITIO, 10).with(Aspect.EXANIMIS, 10));
        this.addItemAspect(Items.SHULKER_SHELL, new AspectStack(Aspect.BESTIA, 5)
                .with(Aspect.VACOUS, 5).with(Aspect.ALIENIS, 5).with(Aspect.PRAEMUNIO, 10));
        this.addItemAspect(Items.MUSIC_DISC_13, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.AQUA, 5));
        this.addItemAspect(Items.MUSIC_DISC_CAT, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.BESTIA, 5));
        this.addItemAspect(Items.MUSIC_DISC_BLOCKS, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.INSTRUMENTUM, 5));
        this.addItemAspect(Items.MUSIC_DISC_CHIRP, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.TERRA, 5));
        this.addItemAspect(Items.MUSIC_DISC_FAR, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.ALIENIS, 5));
        this.addItemAspect(Items.MUSIC_DISC_MALL, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.HUMANUS, 5));
        this.addItemAspect(Items.MUSIC_DISC_MELLOHI, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.FABRICO, 5));
        this.addItemAspect(Items.MUSIC_DISC_STAL, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.TENEBRAE, 5));
        this.addItemAspect(Items.MUSIC_DISC_STRAD, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.POTENTIA, 5));
        this.addItemAspect(Items.MUSIC_DISC_WARD, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.VICTUS, 5));
        this.addItemAspect(Items.MUSIC_DISC_11, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10));
        this.addItemAspect(Items.MUSIC_DISC_WAIT, new AspectStack(Aspect.SENSUS, 15)
                .with(Aspect.AER, 5).with(Aspect.DESIDERIUM, 10).with(Aspect.VINCULUM, 5));
        this.addItemAspect(ThaumcraftItems.THAUMONOMICON, new AspectStack(Aspect.AER)
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
            this.addItemAspect(ThaumcraftItems.CRYSTAL, CrystalHelper.buildType(aspect), new AspectStack(aspect));
        }
    }
}