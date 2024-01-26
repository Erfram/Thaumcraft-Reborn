package llama.thaumcraft.config;

import llama.thaumcraft.Aspects;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.Collections;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AspectRegistry {
    public static final Map<Map<Item, NbtCompound>, Map<Aspects, Integer>> ITEMS = new LinkedHashMap<>();

    private void addItemAspect(Item item, NbtCompound nbtCompound, ItemAspects itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, nbtCompound);
        }}, itemAspects.getAspects());
    }

    private void addItemAspect(Item item, ItemAspects itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(item, new NbtCompound());
        }}, itemAspects.getAspects());
    }

    public static Map<Aspects, Integer> getAspectsByItem(Item item) {
        Map<Item, NbtCompound> key = new ConcurrentHashMap<>(){{
            put(item, new NbtCompound());
        }};

        return ITEMS.get(key);
    }

    public static List<NbtCompound> getNbtCompoundsByItem(Item item) {
        List<NbtCompound> nbtList = new ArrayList<>();

        for (Map.Entry<Map<Item, NbtCompound>, Map<Aspects, Integer>> entry : ITEMS.entrySet()) {

            Map<Item, NbtCompound> itemMap = entry.getKey();

            if (itemMap.containsKey(item)) {
                nbtList.add(itemMap.get(item));
            }
        }

        return nbtList;
    }

    public static Map<Aspects, Integer> getAspectsByItemStack(ItemStack stack) {
        List<NbtCompound> nbts = getNbtCompoundsByItem(stack.getItem());

        if(nbts.equals(new ArrayList<NbtCompound>())) {
            return null;
        }

        if(stack.getNbt() == null) {
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

    public void registryItems() {
        /////////////////////// - - - - - SAPLINGS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.SPRUCE_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.BIRCH_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.JUNGLE_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.ACACIA_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.DARK_OAK_SAPLING, Thaumcraft.saplings);
        this.addItemAspect(Items.MANGROVE_PROPAGULE, Thaumcraft.saplings);
        this.addItemAspect(Items.CHERRY_SAPLING, Thaumcraft.saplings);
        /////////////////////// - - - - - LOGS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.SPRUCE_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.BIRCH_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.JUNGLE_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.ACACIA_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.DARK_OAK_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.MANGROVE_LOG, Thaumcraft.logs);
        this.addItemAspect(Items.CHERRY_LOG, Thaumcraft.logs);
        /////////////////////// - - - - - STRIPPED LOGS - - - - - ////////////////////////// 
        this.addItemAspect(Items.STRIPPED_OAK_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_SPRUCE_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_BIRCH_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_JUNGLE_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_ACACIA_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_DARK_OAK_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_MANGROVE_LOG, Thaumcraft.stripped_logs);
        this.addItemAspect(Items.STRIPPED_CHERRY_LOG, Thaumcraft.stripped_logs);
        /////////////////////// - - - - - WOODS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.SPRUCE_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.BIRCH_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.JUNGLE_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.ACACIA_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.DARK_OAK_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.MANGROVE_WOOD, Thaumcraft.woods);
        this.addItemAspect(Items.CHERRY_WOOD, Thaumcraft.woods);
        /////////////////////// - - - - - STRIPPED WOODS - - - - - ////////////////////////// 
        this.addItemAspect(Items.STRIPPED_OAK_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_SPRUCE_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_BIRCH_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_JUNGLE_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_ACACIA_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_DARK_OAK_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_MANGROVE_WOOD, Thaumcraft.stripped_woods);
        this.addItemAspect(Items.STRIPPED_CHERRY_WOOD, Thaumcraft.stripped_woods);
        /////////////////////// - - - - - PLANKS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.SPRUCE_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.BIRCH_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.JUNGLE_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.ACACIA_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.DARK_OAK_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.MANGROVE_PLANKS, Thaumcraft.planks);
        this.addItemAspect(Items.CHERRY_PLANKS, Thaumcraft.planks);
        /////////////////////// - - - - - STAIRS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.SPRUCE_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.BIRCH_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.JUNGLE_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.ACACIA_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.DARK_OAK_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.MANGROVE_STAIRS, Thaumcraft.stairs);
        this.addItemAspect(Items.CHERRY_STAIRS, Thaumcraft.stairs);
        /////////////////////// - - - - - SLABS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.SPRUCE_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.BIRCH_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.JUNGLE_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.ACACIA_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.DARK_OAK_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.MANGROVE_SLAB, Thaumcraft.slabs);
        this.addItemAspect(Items.CHERRY_SLAB, Thaumcraft.slabs);
        /////////////////////// - - - - - FENCE - - - - -
        this.addItemAspect(Items.OAK_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.SPRUCE_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.BIRCH_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.JUNGLE_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.ACACIA_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.DARK_OAK_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.MANGROVE_FENCE, Thaumcraft.fences);
        this.addItemAspect(Items.CHERRY_FENCE, Thaumcraft.fences);
        /////////////////////// - - - - - FENCE GATE - - - - -
        this.addItemAspect(Items.OAK_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.SPRUCE_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.BIRCH_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.JUNGLE_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.ACACIA_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.DARK_OAK_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.MANGROVE_FENCE_GATE, Thaumcraft.fences_gates);
        this.addItemAspect(Items.CHERRY_FENCE_GATE, Thaumcraft.fences_gates);
        /////////////////////// - - - - - DOORS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.SPRUCE_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.BIRCH_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.JUNGLE_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.ACACIA_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.DARK_OAK_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.MANGROVE_DOOR, Thaumcraft.doors);
        this.addItemAspect(Items.CHERRY_DOOR, Thaumcraft.doors);
        /////////////////////// - - - - - TRAPDOORS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.SPRUCE_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.BIRCH_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.JUNGLE_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.ACACIA_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.DARK_OAK_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.MANGROVE_TRAPDOOR, Thaumcraft.trapdoors);
        this.addItemAspect(Items.CHERRY_TRAPDOOR, Thaumcraft.trapdoors);
        /////////////////////// - - - - - PLATE - - - - -
        this.addItemAspect(Items.OAK_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.SPRUCE_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.BIRCH_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.JUNGLE_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.ACACIA_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.DARK_OAK_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.MANGROVE_PRESSURE_PLATE, Thaumcraft.plates);
        this.addItemAspect(Items.CHERRY_PRESSURE_PLATE, Thaumcraft.plates);
        /////////////////////// - - - - - BUTTONS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.SPRUCE_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.BIRCH_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.JUNGLE_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.ACACIA_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.DARK_OAK_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.MANGROVE_BUTTON, Thaumcraft.buttons);
        this.addItemAspect(Items.CHERRY_BUTTON, Thaumcraft.buttons);
        /////////////////////// - - - - - SIGNS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.SPRUCE_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.BIRCH_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.JUNGLE_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.ACACIA_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.DARK_OAK_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.MANGROVE_SIGN, Thaumcraft.signs);
        this.addItemAspect(Items.CHERRY_SIGN, Thaumcraft.signs);
        /////////////////////// - - - - - HANGING SIGNS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.SPRUCE_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.BIRCH_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.JUNGLE_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.ACACIA_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.DARK_OAK_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.MANGROVE_HANGING_SIGN, Thaumcraft.hanging_signs);
        this.addItemAspect(Items.CHERRY_HANGING_SIGN, Thaumcraft.hanging_signs);
        /////////////////////// - - - - - LEAVES - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.SPRUCE_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.BIRCH_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.JUNGLE_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.ACACIA_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.DARK_OAK_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.MANGROVE_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.CHERRY_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.AZALEA_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.FLOWERING_AZALEA_LEAVES, Thaumcraft.leaves);
        this.addItemAspect(Items.NETHER_WART_BLOCK, new ItemAspects(Aspects.HERBA, 5).add(Aspects.INFERNO, 3).add(Aspects.IGNIS, 5));
        this.addItemAspect(Items.WARPED_WART_BLOCK, new ItemAspects(Aspects.HERBA, 5).add(Aspects.INFERNO, 3).add(Aspects.IGNIS, 5));
        /////////////////////// - - - - - BOATS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.SPRUCE_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.BIRCH_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.JUNGLE_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.ACACIA_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.DARK_OAK_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.MANGROVE_BOAT, Thaumcraft.boats);
        this.addItemAspect(Items.CHERRY_BOAT, Thaumcraft.boats);
        /////////////////////// - - - - - CHEST BOATS - - - - - ////////////////////////// 
        this.addItemAspect(Items.OAK_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.SPRUCE_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.BIRCH_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.JUNGLE_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.ACACIA_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.DARK_OAK_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.MANGROVE_CHEST_BOAT, Thaumcraft.chest_boats);
        this.addItemAspect(Items.CHERRY_CHEST_BOAT, Thaumcraft.chest_boats);
        /////////////////////// - - - - - FLOWERS - - - - - ////////////////////////// 
        this.addItemAspect(Items.DANDELION, Thaumcraft.flowers);
        this.addItemAspect(Items.POPPY, Thaumcraft.flowers);
        this.addItemAspect(Items.BLUE_ORCHID, Thaumcraft.flowers);
        this.addItemAspect(Items.ALLIUM, Thaumcraft.flowers);
        this.addItemAspect(Items.AZURE_BLUET, Thaumcraft.flowers);
        this.addItemAspect(Items.RED_TULIP, Thaumcraft.flowers);
        this.addItemAspect(Items.ORANGE_TULIP, Thaumcraft.flowers);
        this.addItemAspect(Items.WHITE_TULIP, Thaumcraft.flowers);
        this.addItemAspect(Items.PINK_TULIP, Thaumcraft.flowers);
        this.addItemAspect(Items.OXEYE_DAISY, Thaumcraft.flowers);
        this.addItemAspect(Items.CORNFLOWER, Thaumcraft.flowers);
        this.addItemAspect(Items.LILY_OF_THE_VALLEY, Thaumcraft.flowers);
        this.addItemAspect(Items.PINK_PETALS, Thaumcraft.flowers);
        this.addItemAspect(Items.TORCHFLOWER, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5).add(Aspects.VICTUS).add(Aspects.LUX));
        this.addItemAspect(Items.WITHER_ROSE, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5).add(Aspects.VICTUS).add(Aspects.MORTUS, 5));
        this.addItemAspect(Items.SPORE_BLOSSOM, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5).add(Aspects.VICTUS).add(Aspects.TEMPUS, 5));
        this.addItemAspect(Items.PITCHER_PLANT, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5).add(Aspects.VICTUS).add(Aspects.TEMPUS, 15));
        /////////////////////// - - - - - SHULKER_BOXS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.WHITE_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.LIGHT_GRAY_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.GRAY_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.BLACK_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.BROWN_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.RED_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.ORANGE_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.YELLOW_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.LIME_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.GREEN_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.CYAN_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.LIGHT_BLUE_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.BLUE_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.PURPLE_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.MAGENTA_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        this.addItemAspect(Items.PINK_SHULKER_BOX, Thaumcraft.shulkerBoxs);
        /////////////////////// - - - - - FISHS - - - - - ////////////////////////// 
        this.addItemAspect(Items.COD, Thaumcraft.fishs);
        this.addItemAspect(Items.SALMON, Thaumcraft.fishs);
        this.addItemAspect(Items.TROPICAL_FISH, Thaumcraft.fishs);
        this.addItemAspect(Items.PUFFERFISH, Thaumcraft.fishs);
        /////////////////////// - - - - - STONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.STONE, new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect(Items.STONE_STAIRS, new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect(Items.STONE_SLAB, new ItemAspects(Aspects.TERRA));
        this.addItemAspect(Items.STONE_PRESSURE_PLATE, new ItemAspects(Aspects.TERRA, 7));
        this.addItemAspect(Items.STONE_BUTTON, new ItemAspects(Aspects.TERRA, 3).add(Aspects.MACHINA, 5));
        /////////////////////// - - - - - COBBLESTONES ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.COBBLESTONE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO));
        this.addItemAspect(Items.COBBLESTONE_STAIRS, new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO));
        this.addItemAspect(Items.COBBLESTONE_SLAB, new ItemAspects(Aspects.TERRA));
        this.addItemAspect(Items.COBBLESTONE_WALL, new ItemAspects(Aspects.TERRA, 3));
        /////////////////////// - - - - - MOSSY COBBLESTONES ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.MOSSY_COBBLESTONE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 3).add(Aspects.PERDITIO));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_STAIRS, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 3).add(Aspects.PERDITIO));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.HERBA));
        this.addItemAspect(Items.MOSSY_COBBLESTONE_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA, 2));
        /////////////////////// - - - - - SMOOTH ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SMOOTH_STONE, new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect(Items.SMOOTH_STONE_SLAB, new ItemAspects(Aspects.TERRA));
        /////////////////////// - - - - - STONE BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.STONE_BRICKS, new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect(Items.CRACKED_STONE_BRICKS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS));
        this.addItemAspect(Items.STONE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect(Items.STONE_BRICK_SLAB, new ItemAspects(Aspects.TERRA));
        this.addItemAspect(Items.STONE_BRICK_WALL, new ItemAspects(Aspects.TERRA, 2));
        this.addItemAspect(Items.CHISELED_STONE_BRICKS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));
        /////////////////////// - - - - - MOSSY STONE BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.MOSSY_STONE_BRICKS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.HERBA));
        this.addItemAspect(Items.MOSSY_STONE_BRICK_WALL, new ItemAspects(Aspects.TERRA).add(Aspects.HERBA, 2));
        /////////////////////// - - - - - BREEDS ITEMS - - - - - ////////////////////////// 
        //GRANITE
        this.addItemAspect(Items.GRANITE, Thaumcraft.breeds);
        this.addItemAspect(Items.GRANITE_STAIRS, Thaumcraft.breeds_stairs);
        this.addItemAspect(Items.GRANITE_SLAB, Thaumcraft.breeds_slab);
        this.addItemAspect(Items.GRANITE_WALL, Thaumcraft.breeds_wall);
        this.addItemAspect(Items.POLISHED_GRANITE, Thaumcraft.polished_breeds);
        this.addItemAspect(Items.POLISHED_GRANITE_STAIRS, Thaumcraft.polished_breeds_stairs);
        this.addItemAspect(Items.POLISHED_GRANITE_SLAB, Thaumcraft.polished_breeds_slab);
        //DIORITE
        this.addItemAspect(Items.DIORITE, Thaumcraft.breeds);
        this.addItemAspect(Items.DIORITE_STAIRS, Thaumcraft.breeds_stairs);
        this.addItemAspect(Items.DIORITE_SLAB, Thaumcraft.breeds_slab);
        this.addItemAspect(Items.DIORITE_WALL, Thaumcraft.breeds_wall);
        this.addItemAspect(Items.POLISHED_DIORITE, Thaumcraft.polished_breeds);
        this.addItemAspect(Items.POLISHED_DIORITE_STAIRS, Thaumcraft.polished_breeds_stairs);
        this.addItemAspect(Items.POLISHED_DIORITE_SLAB, Thaumcraft.polished_breeds_slab);
        //ANDESITE
        this.addItemAspect(Items.ANDESITE, Thaumcraft.breeds);
        this.addItemAspect(Items.ANDESITE_STAIRS, Thaumcraft.breeds_stairs);
        this.addItemAspect(Items.ANDESITE_SLAB, Thaumcraft.breeds_slab);
        this.addItemAspect(Items.ANDESITE_WALL, Thaumcraft.breeds_wall);
        this.addItemAspect(Items.POLISHED_ANDESITE, Thaumcraft.polished_breeds);
        this.addItemAspect(Items.POLISHED_ANDESITE_STAIRS, Thaumcraft.polished_breeds_stairs);
        this.addItemAspect(Items.POLISHED_ANDESITE_SLAB, Thaumcraft.polished_breeds_slab);

        /////////////////////// - - - - - DEEPSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.DEEPSLATE, new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect(Items.COBBLED_DEEPSLATE, new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2).add(Aspects.PERDITIO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_STAIRS, new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2).add(Aspects.PERDITIO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_SLAB, new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));
        this.addItemAspect(Items.COBBLED_DEEPSLATE_WALL, new ItemAspects(Aspects.TERRA, 4).add(Aspects.ORDO));

        this.addItemAspect(Items.CHISELED_DEEPSLATE, new ItemAspects(Aspects.TERRA, 4).add(Aspects.ORDO, 4));

        this.addItemAspect(Items.POLISHED_DEEPSLATE, new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_STAIRS, new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_SLAB, new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));
        this.addItemAspect(Items.POLISHED_DEEPSLATE_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));

        this.addItemAspect(Items.DEEPSLATE_BRICKS, new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO));
        this.addItemAspect(Items.CRACKED_DEEPSLATE_BRICKS, new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2).add(Aspects.IGNIS));
        this.addItemAspect(Items.DEEPSLATE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO));
        this.addItemAspect(Items.DEEPSLATE_BRICK_SLAB, new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));
        this.addItemAspect(Items.DEEPSLATE_BRICK_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));

        this.addItemAspect(Items.DEEPSLATE_TILES, new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect(Items.CRACKED_DEEPSLATE_TILES, new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO).add(Aspects.IGNIS));
        this.addItemAspect(Items.DEEPSLATE_TILE_STAIRS, new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect(Items.DEEPSLATE_TILE_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.ORDO));
        this.addItemAspect(Items.DEEPSLATE_TILE_WALL, new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));

        this.addItemAspect(Items.REINFORCED_DEEPSLATE, new ItemAspects(Aspects.TERRA, 20).add(Aspects.TEMPUS, 5).add(Aspects.AURAM, 20)
                .add(Aspects.TENEBRAE, 20).add(Aspects.ALIENIS, 20));
        /////////////////////// - - - - - BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.BRICK, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5).add(Aspects.IGNIS));
        this.addItemAspect(Items.BRICKS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15).add(Aspects.IGNIS, 3));
        this.addItemAspect(Items.BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15).add(Aspects.IGNIS, 3));
        this.addItemAspect(Items.BRICK_SLAB, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5).add(Aspects.IGNIS));
        this.addItemAspect(Items.BRICK_WALL, new ItemAspects(Aspects.TERRA, 7).add(Aspects.AQUA, 7).add(Aspects.IGNIS, 2));
        /////////////////////// - - - - - MUD ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.MUD, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect(Items.PACKED_MUD, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5)
                .add(Aspects.HERBA, 3).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.MUD_BRICKS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect(Items.MUD_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect(Items.MUD_BRICK_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.AQUA));
        this.addItemAspect(Items.MUD_BRICK_WALL, new ItemAspects(Aspects.TERRA, 2).add(Aspects.AQUA, 2));
        /////////////////////// - - - - - SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SANDSTONE, Thaumcraft.sandstones);
        this.addItemAspect(Items.SANDSTONE_STAIRS, Thaumcraft.sandstone_stairs);
        this.addItemAspect(Items.SANDSTONE_SLAB, Thaumcraft.sandstone_slabs);
        this.addItemAspect(Items.SANDSTONE_WALL, Thaumcraft.sandstone_walls);
        this.addItemAspect(Items.CHISELED_SANDSTONE, Thaumcraft.chiseled_sandstones);
        /////////////////////// - - - - - SMOOTH SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SMOOTH_SANDSTONE, new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15).add(Aspects.ORDO));
        this.addItemAspect(Items.SMOOTH_SANDSTONE_STAIRS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15).add(Aspects.ORDO));
        this.addItemAspect(Items.SMOOTH_SANDSTONE_SLAB, new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO, 5).add(Aspects.ORDO));
        /////////////////////// - - - - - CUT SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.CUT_SANDSTONE, Thaumcraft.cut_sandstones);
        this.addItemAspect(Items.CUT_SANDSTONE_SLAB, Thaumcraft.cut_sandstone_slabs);
        /////////////////////// - - - - - RED SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.RED_SANDSTONE, Thaumcraft.sandstones);
        this.addItemAspect(Items.RED_SANDSTONE_STAIRS, Thaumcraft.sandstone_stairs);
        this.addItemAspect(Items.RED_SANDSTONE_SLAB, Thaumcraft.sandstone_slabs);
        this.addItemAspect(Items.RED_SANDSTONE_WALL, Thaumcraft.sandstone_walls);
        this.addItemAspect(Items.CHISELED_RED_SANDSTONE, Thaumcraft.chiseled_sandstones);
        /////////////////////// - - - - - RED SMOOTH SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE, new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15).add(Aspects.ORDO));
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE_STAIRS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.PERDITIO, 15).add(Aspects.ORDO));
        this.addItemAspect(Items.SMOOTH_RED_SANDSTONE_SLAB, new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO, 5).add(Aspects.ORDO));
        /////////////////////// - - - - - REDCUT SANDSTONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.CUT_RED_SANDSTONE, Thaumcraft.cut_sandstones);
        this.addItemAspect(Items.CUT_RED_SANDSTONE_SLAB, Thaumcraft.cut_sandstone_slabs);
        /////////////////////// - - - - - PRISMARINE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.PRISMARINE, new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect(Items.PRISMARINE_STAIRS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect(Items.PRISMARINE_SLAB, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect(Items.PRISMARINE_WALL, new ItemAspects(Aspects.TERRA, 7).add(Aspects.AQUA, 7));
        /////////////////////// - - - - - PRISMARINE BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.PRISMARINE_BRICKS, new ItemAspects(Aspects.TERRA, 33).add(Aspects.AQUA, 33));
        this.addItemAspect(Items.PRISMARINE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 33).add(Aspects.AQUA, 33));
        this.addItemAspect(Items.PRISMARINE_BRICK_SLAB, new ItemAspects(Aspects.TERRA, 16).add(Aspects.AQUA, 16));
        /////////////////////// - - - - - DARK BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.DARK_PRISMARINE, new ItemAspects(Aspects.TERRA, 31).add(Aspects.AQUA, 31).add(Aspects.SENSUS, 3).add(Aspects.BESTIA));
        this.addItemAspect(Items.DARK_PRISMARINE_STAIRS, new ItemAspects(Aspects.TERRA, 31).add(Aspects.AQUA, 31).add(Aspects.SENSUS, 3).add(Aspects.BESTIA));
        this.addItemAspect(Items.DARK_PRISMARINE_SLAB, new ItemAspects(Aspects.TERRA, 6).add(Aspects.AQUA, 6).add(Aspects.SENSUS).add(Aspects.BESTIA));
        /////////////////////// - - - - - NETHER BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.NETHER_BRICKS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 9).add(Aspects.ORDO, 3));
        this.addItemAspect(Items.CRACKED_NETHER_BRICKS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 10).add(Aspects.ORDO, 3));
        this.addItemAspect(Items.NETHER_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 10).add(Aspects.ORDO, 3));
        this.addItemAspect(Items.NETHER_BRICK_SLAB, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO));
        this.addItemAspect(Items.NETHER_BRICK_WALL, new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 7).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.NETHER_BRICK_FENCE, new ItemAspects(Aspects.TERRA, 11).add(Aspects.IGNIS, 6).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.CHISELED_NETHER_BRICKS, new ItemAspects(Aspects.TERRA, 11).add(Aspects.IGNIS, 5).add(Aspects.ORDO, 3));
        /////////////////////// - - - - - RED NETHER BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.RED_NETHER_BRICKS, new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 4).add(Aspects.ALKIMIA, 4)
                .add(Aspects.VITIUM, 3).add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 4).add(Aspects.ALKIMIA, 4)
                .add(Aspects.VITIUM, 3).add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_SLAB, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.ALKIMIA).add(Aspects.VITIUM)
                .add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect(Items.RED_NETHER_BRICK_WALL, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO));
        /////////////////////// - - - - - BASALT ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.BASALT, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.SMOOTH_BASALT, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.POLISHED_BASALT, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.ORDO));
        /////////////////////// - - - - - BLACK STONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.BLACKSTONE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect(Items.GILDED_BLACKSTONE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS)
                .add(Aspects.METALLUM, 5).add(Aspects.DESIDERIUM, 5));
        this.addItemAspect(Items.BLACKSTONE_STAIRS, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect(Items.BLACKSTONE_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.BLACKSTONE_STAIRS, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS).add(Aspects.ORDO));
        this.addItemAspect(Items.BLACKSTONE_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS, 2).add(Aspects.VACOUS).add(Aspects.ORDO));
        /////////////////////// - - - - - POLISHED BLACK STONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.CHISELED_POLISHED_BLACKSTONE, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_STAIRS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_PRESSURE_PLATE, new ItemAspects(Aspects.TERRA, 2).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BUTTON, new ItemAspects(Aspects.TERRA, 2).add(Aspects.MACHINA, 5).add(Aspects.IGNIS).add(Aspects.VACOUS));
        /////////////////////// - - - - - POLISHED BLACK STONE BRICKS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICKS, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect(Items.POLISHED_BLACKSTONE_BRICK_WALL, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS, 3).add(Aspects.VACOUS));
        /////////////////////// - - - - - END STONE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.END_STONE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.TENEBRAE, 5));
        this.addItemAspect(Items.END_STONE_BRICKS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        this.addItemAspect(Items.END_STONE_BRICK_STAIRS, new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        this.addItemAspect(Items.END_STONE_BRICK_SLAB, new ItemAspects(Aspects.TERRA).add(Aspects.TENEBRAE));
        this.addItemAspect(Items.END_STONE_BRICK_WALL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        /////////////////////// - - - - - PURPUR ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.PURPUR_BLOCK, new ItemAspects(Aspects.ALIENIS, 3).add(Aspects.SENSUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.PURPUR_STAIRS, new ItemAspects(Aspects.ALIENIS, 3).add(Aspects.SENSUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.PURPUR_SLAB, new ItemAspects(Aspects.ALIENIS).add(Aspects.SENSUS).add(Aspects.HERBA));
        this.addItemAspect(Items.PURPUR_PILLAR, new ItemAspects(Aspects.ALIENIS).add(Aspects.SENSUS).add(Aspects.HERBA));
        /////////////////////// - - - - - IRON ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.IRON_INGOT, new ItemAspects(Aspects.METALLUM, 15));
        this.addItemAspect(Items.IRON_NUGGET, new ItemAspects(Aspects.METALLUM));
        this.addItemAspect(Items.CHAIN, new ItemAspects(Aspects.METALLUM, 16));
        this.addItemAspect(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, new ItemAspects(Aspects.METALLUM, 22).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.IRON_TRAPDOOR, new ItemAspects(Aspects.METALLUM, 45));
        this.addItemAspect(Items.IRON_DOOR, new ItemAspects(Aspects.METALLUM, 22).add(Aspects.VINCULUM, 5).add(Aspects.MACHINA, 5));
        this.addItemAspect(Items.IRON_BARS, new ItemAspects(Aspects.METALLUM, 4));
        this.addItemAspect(Items.IRON_BLOCK, new ItemAspects(Aspects.METALLUM, 101));
        this.addItemAspect(Items.IRON_HORSE_ARMOR, new ItemAspects(Aspects.METALLUM, 15));
        /////////////////////// - - - - - NETHERITE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.ANCIENT_DEBRIS, new ItemAspects(Aspects.METALLUM, 25).add(Aspects.TEMPUS, 15).add(Aspects.IGNIS, 33));
        this.addItemAspect(Items.NETHERITE_SCRAP, new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TEMPUS, 10).add(Aspects.IGNIS, 25));
        this.addItemAspect(Items.NETHERITE_INGOT, new ItemAspects(Aspects.METALLUM, 75).add(Aspects.TEMPUS, 40).add(Aspects.IGNIS, 50)
                .add(Aspects.DESIDERIUM, 30));
        this.addItemAspect(Items.NETHERITE_BLOCK, new ItemAspects(Aspects.METALLUM, 525).add(Aspects.TEMPUS, 280).add(Aspects.IGNIS, 350)
                .add(Aspects.DESIDERIUM, 210));
        /////////////////////// - - - - - QUARTZ ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.QUARTZ_BLOCK, new ItemAspects(Aspects.VITREUS, 15));
        this.addItemAspect(Items.QUARTZ_STAIRS, new ItemAspects(Aspects.VITREUS, 15));
        this.addItemAspect(Items.QUARTZ_SLAB, new ItemAspects(Aspects.VITREUS, 3));
        this.addItemAspect(Items.CHISELED_QUARTZ_BLOCK, new ItemAspects(Aspects.VITREUS, 7));
        this.addItemAspect(Items.QUARTZ_BRICKS, new ItemAspects(Aspects.VITREUS, 10));
        this.addItemAspect(Items.QUARTZ_PILLAR, new ItemAspects(Aspects.VITREUS, 11));
        this.addItemAspect(Items.SMOOTH_QUARTZ, new ItemAspects(Aspects.VITREUS, 9).add(Aspects.IGNIS));
        this.addItemAspect(Items.SMOOTH_QUARTZ_STAIRS, new ItemAspects(Aspects.VITREUS, 9).add(Aspects.IGNIS));
        this.addItemAspect(Items.SMOOTH_QUARTZ_SLAB, new ItemAspects(Aspects.VITREUS, 3).add(Aspects.IGNIS));
        /////////////////////// - - - - - COPPER ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.COPPER_BLOCK, new ItemAspects(Aspects.METALLUM, 50));
        this.addItemAspect(Items.CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO));
        this.addItemAspect(Items.CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO));
        this.addItemAspect(Items.CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO));
        this.addItemAspect(Items.EXPOSED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect(Items.EXPOSED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect(Items.WEATHERED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect(Items.WEATHERED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect(Items.OXIDIZED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        this.addItemAspect(Items.OXIDIZED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        /////////////////////// - - - - - WAXED COPPER ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WAXED_COPPER_BLOCK, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_EXPOSED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_WEATHERED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_COPPER, new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS, new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        /////////////////////// - - - - - BAMBOO ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.BAMBOO, new ItemAspects(Aspects.HERBA));
        this.addItemAspect(Items.BAMBOO_BLOCK, new ItemAspects(Aspects.HERBA, 7));
        this.addItemAspect(Items.STRIPPED_BAMBOO_BLOCK, new ItemAspects(Aspects.HERBA, 6));
        this.addItemAspect(Items.BAMBOO_PLANKS, new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect(Items.BAMBOO_MOSAIC, new ItemAspects(Aspects.HERBA));
        this.addItemAspect(Items.BAMBOO_STAIRS, new ItemAspects(Aspects.HERBA, 7));
        this.addItemAspect(Items.BAMBOO_MOSAIC_STAIRS, new ItemAspects(Aspects.HERBA));
        this.addItemAspect(Items.BAMBOO_SLAB, new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect(Items.BAMBOO_MOSAIC_SLAB, new ItemAspects(Aspects.HERBA));
        this.addItemAspect(Items.BAMBOO_FENCE, new ItemAspects(Aspects.HERBA, 4));
        this.addItemAspect(Items.BAMBOO_FENCE_GATE, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_DOOR, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_TRAPDOOR, new ItemAspects(Aspects.HERBA, 2).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect(Items.BAMBOO_PRESSURE_PLATE, new ItemAspects(Aspects.HERBA).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.BAMBOO_BUTTON, new ItemAspects(Aspects.HERBA).add(Aspects.MACHINA, 5));
        /////////////////////// - - - - - CRIMSON ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.CRIMSON_STEM, new ItemAspects(Aspects.HERBA, 20).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_HYPHAE, new ItemAspects(Aspects.HERBA, 22).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_CRIMSON_STEM, new ItemAspects(Aspects.HERBA, 19).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_CRIMSON_HYPHAE, new ItemAspects(Aspects.HERBA, 19).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_PLANKS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_STAIRS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.CRIMSON_SLAB, new ItemAspects(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect(Items.CRIMSON_FENCE, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 3));
        this.addItemAspect(Items.CRIMSON_FENCE_GATE, new ItemAspects(Aspects.HERBA, 7).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_DOOR, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5).add(Aspects.INFERNO, 9));
        this.addItemAspect(Items.CRIMSON_TRAPDOOR, new ItemAspects(Aspects.HERBA, 6).add(Aspects.MOTUS, 5).add(Aspects.INFERNO, 7));
        this.addItemAspect(Items.CRIMSON_PRESSURE_PLATE, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_BUTTON, new ItemAspects(Aspects.HERBA, 2).add(Aspects.MACHINA, 5).add(Aspects.INFERNO));
        /////////////////////// - - - - - WARPED ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WARPED_STEM, new ItemAspects(Aspects.HERBA, 20).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.WARPED_HYPHAE, new ItemAspects(Aspects.HERBA, 22).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_WARPED_STEM, new ItemAspects(Aspects.HERBA, 19).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.STRIPPED_WARPED_HYPHAE, new ItemAspects(Aspects.HERBA, 19).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.WARPED_PLANKS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.WARPED_STAIRS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 5));
        this.addItemAspect(Items.WARPED_SLAB, new ItemAspects(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect(Items.WARPED_FENCE, new ItemAspects(Aspects.HERBA, 3).add(Aspects.INFERNO, 3));
        this.addItemAspect(Items.WARPED_FENCE_GATE, new ItemAspects(Aspects.HERBA, 7).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.WARPED_DOOR, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5).add(Aspects.INFERNO, 9));
        this.addItemAspect(Items.WARPED_TRAPDOOR, new ItemAspects(Aspects.HERBA, 6).add(Aspects.MOTUS, 5).add(Aspects.INFERNO, 7));
        this.addItemAspect(Items.WARPED_PRESSURE_PLATE, new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.WARPED_BUTTON, new ItemAspects(Aspects.HERBA, 2).add(Aspects.MACHINA, 5).add(Aspects.INFERNO));
        /////////////////////// - - - - - WOOL ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_WOOL, new ItemAspects(Aspects.BESTIA, 15).add(Aspects.FABRICO, 5));
        this.addItemAspect(Items.LIGHT_GRAY_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.GRAY_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.BLACK_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.BROWN_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.RED_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.ORANGE_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.YELLOW_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.LIME_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.GREEN_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.CYAN_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.LIGHT_BLUE_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.BLUE_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.PURPLE_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.MAGENTA_WOOL, Thaumcraft.wools);
        this.addItemAspect(Items.PINK_WOOL, Thaumcraft.wools);
        /////////////////////// - - - - - CARPET ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_CARPET, new ItemAspects(Aspects.BESTIA, 7).add(Aspects.FABRICO, 2));
        this.addItemAspect(Items.LIGHT_GRAY_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.GRAY_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.BLACK_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.BROWN_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.RED_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.ORANGE_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.YELLOW_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.LIME_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.GREEN_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.CYAN_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.LIGHT_BLUE_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.BLUE_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.PURPLE_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.MAGENTA_CARPET, Thaumcraft.carpets);
        this.addItemAspect(Items.PINK_CARPET, Thaumcraft.carpets);
        /////////////////////// - - - - - TERRACOTTA ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.TERRACOTTA, new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS));
        this.addItemAspect(Items.WHITE_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.LIGHT_GRAY_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.GRAY_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.BLACK_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.BROWN_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.RED_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.ORANGE_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.YELLOW_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.LIME_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.GREEN_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.CYAN_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.LIGHT_BLUE_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.BLUE_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.PURPLE_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.MAGENTA_TERRACOTTA, Thaumcraft.terracotes);
        this.addItemAspect(Items.PINK_TERRACOTTA, Thaumcraft.terracotes);
        /////////////////////// - - - - - GLAZED TERRACOTA ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.GRAY_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.BLACK_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.BROWN_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.RED_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.ORANGE_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.YELLOW_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.LIME_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.GREEN_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.CYAN_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.LIGHT_BLUE_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.BLUE_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.PURPLE_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.MAGENTA_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        this.addItemAspect(Items.PINK_GLAZED_TERRACOTTA, Thaumcraft.glazed_terracots);
        /////////////////////// - - - - - CONCRETE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.LIGHT_GRAY_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.GRAY_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.BLACK_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.BROWN_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.RED_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.ORANGE_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.YELLOW_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.LIME_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.GREEN_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.CYAN_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.LIGHT_BLUE_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.BLUE_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.PURPLE_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.MAGENTA_CONCRETE, Thaumcraft.concretes);
        this.addItemAspect(Items.PINK_CONCRETE, Thaumcraft.concretes);
        /////////////////////// - - - - - POWDER CONCRETE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.LIGHT_GRAY_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.GRAY_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.BLACK_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.BROWN_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.RED_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.ORANGE_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.YELLOW_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.LIME_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.GREEN_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.CYAN_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.LIGHT_BLUE_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.BLUE_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.PURPLE_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.MAGENTA_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        this.addItemAspect(Items.PINK_CONCRETE_POWDER, Thaumcraft.concrete_powders);
        /////////////////////// - - - - - GLASS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.TINTED_GLASS, new ItemAspects(Aspects.VITREUS, 5).add(Aspects.TENEBRAE, 2));
        this.addItemAspect(Items.WHITE_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.LIGHT_GRAY_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.GRAY_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.BLACK_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.BROWN_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.RED_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.ORANGE_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.YELLOW_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.LIME_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.GREEN_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.CYAN_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.LIGHT_BLUE_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.BLUE_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.PURPLE_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.MAGENTA_STAINED_GLASS, Thaumcraft.glass);
        this.addItemAspect(Items.PINK_STAINED_GLASS, Thaumcraft.glass);
        /////////////////////// - - - - - GLASS PANE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.WHITE_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.LIGHT_GRAY_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.GRAY_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.BLACK_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.BROWN_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.RED_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.ORANGE_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.YELLOW_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.LIME_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.GREEN_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.CYAN_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.LIGHT_BLUE_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.BLUE_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.PURPLE_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.MAGENTA_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        this.addItemAspect(Items.PINK_STAINED_GLASS_PANE, Thaumcraft.glass_panes);
        /////////////////////// - - - - - BED ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_BED, new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6));
        this.addItemAspect(Items.LIGHT_GRAY_BED, Thaumcraft.beds);
        this.addItemAspect(Items.GRAY_BED, Thaumcraft.beds);
        this.addItemAspect(Items.BLACK_BED, Thaumcraft.beds);
        this.addItemAspect(Items.BROWN_BED, Thaumcraft.beds);
        this.addItemAspect(Items.RED_BED, Thaumcraft.beds);
        this.addItemAspect(Items.ORANGE_BED, Thaumcraft.beds);
        this.addItemAspect(Items.YELLOW_BED, Thaumcraft.beds);
        this.addItemAspect(Items.LIME_BED, Thaumcraft.beds);
        this.addItemAspect(Items.GREEN_BED, Thaumcraft.beds);
        this.addItemAspect(Items.CYAN_BED, Thaumcraft.beds);
        this.addItemAspect(Items.LIGHT_BLUE_BED, Thaumcraft.beds);
        this.addItemAspect(Items.BLUE_BED, Thaumcraft.beds);
        this.addItemAspect(Items.PURPLE_BED, Thaumcraft.beds);
        this.addItemAspect(Items.MAGENTA_BED, Thaumcraft.beds);
        this.addItemAspect(Items.PINK_BED, Thaumcraft.beds);
        /////////////////////// - - - - - CANDLE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.CANDLE, new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO));
        this.addItemAspect(Items.WHITE_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.LIGHT_GRAY_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.GRAY_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.BLACK_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.BROWN_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.RED_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.ORANGE_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.YELLOW_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.LIME_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.GREEN_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.CYAN_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.LIGHT_BLUE_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.BLUE_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.PURPLE_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.MAGENTA_CANDLE, Thaumcraft.candles);
        this.addItemAspect(Items.PINK_CANDLE, Thaumcraft.candles);
        /////////////////////// - - - - - BANNER ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.WHITE_BANNER, new ItemAspects(Aspects.BESTIA, 67).add(Aspects.FABRICO, 22));
        this.addItemAspect(Items.LIGHT_GRAY_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.GRAY_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.BLACK_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.BROWN_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.RED_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.ORANGE_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.YELLOW_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.LIME_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.GREEN_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.CYAN_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.LIGHT_BLUE_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.BLUE_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.PURPLE_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.MAGENTA_BANNER, Thaumcraft.banners);
        this.addItemAspect(Items.PINK_BANNER, Thaumcraft.banners);
        /////////////////////// - - - - - ORE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.COAL_ORE, new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.IGNIS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_COAL_ORE, new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.IGNIS, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect(Items.IRON_ORE, new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_IRON_ORE, new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.RAW_IRON, new ItemAspects(Aspects.METALLUM, 15).add(Aspects.PERDITIO, 3));
        this.addItemAspect(Items.RAW_IRON_BLOCK, new ItemAspects(Aspects.METALLUM, 90).add(Aspects.PERDITIO, 21));

        this.addItemAspect(Items.COPPER_ORE, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.TERRA, 8));
        this.addItemAspect(Items.DEEPSLATE_COPPER_ORE, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));
        this.addItemAspect(Items.RAW_COPPER, new ItemAspects(Aspects.METALLUM, 7).add(Aspects.PERDITIO, 3));
        this.addItemAspect(Items.RAW_COPPER_BLOCK, new ItemAspects(Aspects.METALLUM, 49).add(Aspects.PERDITIO, 21));

        this.addItemAspect(Items.GOLD_ORE, new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_GOLD_ORE, new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));
        this.addItemAspect(Items.RAW_GOLD, new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.PERDITIO, 5));
        this.addItemAspect(Items.RAW_GOLD_BLOCK, new ItemAspects(Aspects.METALLUM, 66).add(Aspects.DESIDERIUM, 66).add(Aspects.PERDITIO, 35));

        this.addItemAspect(Items.REDSTONE_ORE, new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_REDSTONE_ORE, new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect(Items.EMERALD_ORE, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_EMERALD_ORE, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));

        this.addItemAspect(Items.LAPIS_ORE, new ItemAspects(Aspects.SENSUS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_LAPIS_ORE, new ItemAspects(Aspects.SENSUS, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect(Items.DIAMOND_ORE, new ItemAspects(Aspects.DESIDERIUM, 15).add(Aspects.VITREUS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.DEEPSLATE_DIAMOND_ORE, new ItemAspects(Aspects.DESIDERIUM, 15).add(Aspects.VITREUS, 15).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));

        this.addItemAspect(Items.NETHER_GOLD_ORE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.METALLUM, 5).add(Aspects.DESIDERIUM, 3)
                .add(Aspects.IGNIS, 2));
        this.addItemAspect(Items.NETHER_QUARTZ_ORE, new ItemAspects(Aspects.VITREUS, 10).add(Aspects.TERRA, 5));
        /////////////////////// - - - - - AMETHYST ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.AMETHYST_BLOCK, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO));
        this.addItemAspect(Items.BUDDING_AMETHYST, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.SMALL_AMETHYST_BUD, new ItemAspects(Aspects.VITREUS, 3).add(Aspects.PRAECANTATIO).add(Aspects.VICTUS));
        this.addItemAspect(Items.MEDIUM_AMETHYST_BUD, new ItemAspects(Aspects.VITREUS, 5).add(Aspects.PRAECANTATIO, 2).add(Aspects.VICTUS));
        this.addItemAspect(Items.LARGE_AMETHYST_BUD, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO, 3).add(Aspects.VICTUS));
        this.addItemAspect(Items.AMETHYST_CLUSTER, new ItemAspects(Aspects.VITREUS, 20).add(Aspects.PRAECANTATIO, 5).add(Aspects.VICTUS, 2));
        /////////////////////// - - - - - SEEDS ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.PUMPKIN_SEEDS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect(Items.MELON_SEEDS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect(Items.TORCHFLOWER_SEEDS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect(Items.PITCHER_POD, new ItemAspects(Aspects.HERBA, 4).add(Aspects.VICTUS));
        this.addItemAspect(Items.WHEAT_SEEDS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect(Items.BEETROOT_SEEDS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        /////////////////////// - - - - - CORAL ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.TUBE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.AQUA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.BRAIN_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.AQUA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.BUBBLE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.AQUA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.FIRE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.AQUA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.HORN_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.AQUA, 10).add(Aspects.VICTUS, 3));

        this.addItemAspect(Items.DEAD_TUBE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA, 15).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL_BLOCK, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.DEAD_FIRE_CORAL_BLOCK, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.DEAD_HORN_CORAL_BLOCK, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS, 3));

        this.addItemAspect(Items.TUBE_CORAL, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.VICTUS));
        this.addItemAspect(Items.BRAIN_CORAL, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.VICTUS));
        this.addItemAspect(Items.BUBBLE_CORAL, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.VICTUS));
        this.addItemAspect(Items.FIRE_CORAL, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.VICTUS));
        this.addItemAspect(Items.HORN_CORAL, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.VICTUS));

        this.addItemAspect(Items.DEAD_TUBE_CORAL, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_FIRE_CORAL, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_HORN_CORAL, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));

        this.addItemAspect(Items.TUBE_CORAL_FAN, new ItemAspects(Aspects.HERBA, 3).add(Aspects.AQUA, 2).add(Aspects.VICTUS));
        this.addItemAspect(Items.BRAIN_CORAL_FAN, new ItemAspects(Aspects.HERBA, 3).add(Aspects.AQUA, 2).add(Aspects.VICTUS));
        this.addItemAspect(Items.BUBBLE_CORAL_FAN, new ItemAspects(Aspects.HERBA, 3).add(Aspects.AQUA, 2).add(Aspects.VICTUS));
        this.addItemAspect(Items.FIRE_CORAL_FAN, new ItemAspects(Aspects.HERBA, 3).add(Aspects.AQUA, 2).add(Aspects.VICTUS));
        this.addItemAspect(Items.HORN_CORAL_FAN, new ItemAspects(Aspects.HERBA, 3).add(Aspects.AQUA, 2).add(Aspects.VICTUS));

        this.addItemAspect(Items.DEAD_TUBE_CORAL_FAN, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_BRAIN_CORAL_FAN, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_BUBBLE_CORAL_FAN, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_FIRE_CORAL_FAN, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.DEAD_HORN_CORAL_FAN, new ItemAspects(Aspects.HERBA).add(Aspects.VICTUS));
        /////////////////////// - - - - - BEE ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.BEE_NEST, new ItemAspects(Aspects.HERBA, 15).add(Aspects.VICTUS, 15));
        this.addItemAspect(Items.HONEYCOMB, new ItemAspects(Aspects.HERBA, 3));
        this.addItemAspect(Items.HONEYCOMB_BLOCK, new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect(Items.HONEY_BOTTLE, new ItemAspects(Aspects.HERBA, 10).add(Aspects.VICTUS, 3).add(Aspects.AQUA, 2).add(Aspects.DESIDERIUM));
        this.addItemAspect(Items.HONEY_BLOCK, new ItemAspects(Aspects.HERBA, 30).add(Aspects.VICTUS, 9).add(Aspects.AQUA, 6).add(Aspects.DESIDERIUM, 3));
        /////////////////////// - - - - - SKULK ITEMS - - - - - ////////////////////////// 
        this.addItemAspect(Items.SCULK, new ItemAspects(Aspects.ECHO, 5).add(Aspects.TENEBRAE, 5));
        this.addItemAspect(Items.SCULK_VEIN, new ItemAspects(Aspects.ECHO).add(Aspects.TENEBRAE));
        this.addItemAspect(Items.SCULK_CATALYST, new ItemAspects(Aspects.ECHO, 15).add(Aspects.TENEBRAE, 15).add(Aspects.VITIUM, 10));
        this.addItemAspect(Items.SCULK_SHRIEKER, new ItemAspects(Aspects.ECHO, 25).add(Aspects.TENEBRAE, 17).add(Aspects.VITIUM, 12));
        this.addItemAspect(Items.SCULK_SENSOR, new ItemAspects(Aspects.ECHO, 17).add(Aspects.TENEBRAE, 10).add(Aspects.VITIUM, 3));










        this.addItemAspect(Items.CAULDRON, new ItemAspects(Aspects.METALLUM, 60));
        this.addItemAspect(Items.BREWING_STAND, new ItemAspects(Aspects.ALKIMIA, 25).add(Aspects.FABRICO, 15).add(Aspects.IGNIS, 11)
                .add(Aspects.TERRA, 11).add(Aspects.MOTUS, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect(Items.END_CRYSTAL, new ItemAspects(Aspects.VITREUS, 26).add(Aspects.ALKIMIA, 9).add(Aspects.MOTUS, 8)
                .add(Aspects.SENSUS, 7).add(Aspects.SPIRITUS, 7).add(Aspects.ALIENIS, 5).add(Aspects.EXANIMIS, 3));
        this.addItemAspect(Items.ENCHANTING_TABLE, new ItemAspects(Aspects.PRAECANTATIO, 25).add(Aspects.VITREUS, 22).add(Aspects.DESIDERIUM, 22)
                .add(Aspects.FABRICO, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 15).add(Aspects.TENEBRAE, 15));
        this.addItemAspect(Items.LEATHER, new ItemAspects(Aspects.BESTIA, 5).add(Aspects.PRAEMUNIO, 5));
        this.addItemAspect(Items.BOOK, new ItemAspects(Aspects.HERBA, 6).add(Aspects.AQUA, 4).add(Aspects.COGNITIO, 4).add(Aspects.BESTIA, 3)
                .add(Aspects.AER, 2).add(Aspects.PRAEMUNIO, 3));
        this.addItemAspect(Items.DIAMOND, new ItemAspects(Aspects.VITREUS, 15).add(Aspects.DESIDERIUM, 15));
        this.addItemAspect(Items.JUKEBOX, new ItemAspects(Aspects.SENSUS, 18).add(Aspects.HERBA, 18).add(Aspects.AER, 15)
                .add(Aspects.VITREUS, 11).add(Aspects.DESIDERIUM, 11).add(Aspects.MACHINA, 10));
        this.addItemAspect(Items.NOTE_BLOCK, new ItemAspects(Aspects.SENSUS, 18).add(Aspects.HERBA, 18).add(Aspects.AER, 15)
                .add(Aspects.MACHINA, 10).add(Aspects.POTENTIA, 7));
        this.addItemAspect(Items.COMPOSTER, new ItemAspects(Aspects.HERBA, 5));
        this.addItemAspect(Items.ANVIL, new ItemAspects(Aspects.METALLUM, 272));
        this.addItemAspect(Items.CHIPPED_ANVIL, new ItemAspects(Aspects.METALLUM, 150).add(Aspects.PERDITIO, 10));
        this.addItemAspect(Items.DAMAGED_ANVIL, new ItemAspects(Aspects.METALLUM, 75).add(Aspects.PERDITIO, 30));
        this.addItemAspect(Items.CHARCOAL, new ItemAspects(Aspects.POTENTIA, 10).add(Aspects.IGNIS, 12));
        this.addItemAspect(Items.COAL, new ItemAspects(Aspects.POTENTIA, 10).add(Aspects.IGNIS, 10));
        this.addItemAspect(Items.SOUL_CAMPFIRE, new ItemAspects(Aspects.HERBA, 35).add(Aspects.POTENTIA, 8).add(Aspects.IGNIS, 7)
                .add(Aspects.SPIRITUS, 4));
        this.addItemAspect(Items.CAMPFIRE, new ItemAspects(Aspects.HERBA, 35).add(Aspects.POTENTIA, 8).add(Aspects.IGNIS, 7));
        this.addItemAspect(Items.BLAST_FURNACE, new ItemAspects(Aspects.METALLUM, 55).add(Aspects.TERRA, 40).add(Aspects.IGNIS, 10)
                .add(Aspects.PERDITIO, 6));
        this.addItemAspect(Items.SMOKER, new ItemAspects(Aspects.TERRA, 30).add(Aspects.IGNIS, 10).add(Aspects.PERDITIO, 6)
                .add(Aspects.HERBA, 50).add(Aspects.SENSUS, 2));
        this.addItemAspect(Items.FURNACE, new ItemAspects(Aspects.TERRA, 30).add(Aspects.IGNIS, 10).add(Aspects.PERDITIO, 6));
        this.addItemAspect(Items.LOOM, new ItemAspects(Aspects.HERBA, 4).add(Aspects.FABRICO).add(Aspects.BESTIA, 4));
        this.addItemAspect(Items.STICK, new ItemAspects(Aspects.HERBA));
        this.addItemAspect(Items.GRINDSTONE, new ItemAspects(Aspects.TERRA).add(Aspects.HERBA, 6).add(Aspects.SENSUS, 3));
        this.addItemAspect(Items.SMITHING_TABLE, new ItemAspects(Aspects.HERBA, 10).add(Aspects.METALLUM, 10).add(Aspects.SENSUS, 3));
        this.addItemAspect(Items.FLETCHING_TABLE, new ItemAspects(Aspects.HERBA, 10).add(Aspects.METALLUM, 15).add(Aspects.INSTRUMENTUM, 12)
                .add(Aspects.TERRA, 5).add(Aspects.IGNIS, 15).add(Aspects.SENSUS, 3));
        this.addItemAspect(Items.CARTOGRAPHY_TABLE, new ItemAspects(Aspects.HERBA, 14).add(Aspects.METALLUM, 10).add(Aspects.SENSUS, 3));
        this.addItemAspect(Items.STONECUTTER, new ItemAspects(Aspects.TERRA, 10).add(Aspects.METALLUM, 10).add(Aspects.SENSUS, 3));
        this.addItemAspect(Items.CRAFTING_TABLE, new ItemAspects(Aspects.FABRICO, 20).add(Aspects.HERBA, 9));
        this.addItemAspect(Items.PAPER, new ItemAspects(Aspects.HERBA, 3).add(Aspects.COGNITIO, 2).add(Aspects.AQUA, 2).add(Aspects.AER));
        this.addItemAspect(Items.SHROOMLIGHT, new ItemAspects(Aspects.LUX, 30).add(Aspects.HERBA, 7).add(Aspects.IGNIS, 5).add(Aspects.INFERNO, 3));
        this.addItemAspect(Items.GLOWSTONE, new ItemAspects(Aspects.LUX, 30).add(Aspects.SENSUS, 15));
        this.addItemAspect(Items.REDSTONE_LAMP, new ItemAspects(Aspects.POTENTIA, 20).add(Aspects.LUX, 22).add(Aspects.SENSUS, 11));
        this.addItemAspect(Items.END_ROD, new ItemAspects(Aspects.LUX, 5).add(Aspects.IGNIS));
        this.addItemAspect(Items.SOUL_LANTERN, new ItemAspects(Aspects.LUX, 5).add(Aspects.POTENTIA).add(Aspects.IGNIS).add(Aspects.SPIRITUS).add(Aspects.METALLUM));
        this.addItemAspect(Items.LANTERN, new ItemAspects(Aspects.LUX, 5).add(Aspects.POTENTIA).add(Aspects.IGNIS).add(Aspects.METALLUM));
        this.addItemAspect(Items.REDSTONE_TORCH, new ItemAspects(Aspects.POTENTIA, 7));
        this.addItemAspect(Items.SOUL_TORCH, new ItemAspects(Aspects.LUX, 5).add(Aspects.POTENTIA).add(Aspects.IGNIS).add(Aspects.SPIRITUS));
        this.addItemAspect(Items.TORCH, new ItemAspects(Aspects.LUX, 5).add(Aspects.POTENTIA).add(Aspects.IGNIS));
        this.addItemAspect(Items.OCHRE_FROGLIGHT, new ItemAspects(Aspects.LUX, 15).add(Aspects.VICTUS, 3).add(Aspects.ALKIMIA, 8));
        this.addItemAspect(Items.VERDANT_FROGLIGHT, new ItemAspects(Aspects.LUX, 15).add(Aspects.VICTUS, 3).add(Aspects.ALKIMIA, 8));
        this.addItemAspect(Items.PEARLESCENT_FROGLIGHT, new ItemAspects(Aspects.LUX, 15).add(Aspects.VICTUS, 3).add(Aspects.ALKIMIA, 8));
        this.addItemAspect(Items.SLIME_BLOCK, new ItemAspects(Aspects.AQUA, 33).add(Aspects.VICTUS, 33).add(Aspects.ALKIMIA, 6));
        this.addItemAspect(Items.HAY_BLOCK, new ItemAspects(Aspects.HERBA, 33).add(Aspects.VICTUS, 33));
        this.addItemAspect(Items.JACK_O_LANTERN, new ItemAspects(Aspects.HERBA, 20).add(Aspects.LUX, 2));
        this.addItemAspect(Items.CARVED_PUMPKIN, new ItemAspects(Aspects.HERBA, 20));
        this.addItemAspect(Items.PUMPKIN, new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect(Items.MELON, new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect(Items.SPONGE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.VINCULUM, 5).add(Aspects.VACOUS, 5));
        this.addItemAspect(Items.WET_SPONGE, new ItemAspects(Aspects.TERRA, 5).add(Aspects.VINCULUM, 5).add(Aspects.AQUA, 5));
        this.addItemAspect(Items.DRIED_KELP_BLOCK, new ItemAspects(Aspects.HERBA, 8).add(Aspects.IGNIS, 7));
        this.addItemAspect(Items.DRIED_KELP, new ItemAspects(Aspects.HERBA).add(Aspects.IGNIS));
        this.addItemAspect(Items.KELP, new ItemAspects(Aspects.HERBA).add(Aspects.AQUA));
        this.addItemAspect(Items.SEA_PICKLE, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.LUX, 2));
        this.addItemAspect(Items.SEAGRASS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA));
        this.addItemAspect(Items.SWEET_BERRIES, new ItemAspects(Aspects.HERBA, 7).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.GLOW_BERRIES, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS, 3).add(Aspects.LUX, 2));
        this.addItemAspect(Items.SNIFFER_EGG, new ItemAspects(Aspects.VICTUS, 15).add(Aspects.TEMPUS, 10).add(Aspects.HUMANUS, 3));
        this.addItemAspect(Items.TURTLE_EGG, new ItemAspects(Aspects.VICTUS, 5).add(Aspects.HUMANUS));
        this.addItemAspect(Items.FROGSPAWN, new ItemAspects(Aspects.VICTUS, 5).add(Aspects.HUMANUS));
        this.addItemAspect(Items.HANGING_ROOTS, new ItemAspects(Aspects.HERBA, 3));
        this.addItemAspect(Items.GLOW_LICHEN, new ItemAspects(Aspects.HERBA, 5).add(Aspects.LUX));
        this.addItemAspect(Items.SMALL_DRIPLEAF, new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect(Items.BIG_DRIPLEAF, new ItemAspects(Aspects.HERBA, 5));
        this.addItemAspect(Items.TWISTING_VINES, new ItemAspects(Aspects.HERBA, 5).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.WEEPING_VINES, new ItemAspects(Aspects.HERBA, 5).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.CRIMSON_ROOTS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.WARPED_ROOTS, new ItemAspects(Aspects.HERBA, 3).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect(Items.DEAD_BUSH, new ItemAspects(Aspects.HERBA, 5).add(Aspects.PERDITIO));
        this.addItemAspect(Items.FERN, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect(Items.SHORT_GRASS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect(Items.CRIMSON_FUNGUS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2).add(Aspects.IGNIS, 5));
        this.addItemAspect(Items.WARPED_FUNGUS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2).add(Aspects.IGNIS, 5));
        this.addItemAspect(Items.FLOWERING_AZALEA, new ItemAspects(Aspects.HERBA, 15).add(Aspects.VICTUS, 7));
        this.addItemAspect(Items.AZALEA, new ItemAspects(Aspects.HERBA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect(Items.RED_MUSHROOM_BLOCK, new ItemAspects(Aspects.HERBA, 15));
        this.addItemAspect(Items.BROWN_MUSHROOM_BLOCK, new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect(Items.MUSHROOM_STEM, new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect(Items.MUDDY_MANGROVE_ROOTS, new ItemAspects(Aspects.HERBA, 15).add(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect(Items.MANGROVE_ROOTS, new ItemAspects(Aspects.HERBA, 15));
        this.addItemAspect(Items.BONE_BLOCK, new ItemAspects(Aspects.SENSUS, 33));
        this.addItemAspect(Items.SOUL_SAND, new ItemAspects(Aspects.TERRA, 3).add(Aspects.SPIRITUS, 3)
                .add(Aspects.VINCULUM));
        this.addItemAspect(Items.SOUL_SOIL, new ItemAspects(Aspects.TERRA, 3).add(Aspects.SPIRITUS, 3)
                .add(Aspects.VINCULUM).add(Aspects.VOLATUS));
        this.addItemAspect(Items.WARPED_NYLIUM, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect(Items.CRIMSON_NYLIUM, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect(Items.CRYING_OBSIDIAN, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 5).add(Aspects.TENEBRAE, 5)
                .add(Aspects.PRAECANTATIO));
        this.addItemAspect(Items.MAGMA_BLOCK, new ItemAspects(Aspects.IGNIS, 10).add(Aspects.TERRA, 5).add(Aspects.INFERNO));
        this.addItemAspect(Items.POINTED_DRIPSTONE, new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO).add(Aspects.AQUA));
        this.addItemAspect(Items.DRIPSTONE_BLOCK, new ItemAspects(Aspects.TERRA, 10).add(Aspects.ORDO, 5).add(Aspects.AQUA, 3));
        this.addItemAspect(Items.CALCITE, new ItemAspects(Aspects.TERRA, 20).add(Aspects.ORDO, 5));
        this.addItemAspect(Items.MOSS_CARPET, new ItemAspects(Aspects.TERRA).add(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect(Items.MOSS_BLOCK, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 5).add(Aspects.VICTUS, 5));
        this.addItemAspect(Items.SNOW, new ItemAspects(Aspects.GELUM));
        this.addItemAspect(Items.SNOW_BLOCK, new ItemAspects(Aspects.GELUM, 3));
        this.addItemAspect(Items.BLUE_ICE, new ItemAspects(Aspects.GELUM, 30));
        this.addItemAspect(Items.PACKED_ICE, new ItemAspects(Aspects.GELUM, 25));
        this.addItemAspect(Items.CLAY, new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect(Items.ROOTED_DIRT, new ItemAspects(Aspects.TERRA, 4));
        this.addItemAspect(Items.COARSE_DIRT, new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect(Items.MYCELIUM, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA).add(Aspects.VITIUM));
        this.addItemAspect(Items.STRING, new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO));
        this.addItemAspect(Items.DIAMOND_BLOCK, new ItemAspects(Aspects.VITREUS, 101).add(Aspects.DESIDERIUM, 10));
        this.addItemAspect(Items.LAPIS_BLOCK, new ItemAspects(Aspects.SENSUS, 33).add(Aspects.TERRA, 13).add(Aspects.DESIDERIUM, 13));
        this.addItemAspect(Items.EMERALD_BLOCK, new ItemAspects(Aspects.VITREUS, 101).add(Aspects.DESIDERIUM, 67));
        this.addItemAspect(Items.REDSTONE_BLOCK, new ItemAspects(Aspects.DESIDERIUM, 67).add(Aspects.POTENTIA, 67));
        this.addItemAspect(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, new ItemAspects(Aspects.METALLUM, 15).add(Aspects.DESIDERIUM, 15)
                .add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.GOLD_BLOCK, new ItemAspects(Aspects.METALLUM, 67).add(Aspects.DESIDERIUM, 67));
        this.addItemAspect(Items.COAL_BLOCK, new ItemAspects(Aspects.POTENTIA, 67).add(Aspects.IGNIS, 67));
        this.addItemAspect(Items.NETHERRACK, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2));
        this.addItemAspect(Items.PRISMARINE_SHARD, new ItemAspects(Aspects.AQUA, 5)
                .add(Aspects.TERRA, 5));
        this.addItemAspect(Items.PRISMARINE_CRYSTALS, new ItemAspects(Aspects.AQUA, 5)
                .add(Aspects.LUX, 5).add(Aspects.VITREUS, 5));
        this.addItemAspect(Items.SEA_LANTERN, new ItemAspects(Aspects.AQUA, 33).add(Aspects.VITREUS, 18).add(Aspects.LUX, 18)
                .add(Aspects.TERRA, 15));
        this.addItemAspect(Items.TUFF, new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect(Items.CHEST, new ItemAspects(Aspects.HERBA, 18));
        this.addItemAspect(Items.GRASS_BLOCK, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 2));
        this.addItemAspect(Items.DIRT, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 2));
        this.addItemAspect(Items.PODZOL, new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA));
        this.addItemAspect(Items.BEDROCK, new ItemAspects(Aspects.VACOUS, 25).add(Aspects.PERDITIO, 25)
                .add(Aspects.TERRA, 25).add(Aspects.TENEBRAE, 25));
        this.addItemAspect(Items.SAND, new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.RED_SAND, new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.GRAVEL, new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 2));
        this.addItemAspect(Items.COBWEB, new ItemAspects(Aspects.VINCULUM, 5).add(Aspects.BESTIA));
        this.addItemAspect(Items.BROWN_MUSHROOM, new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2)
                .add(Aspects.TERRA, 2));
        this.addItemAspect(Items.RED_MUSHROOM, new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2)
                .add(Aspects.IGNIS, 2));
        this.addItemAspect(Items.SUGAR_CANE, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3)
                .add(Aspects.AER, 2));
        this.addItemAspect(Items.OBSIDIAN, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 5)
                .add(Aspects.TENEBRAE, 5));
        this.addItemAspect(Items.CHORUS_PLANT, new ItemAspects(Aspects.ALIENIS, 5).add(Aspects.HERBA, 5));
        this.addItemAspect(Items.CHORUS_FLOWER, new ItemAspects(Aspects.ALIENIS, 5).add(Aspects.HERBA, 5)
                .add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.ICE, new ItemAspects(Aspects.GELUM, 20));
        this.addItemAspect(Items.CACTUS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 5)
                .add(Aspects.AVERSIO));
        this.addItemAspect(Items.NETHERRACK, new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2));
        this.addItemAspect(Items.VINE, new ItemAspects(Aspects.HERBA, 5));
        this.addItemAspect(Items.LILY_PAD, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA));
        this.addItemAspect(Items.END_PORTAL_FRAME, new ItemAspects(Aspects.POTENTIA, 10).add(Aspects.MOTUS, 10)
                .add(Aspects.PRAECANTATIO, 5).add(Aspects.ALIENIS, 10));
        this.addItemAspect(Items.DRAGON_EGG, new ItemAspects(Aspects.ALIENIS, 15).add(Aspects.MOTUS, 15)
                .add(Aspects.TENEBRAE, 15).add(Aspects.PRAECANTATIO, 5).add(Aspects.BESTIA, 15));
        this.addItemAspect(Items.DIRT_PATH, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 2)
                .add(Aspects.ORDO, 2));
        this.addItemAspect(Items.SUNFLOWER, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect(Items.LILAC, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect(Items.ROSE_BUSH, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect(Items.PEONY, new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect(Items.TALL_GRASS, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect(Items.LARGE_FERN, new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect(Items.RED_CONCRETE, new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2)
                .add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect(Items.BLACK_CONCRETE, new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2)
                .add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect(Items.SADDLE, new ItemAspects(Aspects.BESTIA, 10).add(Aspects.MOTUS, 10)
                .add(Aspects.ORDO, 5));
        this.addItemAspect(Items.ELYTRA, new ItemAspects(Aspects.MOTUS, 15).add(Aspects.VOLATUS, 20));
        this.addItemAspect(Items.APPLE, new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS, 5));
        this.addItemAspect(Items.GOLD_INGOT, new ItemAspects(Aspects.METALLUM).add(Aspects.DESIDERIUM));
        this.addItemAspect(Items.STRING, new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO));
        this.addItemAspect(Items.FEATHER, new ItemAspects(Aspects.AER, 5).add(Aspects.VOLATUS, 5));
        this.addItemAspect(Items.GUNPOWDER, new ItemAspects(Aspects.IGNIS, 10).add(Aspects.PERDITIO, 10)
                .add(Aspects.ALKIMIA, 5));
        this.addItemAspect(Items.CHAINMAIL_HELMET, new ItemAspects(Aspects.METALLUM, 42).add(Aspects.PRAEMUNIO, 8));
        this.addItemAspect(Items.CHAINMAIL_CHESTPLATE, new ItemAspects(Aspects.METALLUM, 67).add(Aspects.PRAEMUNIO, 20));
        this.addItemAspect(Items.CHAINMAIL_LEGGINGS, new ItemAspects(Aspects.METALLUM, 58).add(Aspects.PRAEMUNIO, 16));
        this.addItemAspect(Items.CHAINMAIL_BOOTS, new ItemAspects(Aspects.METALLUM, 33).add(Aspects.PRAEMUNIO, 4));
        this.addItemAspect(Items.FLINT, new ItemAspects(Aspects.METALLUM, 11).add(Aspects.IGNIS, 10)
                .add(Aspects.TERRA, 3).add(Aspects.INSTRUMENTUM, 8));
        this.addItemAspect(Items.PORKCHOP, new ItemAspects(Aspects.BESTIA, 5).add(Aspects.VICTUS, 5)
                .add(Aspects.TERRA, 5));
        this.addItemAspect(Items.ENCHANTED_GOLDEN_APPLE, new ItemAspects(Aspects.VICTUS, 15)
                .add(Aspects.PRAEMUNIO, 15));
        this.addItemAspect(Items.WATER_BUCKET, new ItemAspects(Aspects.METALLUM, 33).add(Aspects.AQUA, 20)
                .add(Aspects.VACOUS, 5));
        this.addItemAspect(Items.LAVA_BUCKET, new ItemAspects(Aspects.METALLUM, 33).add(Aspects.IGNIS, 15)
                .add(Aspects.VACOUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.SNOWBALL, new ItemAspects(Aspects.GELUM));
        this.addItemAspect(Items.MILK_BUCKET, new ItemAspects(Aspects.METALLUM, 33).add(Aspects.AQUA, 5)
                .add(Aspects.VACOUS, 5).add(Aspects.VICTUS, 10).add(Aspects.BESTIA, 5));
        this.addItemAspect(Items.CLAY_BALL, new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect(Items.EGG, new ItemAspects(Aspects.VICTUS, 5).add(Aspects.BESTIA, 5));
        this.addItemAspect(Items.GLOWSTONE_DUST, new ItemAspects(Aspects.LUX, 10).add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.INK_SAC, new ItemAspects(Aspects.SENSUS, 5).add(Aspects.AQUA, 2)
                .add(Aspects.BESTIA, 2));
        this.addItemAspect(Items.COCOA_BEANS, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.POTENTIA, 2).add(Aspects.DESIDERIUM, 2));
        this.addItemAspect(Items.ORANGE_DYE, new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect(Items.MAGENTA_DYE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect(Items.LIGHT_BLUE_DYE, new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect(Items.YELLOW_DYE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect(Items.PINK_DYE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect(Items.LIGHT_GRAY_DYE, new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect(Items.RED_DYE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect(Items.BONE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA).add(Aspects.VICTUS, 2).add(Aspects.MORTUS));
        this.addItemAspect(Items.SUGAR, new ItemAspects(Aspects.HERBA, 3)
                .add(Aspects.AQUA, 2).add(Aspects.AER).add(Aspects.POTENTIA)
                .add(Aspects.DESIDERIUM));
        this.addItemAspect(Items.BEEF, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.CHICKEN, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.AER, 5));
        this.addItemAspect(Items.ROTTEN_FLESH, new ItemAspects(Aspects.VICTUS, 5)
                .add(Aspects.PERDITIO, 5).add(Aspects.HUMANUS, 5));
        this.addItemAspect(Items.ENDER_PEARL, new ItemAspects(Aspects.MOTUS, 15)
                .add(Aspects.ALIENIS, 10));
        this.addItemAspect(Items.BLAZE_ROD, new ItemAspects(Aspects.IGNIS, 15)
                .add(Aspects.MOTUS, 10));
        this.addItemAspect(Items.GHAST_TEAR, new ItemAspects(Aspects.IGNIS, 6)
                .add(Aspects.PERDITIO, 2).add(Aspects.POTENTIA, 2).add(Aspects.ALKIMIA, 2));
        this.addItemAspect(Items.NETHER_WART, new ItemAspects(Aspects.ALKIMIA, 3)
                .add(Aspects.HERBA).add(Aspects.VITIUM, 2));
        this.addItemAspect(Items.SPIDER_EYE, new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.BESTIA, 5).add(Aspects.MORTUS, 5));
        this.addItemAspect(Items.EXPERIENCE_BOTTLE, new ItemAspects(Aspects.COGNITIO, 20));
        this.addItemAspect(Items.WRITTEN_BOOK, new ItemAspects(Aspects.COGNITIO, 3)
                .add(Aspects.AQUA, 4).add(Aspects.HERBA, 4).add(Aspects.AER, 5)
                .add(Aspects.BESTIA, 3).add(Aspects.SENSUS, 3).add(Aspects.VOLATUS, 3));
        this.addItemAspect(Items.CARROT, new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect(Items.POTATO, new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.POISONOUS_POTATO, new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.MORTUS, 5));
        this.addItemAspect(Items.SKELETON_SKULL, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect(Items.WITHER_SKELETON_SKULL, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect(Items.PLAYER_HEAD, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.HUMANUS, 10));
        this.addItemAspect(Items.ZOMBIE_HEAD, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.HUMANUS, 10));
        this.addItemAspect(Items.CREEPER_HEAD, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.PERDITIO, 5).add(Aspects.IGNIS, 5));
        this.addItemAspect(Items.DRAGON_HEAD, new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.TENEBRAE, 10).add(Aspects.IGNIS, 10));
        this.addItemAspect(Items.NETHER_STAR, new ItemAspects(Aspects.ORDO, 20)
                .add(Aspects.ALIENIS, 20).add(Aspects.PRAECANTATIO, 20).add(Aspects.AURAM, 20));
        this.addItemAspect(Items.RABBIT, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.RABBIT_STEW, new ItemAspects(Aspects.HERBA, 12)
                .add(Aspects.VICTUS, 11).add(Aspects.BESTIA, 3).add(Aspects.SENSUS, 3)
                .add(Aspects.VACOUS, 3).add(Aspects.IGNIS).add(Aspects.TENEBRAE));
        this.addItemAspect(Items.RABBIT_FOOT, new ItemAspects(Aspects.MOTUS, 10)
                .add(Aspects.BESTIA, 5).add(Aspects.PRAEMUNIO, 5).add(Aspects.ALKIMIA, 5));
        this.addItemAspect(Items.RABBIT_HIDE, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.PRAEMUNIO, 2));
        this.addItemAspect(Items.GOLDEN_HORSE_ARMOR, new ItemAspects(Aspects.METALLUM, 10)
                .add(Aspects.PRAEMUNIO, 15).add(Aspects.BESTIA, 5));
        this.addItemAspect(Items.DIAMOND_HORSE_ARMOR, new ItemAspects(Aspects.PRAEMUNIO, 20)
                .add(Aspects.BESTIA, 5).add(Aspects.VITREUS, 15));
        this.addItemAspect(Items.NAME_TAG, new ItemAspects(Aspects.BESTIA, 10)
                .add(Aspects.COGNITIO, 10));
        this.addItemAspect(Items.MUTTON, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.CHORUS_FRUIT, new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.SENSUS, 5).add(Aspects.ALIENIS, 5));
        this.addItemAspect(Items.BEETROOT, new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.DESIDERIUM, 5));
        this.addItemAspect(Items.DRAGON_BREATH, new ItemAspects(Aspects.IGNIS, 10)
                .add(Aspects.PERDITIO, 10).add(Aspects.TENEBRAE, 10).add(Aspects.ALKIMIA, 10));
        this.addItemAspect(Items.TOTEM_OF_UNDYING, new ItemAspects(Aspects.VICTUS, 25)
                .add(Aspects.ORDO, 10).add(Aspects.PERDITIO, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect(Items.SHULKER_SHELL, new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VACOUS, 5).add(Aspects.ALIENIS, 5).add(Aspects.PRAEMUNIO, 10));
        this.addItemAspect(Items.MUSIC_DISC_13, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.AQUA, 5));
        this.addItemAspect(Items.MUSIC_DISC_CAT, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.BESTIA, 5));
        this.addItemAspect(Items.MUSIC_DISC_BLOCKS, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.INSTRUMENTUM, 5));
        this.addItemAspect(Items.MUSIC_DISC_CHIRP, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect(Items.MUSIC_DISC_FAR, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.ALIENIS, 5));
        this.addItemAspect(Items.MUSIC_DISC_MALL, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.HUMANUS, 5));
        this.addItemAspect(Items.MUSIC_DISC_MELLOHI, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.FABRICO, 5));
        this.addItemAspect(Items.MUSIC_DISC_STAL, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.TENEBRAE, 5));
        this.addItemAspect(Items.MUSIC_DISC_STRAD, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.POTENTIA, 5));
        this.addItemAspect(Items.MUSIC_DISC_WARD, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.VICTUS, 5));
        this.addItemAspect(Items.MUSIC_DISC_11, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10));
        this.addItemAspect(Items.MUSIC_DISC_WAIT, new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.VINCULUM, 5));
        this.addItemAspect(ThaumcraftItems.THAUMONOMICON, new ItemAspects(Aspects.AER)
                .add(Aspects.TERRA, 2).add(Aspects.IGNIS, 3).add(Aspects.AQUA, 4)
                .add(Aspects.ORDO, 5).add(Aspects.PERDITIO, 6).add(Aspects.VACOUS, 7)
                .add(Aspects.LUX, 8).add(Aspects.MOTUS, 9).add(Aspects.GELUM, 10)
                .add(Aspects.VITREUS, 11).add(Aspects.METALLUM, 12).add(Aspects.VICTUS, 13)
                .add(Aspects.MORTUS, 14).add(Aspects.POTENTIA, 15).add(Aspects.PERMUTATIO, 16)
                .add(Aspects.PRAECANTATIO, 17).add(Aspects.AURAM, 18).add(Aspects.ALKIMIA, 19)
                .add(Aspects.VITIUM, 20).add(Aspects.TENEBRAE, 21).add(Aspects.ALIENIS, 22)
                .add(Aspects.VOLATUS, 23).add(Aspects.HERBA, 24).add(Aspects.INSTRUMENTUM, 25)
                .add(Aspects.FABRICO, 26).add(Aspects.MACHINA, 27).add(Aspects.VINCULUM, 28)
                .add(Aspects.SPIRITUS, 29).add(Aspects.COGNITIO, 30).add(Aspects.SENSUS, 31)
                .add(Aspects.AVERSIO, 32).add(Aspects.PRAEMUNIO, 33).add(Aspects.DESIDERIUM, 34)
                .add(Aspects.EXANIMIS, 35).add(Aspects.BESTIA, 36).add(Aspects.HUMANUS, 37)
                .add(Aspects.ECHO, 38).add(Aspects.INFERNO, 39).add(Aspects.TEMPUS, 40));

        for(Aspects aspect : Aspects.values()) {
            this.addItemAspect(ThaumcraftItems.CRYSTAL, CrystalHelper.buildType(aspect), new ItemAspects(aspect));
        }
    }
}