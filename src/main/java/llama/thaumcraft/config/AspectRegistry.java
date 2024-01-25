package llama.thaumcraft.config;

import llama.thaumcraft.Aspects;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.items.CrystalHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AspectRegistry {
    public static final Map<Map<String, NbtCompound>, Map<Aspects, Integer>> ITEMS = new LinkedHashMap<>();

    private void addItemAspect(String id, NbtCompound nbtCompound, ItemAspects itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(id, nbtCompound);
        }}, itemAspects.getAspects());
    }

    private void addItemAspect(String id, ItemAspects itemAspects) {
        ITEMS.put(new LinkedHashMap<>(){{
            put(id, new NbtCompound());
        }}, itemAspects.getAspects());
    }

    public static Map<Aspects, Integer> getAspectsByItem(Item item) {
        Map<String, NbtCompound> key = new ConcurrentHashMap<>(){{
            put(Registries.ITEM.getId(item).toString(), new NbtCompound());
        }};

        return ITEMS.get(key);
    }

    public static List<NbtCompound> getNbtCompoundsByItem(Item item) {

        String itemId = Registries.ITEM.getId(item).toString();

        List<NbtCompound> nbtList = new ArrayList<>();

        for (Map.Entry<Map<String, NbtCompound>, Map<Aspects, Integer>> entry : ITEMS.entrySet()) {

            Map<String, NbtCompound> itemMap = entry.getKey();

            if (itemMap.containsKey(itemId)) {
                nbtList.add(itemMap.get(itemId));
            }
        }

        return nbtList;
    }


    public static Map<Aspects, Integer> getAspectsByItem(Item item, NbtCompound nbt) {
        Map<String, NbtCompound> key = new ConcurrentHashMap<>(){{
            put(Registries.ITEM.getId(item).toString(), nbt == null ? new NbtCompound() : nbt);
        }};

        return ITEMS.get(key);
    }

    public static Map<Aspects, Integer> getAspectsByItemStack(ItemStack stack) {
        String itemId = Registries.ITEM.getId(stack.getItem()).toString();

        List<NbtCompound> nbts = getNbtCompoundsByItem(stack.getItem());

        if(nbts.equals(new ArrayList<NbtCompound>())) {
            return null;
        }

        if(stack.getNbt() == null) {
            return getAspectsByItem(stack.getItem());
        }

        Map<String, NbtCompound> key = Collections.singletonMap(itemId, nbts.get(0));
        for(NbtCompound nbt : nbts) {
            if(stack.getNbt().equals(nbt)) {
                key = Collections.singletonMap(itemId, stack.getNbt());
            }
        }
        return ITEMS.get(key);
    }

    public static void register() {
        Thaumcraft.LOGGER.debug("Registering aspects in items for: "+Thaumcraft.MOD_NAME);

        new AspectRegistry().registryItems();
    }

    public void registryItems() {
        // - - - - - SAPLINGS - - - - -
        this.addItemAspect("minecraft:oak_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:spruce_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:birch_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:jungle_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:acacia_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:dark_oak_sapling", Thaumcraft.saplings);
        this.addItemAspect("minecraft:mangrove_propagule", Thaumcraft.saplings);
        this.addItemAspect("minecraft:cherry_sapling", Thaumcraft.saplings);
        // - - - - - LOGS - - - - -
        this.addItemAspect("minecraft:oak_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:spruce_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:birch_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:jungle_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:acacia_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:dark_oak_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:mangrove_log", Thaumcraft.logs);
        this.addItemAspect("minecraft:cherry_log", Thaumcraft.logs);
        // - - - - - STRIPPED LOGS - - - - -
        this.addItemAspect("minecraft:stripped_oak_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_spruce_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_birch_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_jungle_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_acacia_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_dark_oak_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_mangrove_log", Thaumcraft.stripped_logs);
        this.addItemAspect("minecraft:stripped_cherry_log", Thaumcraft.stripped_logs);
        // - - - - - WOODS - - - - -
        this.addItemAspect("minecraft:oak_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:spruce_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:birch_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:jungle_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:acacia_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:dark_oak_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:mangrove_wood", Thaumcraft.woods);
        this.addItemAspect("minecraft:cherry_wood", Thaumcraft.woods);
        // - - - - - STRIPPED WOODS - - - - -
        this.addItemAspect("minecraft:stripped_oak_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_spruce_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_birch_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_jungle_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_acacia_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_dark_oak_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_mangrove_wood", Thaumcraft.stripped_woods);
        this.addItemAspect("minecraft:stripped_cherry_wood", Thaumcraft.stripped_woods);
        // - - - - - PLANKS - - - - -
        this.addItemAspect("minecraft:oak_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:spruce_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:birch_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:jungle_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:acacia_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:dark_oak_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:mangrove_planks", Thaumcraft.planks);
        this.addItemAspect("minecraft:cherry_planks", Thaumcraft.planks);
        // - - - - - STAIRS - - - - -
        this.addItemAspect("minecraft:oak_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:spruce_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:birch_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:jungle_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:acacia_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:dark_oak_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:mangrove_stairs", Thaumcraft.stairs);
        this.addItemAspect("minecraft:cherry_stairs", Thaumcraft.stairs);
        // - - - - - SLABS - - - - -
        this.addItemAspect("minecraft:oak_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:spruce_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:birch_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:jungle_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:acacia_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:dark_oak_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:mangrove_slab", Thaumcraft.slabs);
        this.addItemAspect("minecraft:cherry_slab", Thaumcraft.slabs);
        // - - - - - FENCE - - - - -
        this.addItemAspect("minecraft:oak_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:spruce_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:birch_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:jungle_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:acacia_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:dark_oak_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:mangrove_fence", Thaumcraft.fences);
        this.addItemAspect("minecraft:cherry_fence", Thaumcraft.fences);
        // - - - - - FENCE GATE - - - - -
        this.addItemAspect("minecraft:oak_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:spruce_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:birch_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:jungle_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:acacia_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:dark_oak_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:mangrove_fence_gate", Thaumcraft.fences_gates);
        this.addItemAspect("minecraft:cherry_fence_gate", Thaumcraft.fences_gates);
        // - - - - - DOORS - - - - -
        this.addItemAspect("minecraft:oak_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:spruce_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:birch_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:jungle_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:acacia_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:dark_oak_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:mangrove_door", Thaumcraft.doors);
        this.addItemAspect("minecraft:cherry_door", Thaumcraft.doors);
        // - - - - - TRAPDOORS - - - - -
        this.addItemAspect("minecraft:oak_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:spruce_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:birch_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:jungle_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:acacia_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:dark_oak_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:mangrove_trapdoor", Thaumcraft.trapdoors);
        this.addItemAspect("minecraft:cherry_trapdoor", Thaumcraft.trapdoors);
        // - - - - - PLATE - - - - -
        this.addItemAspect("minecraft:oak_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:spruce_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:birch_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:jungle_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:acacia_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:dark_oak_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:mangrove_pressure_plate", Thaumcraft.plates);
        this.addItemAspect("minecraft:cherry_pressure_plate", Thaumcraft.plates);
        // - - - - - BUTTONS - - - - -
        this.addItemAspect("minecraft:oak_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:spruce_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:birch_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:jungle_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:acacia_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:dark_oak_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:mangrove_button", Thaumcraft.buttons);
        this.addItemAspect("minecraft:cherry_button", Thaumcraft.buttons);
        // - - - - - SIGNS - - - - -
        this.addItemAspect("minecraft:oak_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:spruce_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:birch_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:jungle_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:acacia_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:dark_oak_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:mangrove_sign", Thaumcraft.signs);
        this.addItemAspect("minecraft:cherry_sign", Thaumcraft.signs);
        // - - - - - HANGING SIGNS - - - - -
        this.addItemAspect("minecraft:oak_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:spruce_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:birch_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:jungle_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:acacia_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:dark_oak_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:mangrove_hanging_sign", Thaumcraft.hanging_signs);
        this.addItemAspect("minecraft:cherry_hanging_sign", Thaumcraft.hanging_signs);
        // - - - - - LEAVES - - - - -
        this.addItemAspect("minecraft:oak_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:spruce_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:birch_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:jungle_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:acacia_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:dark_oak_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:mangrove_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:cherry_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:azalea_leaves", Thaumcraft.leaves);
        this.addItemAspect("minecraft:flowering_azalea_leaves", Thaumcraft.leaves.add(Aspects.VICTUS));
        // - - - - - BOATS - - - - -
        this.addItemAspect("minecraft:oak_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:spruce_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:birch_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:jungle_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:acacia_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:dark_oak_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:mangrove_boat", Thaumcraft.boats);
        this.addItemAspect("minecraft:cherry_boat", Thaumcraft.boats);
        // - - - - - CHEST BOATS - - - - -
        this.addItemAspect("minecraft:oak_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:spruce_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:birch_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:jungle_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:acacia_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:dark_oak_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:mangrove_chest_boat", Thaumcraft.chest_boats);
        this.addItemAspect("minecraft:cherry_chest_boat", Thaumcraft.chest_boats);
        // - - - - - FLOWERS - - - - -
        this.addItemAspect("minecraft:dandelion", Thaumcraft.flowers);
        this.addItemAspect("minecraft:poppy", Thaumcraft.flowers);
        this.addItemAspect("minecraft:blue_orchid", Thaumcraft.flowers);
        this.addItemAspect("minecraft:allium", Thaumcraft.flowers);
        this.addItemAspect("minecraft:azure_bluet", Thaumcraft.flowers);
        this.addItemAspect("minecraft:red_tulip", Thaumcraft.flowers);
        this.addItemAspect("minecraft:orange_tulip", Thaumcraft.flowers);
        this.addItemAspect("minecraft:white_tulip", Thaumcraft.flowers);
        this.addItemAspect("minecraft:pink_tulip", Thaumcraft.flowers);
        this.addItemAspect("minecraft:oxeye_daisy", Thaumcraft.flowers);
        this.addItemAspect("minecraft:cornflower", Thaumcraft.flowers);
        this.addItemAspect("minecraft:lily_of_the_valley", Thaumcraft.flowers);
        this.addItemAspect("minecraft:torchflower", Thaumcraft.flowers.add(Aspects.LUX));
        this.addItemAspect("minecraft:wither_rose", Thaumcraft.flowers.add(Aspects.MORTUS, 5));
        this.addItemAspect("minecraft:pink_petals", Thaumcraft.flowers);
        this.addItemAspect("minecraft:spore_blossom", Thaumcraft.flowers.add(Aspects.TEMPUS, 5));
        this.addItemAspect("minecraft:pitcher_plant", Thaumcraft.flowers.add(Aspects.TEMPUS, 15));
        // - - - - - SHULKER_BOXS - - - - -
        this.addItemAspect("minecraft:shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:white_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:light_gray_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:gray_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:black_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:brown_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:red_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:orange_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:yellow_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:lime_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:green_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:cyan_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:light_blue_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:blue_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:purple_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:magenta_shulker_box", Thaumcraft.shulkerBoxs);
        this.addItemAspect("minecraft:pink_shulker_box", Thaumcraft.shulkerBoxs);
        // - - - - - FISHS - - - - -
        this.addItemAspect("minecraft:cod", Thaumcraft.fishs);
        this.addItemAspect("minecraft:salmon", Thaumcraft.fishs);
        this.addItemAspect("minecraft:tropical_fish", Thaumcraft.fishs);
        this.addItemAspect("minecraft:pufferfish", Thaumcraft.fishs);
        // - - - - - STONE ITEMS - - - - -
        this.addItemAspect("minecraft:stone", new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:stone_stairs", new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:stone_slab", new ItemAspects(Aspects.TERRA, 1));
        this.addItemAspect("minecraft:stone_pressure_plate", new ItemAspects(Aspects.TERRA, 7));
        this.addItemAspect("minecraft:stone_button", new ItemAspects(Aspects.TERRA, 3).add(Aspects.MACHINA, 5));
        // - - - - - COBBLESTONES ITEMS - - - - -
        this.addItemAspect("minecraft:cobblestone", new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO));
        this.addItemAspect("minecraft:cobblestone_stairs", new ItemAspects(Aspects.TERRA, 5).add(Aspects.PERDITIO));
        this.addItemAspect("minecraft:cobblestone_slab", new ItemAspects(Aspects.TERRA));
        this.addItemAspect("minecraft:cobblestone_wall", new ItemAspects(Aspects.TERRA, 3));
        // - - - - - MOSSY COBBLESTONES ITEMS - - - - -
        this.addItemAspect("minecraft:mossy_cobblestone", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 3)
                .add(Aspects.PERDITIO));
        this.addItemAspect("minecraft:mossy_cobblestone_stairs", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 3)
                .add(Aspects.PERDITIO));
        this.addItemAspect("minecraft:mossy_cobblestone_slab", new ItemAspects(Aspects.TERRA).add(Aspects.HERBA));
        this.addItemAspect("minecraft:mossy_cobblestone_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA, 2));
        // - - - - - SMOOTH ITEMS - - - - -
        this.addItemAspect("minecraft:smooth_stone", new ItemAspects(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:smooth_stone_slab", new ItemAspects(Aspects.TERRA));
        // - - - - - STONE BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:stone_bricks", new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect("minecraft:cracked_stone_bricks", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:stone_brick_stairs", new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect("minecraft:stone_brick_slab", new ItemAspects(Aspects.TERRA));
        this.addItemAspect("minecraft:stone_brick_wall", new ItemAspects(Aspects.TERRA, 2));
        this.addItemAspect("minecraft:chiseled_stone_bricks", new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));
        // - - - - - MOSSY STONE BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:mossy_stone_bricks", new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA));
        this.addItemAspect("minecraft:mossy_stone_brick_stairs", new ItemAspects(Aspects.TERRA, 3).add(Aspects.HERBA));
        this.addItemAspect("minecraft:mossy_stone_brick_slab", new ItemAspects(Aspects.TERRA).add(Aspects.HERBA));
        this.addItemAspect("minecraft:mossy_stone_brick_wall", new ItemAspects(Aspects.TERRA, 1).add(Aspects.HERBA, 2));
        // - - - - - BREEDS ITEMS - - - - -
        //GRANITE
        this.addItemAspect("minecraft:granite", Thaumcraft.breeds);
        this.addItemAspect("minecraft:granite_stairs", Thaumcraft.breeds_stairs);
        this.addItemAspect("minecraft:granite_slab", Thaumcraft.breeds_slab);
        this.addItemAspect("minecraft:granite_wall", Thaumcraft.breeds_wall);
        this.addItemAspect("minecraft:polished_granite", Thaumcraft.polished_breeds);
        this.addItemAspect("minecraft:polished_granite_stairs", Thaumcraft.polished_breeds_stairs);
        this.addItemAspect("minecraft:polished_granite_slab", Thaumcraft.polished_breeds_slab);
        //DIORITE
        this.addItemAspect("minecraft:diorite", Thaumcraft.breeds);
        this.addItemAspect("minecraft:diorite_stairs", Thaumcraft.breeds_stairs);
        this.addItemAspect("minecraft:diorite_slab", Thaumcraft.breeds_slab);
        this.addItemAspect("minecraft:diorite_wall", Thaumcraft.breeds_wall);
        this.addItemAspect("minecraft:polished_diorite", Thaumcraft.polished_breeds);
        this.addItemAspect("minecraft:polished_diorite_stairs", Thaumcraft.polished_breeds_stairs);
        this.addItemAspect("minecraft:polished_diorite_slab", Thaumcraft.polished_breeds_slab);
        //ANDESITE
        this.addItemAspect("minecraft:andesite", Thaumcraft.breeds);
        this.addItemAspect("minecraft:andesite_stairs", Thaumcraft.breeds_stairs);
        this.addItemAspect("minecraft:andesite_slab", Thaumcraft.breeds_slab);
        this.addItemAspect("minecraft:andesite_wall", Thaumcraft.breeds_wall);
        this.addItemAspect("minecraft:polished_andesite", Thaumcraft.polished_breeds);
        this.addItemAspect("minecraft:polished_andesite_stairs", Thaumcraft.polished_breeds_stairs);
        this.addItemAspect("minecraft:polished_andesite_slab", Thaumcraft.polished_breeds_slab);
        // - - - - - DEEPSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:deepslate", new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:cobbled_deepslate", new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2).add(Aspects.PERDITIO, 1));
        this.addItemAspect("minecraft:cobbled_deepslate_stairs", new ItemAspects(Aspects.TERRA, 8).add(Aspects.ORDO, 2).add(Aspects.PERDITIO, 1));
        this.addItemAspect("minecraft:cobbled_deepslate_slab", new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cobbled_deepslate_wall", new ItemAspects(Aspects.TERRA, 4).add(Aspects.ORDO));

        this.addItemAspect("minecraft:chiseled_deepslate", new ItemAspects(Aspects.TERRA, 4).add(Aspects.ORDO, 4));

        this.addItemAspect("minecraft:polished_deepslate", new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:polished_deepslate_stairs", new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:polished_deepslate_slab", new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));
        this.addItemAspect("minecraft:polished_deepslate_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));

        this.addItemAspect("minecraft:deepslate_bricks", new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cracked_deepslate_bricks", new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO, 2).add(Aspects.IGNIS, 1));
        this.addItemAspect("minecraft:deepslate_brick_stairs", new ItemAspects(Aspects.TERRA, 6).add(Aspects.ORDO));
        this.addItemAspect("minecraft:deepslate_brick_slab", new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));
        this.addItemAspect("minecraft:deepslate_brick_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO));

        this.addItemAspect("minecraft:deepslate_tiles", new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cracked_deepslate_tiles", new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO).add(Aspects.IGNIS, 1));
        this.addItemAspect("minecraft:deepslate_tile_stairs", new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect("minecraft:deepslate_tile_slab", new ItemAspects(Aspects.TERRA, 1).add(Aspects.ORDO));
        this.addItemAspect("minecraft:deepslate_tile_wall", new ItemAspects(Aspects.TERRA, 2).add(Aspects.ORDO));

        this.addItemAspect("minecraft:reinforced_deepslate", new ItemAspects(Aspects.TERRA, 20).add(Aspects.TEMPUS, 5).add(Aspects.AURAM, 20)
                .add(Aspects.TENEBRAE, 20).add(Aspects.ALIENIS, 20));
        // - - - - - BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:brick", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:bricks", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15).add(Aspects.IGNIS, 3));
        this.addItemAspect("minecraft:brick_stairs", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15).add(Aspects.IGNIS, 3));
        this.addItemAspect("minecraft:brick_stairs", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15).add(Aspects.IGNIS, 3));
        this.addItemAspect("minecraft:brick_slab", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:brick_wall", new ItemAspects(Aspects.TERRA, 7).add(Aspects.AQUA, 7).add(Aspects.IGNIS, 2));
        // - - - - - BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:mud", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect("minecraft:packed_mud", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5)
                .add(Aspects.HERBA, 3).add(Aspects.VICTUS, 3));
        this.addItemAspect("minecraft:mud_bricks", new ItemAspects(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect("minecraft:mud_brick_stairs", new ItemAspects(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect("minecraft:mud_brick_slab", new ItemAspects(Aspects.TERRA, 1).add(Aspects.AQUA, 1));
        this.addItemAspect("minecraft:mud_brick_wall", new ItemAspects(Aspects.TERRA, 2).add(Aspects.AQUA, 2));
        // - - - - - SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:sandstone", Thaumcraft.sandstones);
        this.addItemAspect("minecraft:sandstone_stairs", Thaumcraft.sandstone_stairs);
        this.addItemAspect("minecraft:sandstone_slab", Thaumcraft.sandstone_slabs);
        this.addItemAspect("minecraft:sandstone_wall", Thaumcraft.sandstone_walls);
        this.addItemAspect("minecraft:chiseled_sandstone", Thaumcraft.chiseled_sandstones);
        // - - - - - SMOOTH SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:smooth_sandstone", Thaumcraft.sandstones.add(Aspects.ORDO));
        this.addItemAspect("minecraft:smooth_sandstone_stairs", Thaumcraft.sandstone_stairs.add(Aspects.ORDO));
        this.addItemAspect("minecraft:smooth_sandstone_slab", Thaumcraft.sandstone_slabs.add(Aspects.ORDO));
        // - - - - - CUT SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:cut_sandstone", Thaumcraft.cut_sandstones);
        this.addItemAspect("minecraft:cut_sandstone_slab", Thaumcraft.cut_sandstone_slabs);
        // - - - - - RED SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:red_sandstone", Thaumcraft.sandstones);
        this.addItemAspect("minecraft:red_sandstone_stairs", Thaumcraft.sandstone_stairs);
        this.addItemAspect("minecraft:red_sandstone_slab", Thaumcraft.sandstone_slabs);
        this.addItemAspect("minecraft:red_sandstone_wall", Thaumcraft.sandstone_walls);
        this.addItemAspect("minecraft:chiseled_red_sandstone", Thaumcraft.chiseled_sandstones);
        // - - - - - RED SMOOTH SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:smooth_red_sandstone", Thaumcraft.sandstones.add(Aspects.ORDO));
        this.addItemAspect("minecraft:smooth_red_sandstone_stairs", Thaumcraft.sandstone_stairs.add(Aspects.ORDO));
        this.addItemAspect("minecraft:smooth_red_sandstone_slab", Thaumcraft.sandstone_slabs.add(Aspects.ORDO));
        // - - - - - REDCUT SANDSTONE ITEMS - - - - -
        this.addItemAspect("minecraft:cut_red_sandstone", Thaumcraft.cut_sandstones);
        this.addItemAspect("minecraft:cut_red_sandstone_slab", Thaumcraft.cut_sandstone_slabs);
        // - - - - - PRISMARINE ITEMS - - - - -
        this.addItemAspect("minecraft:prismarine", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect("minecraft:prismarine_stairs", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect("minecraft:prismarine_slab", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect("minecraft:prismarine_wall", new ItemAspects(Aspects.TERRA, 7).add(Aspects.AQUA, 7));
        // - - - - - PRISMARINE BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:prismarine_bricks", new ItemAspects(Aspects.TERRA, 33).add(Aspects.AQUA, 33));
        this.addItemAspect("minecraft:prismarine_brick_stairs", new ItemAspects(Aspects.TERRA, 33).add(Aspects.AQUA, 33));
        this.addItemAspect("minecraft:prismarine_brick_slab", new ItemAspects(Aspects.TERRA, 16).add(Aspects.AQUA, 16));
        // - - - - - DARK BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:dark_prismarine", new ItemAspects(Aspects.TERRA, 31).add(Aspects.AQUA, 31).add(Aspects.SENSUS, 3)
                .add(Aspects.BESTIA));
        this.addItemAspect("minecraft:dark_prismarine_stairs", new ItemAspects(Aspects.TERRA, 31).add(Aspects.AQUA, 31).add(Aspects.SENSUS, 3)
                .add(Aspects.BESTIA));
        this.addItemAspect("minecraft:dark_prismarine_slab", new ItemAspects(Aspects.TERRA, 6).add(Aspects.AQUA, 6).add(Aspects.SENSUS, 1)
                .add(Aspects.BESTIA));
        // - - - - - NETHER BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:nether_bricks", new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 9).add(Aspects.ORDO, 3));
        this.addItemAspect("minecraft:cracked_nether_bricks", new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 10).add(Aspects.ORDO, 3));
        this.addItemAspect("minecraft:nether_brick_stairs", new ItemAspects(Aspects.TERRA, 15).add(Aspects.IGNIS, 10).add(Aspects.ORDO, 3));
        this.addItemAspect("minecraft:nether_brick_slab", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO, 1));
        this.addItemAspect("minecraft:nether_brick_wall", new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 7).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:nether_brick_fence", new ItemAspects(Aspects.TERRA, 11).add(Aspects.IGNIS, 6).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:chiseled_nether_bricks", new ItemAspects(Aspects.TERRA, 11).add(Aspects.IGNIS, 5).add(Aspects.ORDO, 3));
        // - - - - - RED NETHER BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:red_nether_bricks", new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 4).add(Aspects.ALKIMIA, 4)
                .add(Aspects.VITIUM, 3).add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect("minecraft:red_nether_brick_stairs", new ItemAspects(Aspects.TERRA, 7).add(Aspects.IGNIS, 4).add(Aspects.ALKIMIA, 4)
                .add(Aspects.VITIUM, 3).add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect("minecraft:red_nether_brick_slab", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.ALKIMIA).add(Aspects.VITIUM)
                .add(Aspects.ORDO).add(Aspects.HERBA));
        this.addItemAspect("minecraft:red_nether_brick_wall", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO, 1));
        // - - - - - BASALT ITEMS - - - - -
        this.addItemAspect("minecraft:basalt", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:smooth_basalt", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 3).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:polished_basalt", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.ORDO));
        // - - - - - BLACK STONE ITEMS - - - - -
        this.addItemAspect("minecraft:blackstone", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:gilded_blackstone", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS)
                .add(Aspects.METALLUM, 5).add(Aspects.DESIDERIUM, 5));
        this.addItemAspect("minecraft:blackstone_stairs", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:blackstone_slab", new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:blackstone_stairs", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS).add(Aspects.ORDO));
        this.addItemAspect("minecraft:blackstone_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS, 2).add(Aspects.VACOUS).add(Aspects.ORDO));
        // - - - - - POLISHED BLACK STONE ITEMS - - - - -
        this.addItemAspect("minecraft:chiseled_polished_blackstone", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:chiseled_blackstone", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_stairs", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_slab", new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_pressure_plate", new ItemAspects(Aspects.TERRA, 2).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_button", new ItemAspects(Aspects.TERRA, 2).add(Aspects.MACHINA, 5).add(Aspects.IGNIS).add(Aspects.VACOUS));
        // - - - - - POLISHED BLACK STONE BRICKS ITEMS - - - - -
        this.addItemAspect("minecraft:polished_blackstone_bricks", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_brick_stairs", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_brick_slab", new ItemAspects(Aspects.TERRA).add(Aspects.IGNIS).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:polished_blackstone_brick_wall", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS, 2).add(Aspects.VACOUS));
        this.addItemAspect("minecraft:cracked_polished_blackstone_bricks", new ItemAspects(Aspects.TERRA, 2).add(Aspects.IGNIS, 3).add(Aspects.VACOUS));
        // - - - - - END STONE ITEMS - - - - -
        this.addItemAspect("minecraft:end_stone", new ItemAspects(Aspects.TERRA, 5).add(Aspects.TENEBRAE, 5));
        this.addItemAspect("minecraft:end_stone_bricks", new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        this.addItemAspect("minecraft:end_stone_brick_stairs", new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        this.addItemAspect("minecraft:end_stone_brick_slab", new ItemAspects(Aspects.TERRA).add(Aspects.TENEBRAE));
        this.addItemAspect("minecraft:end_stone_brick_wall", new ItemAspects(Aspects.TERRA, 3).add(Aspects.TENEBRAE, 3));
        // - - - - - PURPUR ITEMS - - - - -
        this.addItemAspect("minecraft:purpur_block", new ItemAspects(Aspects.ALIENIS, 3).add(Aspects.SENSUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:purpur_stairs", new ItemAspects(Aspects.ALIENIS, 3).add(Aspects.SENSUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:purpur_slab", new ItemAspects(Aspects.ALIENIS).add(Aspects.SENSUS).add(Aspects.HERBA));
        this.addItemAspect("minecraft:purpur_block_stairs", new ItemAspects(Aspects.ALIENIS, 3).add(Aspects.SENSUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:purpur_slab", new ItemAspects(Aspects.ALIENIS).add(Aspects.SENSUS).add(Aspects.HERBA));
        this.addItemAspect("minecraft:purpur_pillar", new ItemAspects(Aspects.ALIENIS).add(Aspects.SENSUS).add(Aspects.HERBA));
        // - - - - - IRON ITEMS - - - - -
        this.addItemAspect("minecraft:iron_ingot", new ItemAspects(Aspects.METALLUM, 15));
        this.addItemAspect("minecraft:iron_nugget", new ItemAspects(Aspects.METALLUM, 1));
        this.addItemAspect("minecraft:chain", new ItemAspects(Aspects.METALLUM, 16));
        this.addItemAspect("minecraft:heavy_weighted_pressure_plate", new ItemAspects(Aspects.METALLUM, 22).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:iron_trapdoor", new ItemAspects(Aspects.METALLUM, 45));
        this.addItemAspect("minecraft:iron_door", new ItemAspects(Aspects.METALLUM, 22).add(Aspects.VINCULUM, 5).add(Aspects.MACHINA, 5));
        this.addItemAspect("minecraft:iron_bars", new ItemAspects(Aspects.METALLUM, 4));
        this.addItemAspect("minecraft:iron_block", new ItemAspects(Aspects.METALLUM, 101));
        this.addItemAspect("minecraft:iron_horse_armor", new ItemAspects(Aspects.METALLUM, 15));
        // - - - - - NETHERITE ITEMS - - - - -
        this.addItemAspect("minecraft:ancient_debris", new ItemAspects(Aspects.METALLUM, 25).add(Aspects.TEMPUS, 15).add(Aspects.IGNIS, 33));
        this.addItemAspect("minecraft:netherite_scrap", new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TEMPUS, 10).add(Aspects.IGNIS, 25));
        this.addItemAspect("minecraft:netherite_ingot", new ItemAspects(Aspects.METALLUM, 75).add(Aspects.TEMPUS, 40).add(Aspects.IGNIS, 50)
                .add(Aspects.DESIDERIUM, 30));
        this.addItemAspect("minecraft:netherite_block", new ItemAspects(Aspects.METALLUM, 525).add(Aspects.TEMPUS, 280).add(Aspects.IGNIS, 350)
                .add(Aspects.DESIDERIUM, 210));
        // - - - - - QUARTZ ITEMS - - - - -
        this.addItemAspect("minecraft:quartz_block", new ItemAspects(Aspects.VITREUS, 15));
        this.addItemAspect("minecraft:quartz_stairs", new ItemAspects(Aspects.VITREUS, 15));
        this.addItemAspect("minecraft:quartz_slab", new ItemAspects(Aspects.VITREUS, 3));
        this.addItemAspect("minecraft:chiseled_quartz_block", new ItemAspects(Aspects.VITREUS, 7));
        this.addItemAspect("minecraft:quartz_bricks", new ItemAspects(Aspects.VITREUS, 10));
        this.addItemAspect("minecraft:quartz_pillar", new ItemAspects(Aspects.VITREUS, 11));
        this.addItemAspect("minecraft:smooth_quartz", new ItemAspects(Aspects.VITREUS, 9).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:smooth_quartz_stairs", new ItemAspects(Aspects.VITREUS, 9).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:smooth_quartz_slab", new ItemAspects(Aspects.VITREUS, 3).add(Aspects.IGNIS));
        // - - - - - COPPER ITEMS - - - - -
        this.addItemAspect("minecraft:copper_block", new ItemAspects(Aspects.METALLUM, 50));
        this.addItemAspect("minecraft:cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO));
        this.addItemAspect("minecraft:exposed_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS));
        this.addItemAspect("minecraft:exposed_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect("minecraft:exposed_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect("minecraft:exposed_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS));
        this.addItemAspect("minecraft:weathered_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 3));
        this.addItemAspect("minecraft:weathered_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect("minecraft:weathered_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect("minecraft:weathered_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 3));
        this.addItemAspect("minecraft:oxidized_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 5));
        this.addItemAspect("minecraft:oxidized_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        this.addItemAspect("minecraft:oxidized_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        this.addItemAspect("minecraft:oxidized_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 5));
        // - - - - - WAXED COPPER ITEMS - - - - -
        this.addItemAspect("minecraft:waxed_copper_block", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_exposed_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_exposed_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_exposed_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_exposed_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_weathered_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_weathered_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_weathered_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_weathered_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 3).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_oxidized_copper", new ItemAspects(Aspects.METALLUM, 50).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_oxidized_cut_copper", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_oxidized_cut_copper_stairs", new ItemAspects(Aspects.METALLUM, 35).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:waxed_oxidized_cut_copper_slab", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.ORDO).add(Aspects.TEMPUS, 5).add(Aspects.HERBA, 3));
        // - - - - - BAMBOO ITEMS - - - - -
        this.addItemAspect("minecraft:bamboo", new ItemAspects(Aspects.HERBA, 1));
        this.addItemAspect("minecraft:bamboo_block", new ItemAspects(Aspects.HERBA, 7));
        this.addItemAspect("minecraft:stripped_bamboo_block", new ItemAspects(Aspects.HERBA, 6));
        this.addItemAspect("minecraft:bamboo_planks", new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect("minecraft:bamboo_mosaic", new ItemAspects(Aspects.HERBA, 1));
        this.addItemAspect("minecraft:bamboo_stairs", new ItemAspects(Aspects.HERBA, 7));
        this.addItemAspect("minecraft:bamboo_mosaic_stairs", new ItemAspects(Aspects.HERBA, 1));
        this.addItemAspect("minecraft:bamboo_slab", new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect("minecraft:bamboo_mosaic_slab", new ItemAspects(Aspects.HERBA, 1));
        this.addItemAspect("minecraft:bamboo_fence", new ItemAspects(Aspects.HERBA, 4));
        this.addItemAspect("minecraft:bamboo_fence_gate", new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect("minecraft:bamboo_door", new ItemAspects(Aspects.HERBA, 4).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect("minecraft:bamboo_trapdoor", new ItemAspects(Aspects.HERBA, 2).add(Aspects.MACHINA, 5).add(Aspects.VINCULUM, 5));
        this.addItemAspect("minecraft:bamboo_pressure_plate", new ItemAspects(Aspects.HERBA, 1).add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:bamboo_button", new ItemAspects(Aspects.HERBA, 1).add(Aspects.MACHINA, 5));
        // - - - - - CRIMSON ITEMS - - - - -
        this.addItemAspect("minecraft:crimson_stem", Thaumcraft.logs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:crimson_hyphae", Thaumcraft.woods.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:stripped_crimson_stem", Thaumcraft.stripped_logs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:stripped_crimson_hyphae", Thaumcraft.stripped_woods.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:crimson_planks", Thaumcraft.planks.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:crimson_stairs", Thaumcraft.stairs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:crimson_slab", Thaumcraft.slabs.add(Aspects.INFERNO, 1));
        this.addItemAspect("minecraft:crimson_fence", Thaumcraft.fences.add(Aspects.INFERNO, 3));
        this.addItemAspect("minecraft:crimson_fence_gate", Thaumcraft.fences_gates.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:crimson_door", Thaumcraft.doors.add(Aspects.INFERNO, 9));
        this.addItemAspect("minecraft:crimson_trapdoor", Thaumcraft.trapdoors.add(Aspects.INFERNO, 7));
        this.addItemAspect("minecraft:crimson_pressure_plate", Thaumcraft.plates.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:crimson_button", Thaumcraft.buttons.add(Aspects.INFERNO, 1));
        // - - - - - WARPED ITEMS - - - - -
        this.addItemAspect("minecraft:warped_stem", Thaumcraft.logs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:warped_hyphae", Thaumcraft.woods.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:warpedd_crimson_stem", Thaumcraft.stripped_logs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:warpedd_crimson_hyphae", Thaumcraft.stripped_woods.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:warped_planks", Thaumcraft.planks.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:warped_stairs", Thaumcraft.stairs.add(Aspects.INFERNO, 5));
        this.addItemAspect("minecraft:warped_slab", Thaumcraft.slabs.add(Aspects.INFERNO, 1));
        this.addItemAspect("minecraft:warped_fence", Thaumcraft.fences.add(Aspects.INFERNO, 3));
        this.addItemAspect("minecraft:warped_fence_gate", Thaumcraft.fences_gates.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:warped_door", Thaumcraft.doors.add(Aspects.INFERNO, 9));
        this.addItemAspect("minecraft:warped_trapdoor", Thaumcraft.trapdoors.add(Aspects.INFERNO, 7));
        this.addItemAspect("minecraft:warped_pressure_plate", Thaumcraft.plates.add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:warped_button", Thaumcraft.buttons.add(Aspects.INFERNO, 1));
        // - - - - - WOOL ITEMS - - - - -
        this.addItemAspect("minecraft:white_wool", new ItemAspects(Aspects.BESTIA, 15).add(Aspects.FABRICO, 5));
        this.addItemAspect("minecraft:light_gray_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:gray_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:black_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:brown_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:red_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:orange_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:yellow_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:lime_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:green_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:cyan_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:light_blue_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:blue_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:purple_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:magenta_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        this.addItemAspect("minecraft:pink_wool", new ItemAspects(Aspects.BESTIA, 11).add(Aspects.FABRICO, 3).add(Aspects.SENSUS, 3));
        // - - - - - CARPET ITEMS - - - - -
        this.addItemAspect("minecraft:white_carpet", new ItemAspects(Aspects.BESTIA, 7).add(Aspects.FABRICO, 2));
        this.addItemAspect("minecraft:light_gray_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:gray_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:black_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:brown_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:red_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:orange_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:yellow_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:lime_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:green_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:cyan_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:light_blue_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:blue_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:purple_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:magenta_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        this.addItemAspect("minecraft:pink_carpet", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO, 1).add(Aspects.SENSUS, 1));
        // - - - - - TERRACOTtA ITEMS - - - - -
        this.addItemAspect("minecraft:terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:white_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:light_gray_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:gray_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:black_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:brown_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:red_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:orange_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:yellow_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:lime_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:green_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:cyan_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:light_blue_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:blue_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:purple_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:magenta_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:pink_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS).add(Aspects.SENSUS));
        // - - - - - GLAZED TERRACOTA ITEMS - - - - -
        this.addItemAspect("minecraft:white_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:gray_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:black_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:brown_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:red_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:orange_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:yellow_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:lime_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:green_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:cyan_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:light_blue_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:blue_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:purple_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:magenta_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        this.addItemAspect("minecraft:pink_glazed_terracotta", new ItemAspects(Aspects.AQUA, 15).add(Aspects.TERRA, 15).add(Aspects.IGNIS, 2).add(Aspects.SENSUS, 2));
        // - - - - - CONCRETE ITEMS - - - - -
        this.addItemAspect("minecraft:white_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:light_gray_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:gray_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:black_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:brown_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:red_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:orange_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:yellow_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:lime_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:green_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:cyan_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:light_blue_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:blue_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:purple_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:magenta_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:pink_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2).add(Aspects.AQUA).add(Aspects.ORDO));
        // - - - - - POWDER CONCRETE ITEMS - - - - -
        this.addItemAspect("minecraft:white_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:light_gray_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:gray_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:black_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:brown_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:red_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:orange_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:yellow_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:lime_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:green_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:cyan_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:light_blue_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:blue_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:purple_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:magenta_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        this.addItemAspect("minecraft:pink_concrete_powder", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2));
        // - - - - - GLASS ITEMS - - - - -
        this.addItemAspect("minecraft:glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:tinted_glass", new ItemAspects(Aspects.VITREUS, 5).add(Aspects.TENEBRAE, 2));
        this.addItemAspect("minecraft:white_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:light_gray_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:gray_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:black_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:brown_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:red_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:orange_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:yellow_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:lime_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:green_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:cyan_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:light_blue_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:blue_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:purple_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:magenta_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:pink_stained_glass", new ItemAspects(Aspects.VITREUS, 5));
        // - - - - - GLASS PANE ITEMS - - - - -
        this.addItemAspect("minecraft:glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:tinted_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:white_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:light_gray_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:gray_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:black_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:brown_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:red_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:orange_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:yellow_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:lime_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:green_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:cyan_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:light_blue_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:blue_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:purple_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:magenta_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        this.addItemAspect("minecraft:pink_stained_glass_pane", new ItemAspects(Aspects.VITREUS, 1));
        // - - - - - BED ITEMS - - - - -
        this.addItemAspect("minecraft:white_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6));
        this.addItemAspect("minecraft:light_gray_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:gray_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:black_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:brown_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:red_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:orange_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:yellow_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:lime_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:green_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:cyan_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:light_blue_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:blue_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:purple_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:magenta_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:pink_bed", new ItemAspects(Aspects.BESTIA, 33).add(Aspects.FABRICO, 11).add(Aspects.HERBA, 6).add(Aspects.SENSUS));
        // - - - - - CANDLE ITEMS - - - - -
        this.addItemAspect("minecraft:candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO));
        this.addItemAspect("minecraft:white_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:light_gray_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:gray_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:black_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:brown_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:red_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:orange_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:yellow_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:lime_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:green_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:cyan_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:light_blue_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:blue_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:purple_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:magenta_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        this.addItemAspect("minecraft:pink_candle", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.HERBA, 3).add(Aspects.FABRICO).add(Aspects.SENSUS));
        // - - - - - BANNER ITEMS - - - - -
        this.addItemAspect("minecraft:white_banner", new ItemAspects(Aspects.BESTIA, 67).add(Aspects.FABRICO, 22));
        this.addItemAspect("minecraft:light_gray_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:gray_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:black_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:brown_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:red_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:orange_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:yellow_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:lime_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:green_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:cyan_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:light_blue_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:blue_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:purple_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:magenta_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        this.addItemAspect("minecraft:pink_banner", new ItemAspects(Aspects.BESTIA, 49).add(Aspects.FABRICO, 13).add(Aspects.SENSUS, 13));
        // - - - - - ORE ITEMS - - - - -
        this.addItemAspect("minecraft:coal_ore", new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.IGNIS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_coal_ore", new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.IGNIS, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:iron_ore", new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_iron_ore", new ItemAspects(Aspects.METALLUM, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:raw_iron", new ItemAspects(Aspects.METALLUM, 15).add(Aspects.PERDITIO, 3));
        this.addItemAspect("minecraft:raw_iron_block", new ItemAspects(Aspects.METALLUM, 90).add(Aspects.PERDITIO, 21));

        this.addItemAspect("minecraft:copper_ore", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.TERRA, 8));
        this.addItemAspect("minecraft:deepslate_copper_ore", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:raw_copper", new ItemAspects(Aspects.METALLUM, 7).add(Aspects.PERDITIO, 3));
        this.addItemAspect("minecraft:raw_copper_block", new ItemAspects(Aspects.METALLUM, 49).add(Aspects.PERDITIO, 21));

        this.addItemAspect("minecraft:gold_ore", new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_gold_ore", new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:raw_gold", new ItemAspects(Aspects.METALLUM, 10).add(Aspects.DESIDERIUM, 10).add(Aspects.PERDITIO, 5));
        this.addItemAspect("minecraft:raw_gold_block", new ItemAspects(Aspects.METALLUM, 66).add(Aspects.DESIDERIUM, 66).add(Aspects.PERDITIO, 35));

        this.addItemAspect("minecraft:redstone_ore", new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_redstone_ore", new ItemAspects(Aspects.POTENTIA, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:emerald_ore", new ItemAspects(Aspects.VITREUS, 15).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_emerald_ore", new ItemAspects(Aspects.VITREUS, 15).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:lapis_ore", new ItemAspects(Aspects.SENSUS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_lapis_ore", new ItemAspects(Aspects.SENSUS, 15).add(Aspects.TERRA, 8).add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:diamond_ore", new ItemAspects(Aspects.DESIDERIUM, 15).add(Aspects.VITREUS, 15).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:deepslate_diamond_ore", new ItemAspects(Aspects.DESIDERIUM, 15).add(Aspects.VITREUS, 15).add(Aspects.TERRA, 8)
                .add(Aspects.ORDO, 2));

        this.addItemAspect("minecraft:nether_gold_ore", new ItemAspects(Aspects.TERRA, 5).add(Aspects.METALLUM, 5).add(Aspects.DESIDERIUM, 3)
                .add(Aspects.IGNIS, 2));
        this.addItemAspect("minecraft:nether_quartz_ore", new ItemAspects(Aspects.VITREUS, 10).add(Aspects.TERRA, 5));
        // - - - - - AMETHYST ITEMS - - - - -
        this.addItemAspect("minecraft:amethyst_block", new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO));
        this.addItemAspect("minecraft:budding_amethyst", new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO).add(Aspects.VICTUS, 3));
        this.addItemAspect("minecraft:small_amethyst_bud", new ItemAspects(Aspects.VITREUS, 3).add(Aspects.PRAECANTATIO).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:medium_amethyst_bud", new ItemAspects(Aspects.VITREUS, 5).add(Aspects.PRAECANTATIO, 2).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:large_amethyst_bud", new ItemAspects(Aspects.VITREUS, 15).add(Aspects.PRAECANTATIO, 3).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:amethyst_cluster", new ItemAspects(Aspects.VITREUS, 20).add(Aspects.PRAECANTATIO, 5).add(Aspects.VICTUS, 2));
        // - - - - - SEEDS ITEMS - - - - -
        this.addItemAspect("minecraft:pumpkin_seeds", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:melon_seeds", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:torchflower_seeds", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:pitcher_pod", new ItemAspects(Aspects.HERBA, 4).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:wheat_seeds", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:beetroot_seeds", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS));







        this.addItemAspect("minecraft:dried_kelp_block", new ItemAspects(Aspects.HERBA, 8).add(Aspects.IGNIS, 7));
        this.addItemAspect("minecraft:dried_kelp", new ItemAspects(Aspects.HERBA).add(Aspects.IGNIS));
        this.addItemAspect("minecraft:kelp", new ItemAspects(Aspects.HERBA).add(Aspects.AQUA));
        this.addItemAspect("minecraft:sea_pickle", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3).add(Aspects.LUX, 2));
        this.addItemAspect("minecraft:seagrass", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA));
        this.addItemAspect("minecraft:sweet_berries", new ItemAspects(Aspects.HERBA, 7).add(Aspects.VICTUS, 3));
        this.addItemAspect("minecraft:glow_berries", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS, 3).add(Aspects.LUX, 2));
        this.addItemAspect("minecraft:sniffer_egg", new ItemAspects(Aspects.VICTUS, 15).add(Aspects.TEMPUS, 10).add(Aspects.HUMANUS, 3));
        this.addItemAspect("minecraft:turtle_egg", new ItemAspects(Aspects.VICTUS, 5).add(Aspects.HUMANUS));
        this.addItemAspect("minecraft:frogspawn", new ItemAspects(Aspects.VICTUS, 5).add(Aspects.HUMANUS));
        this.addItemAspect("minecraft:hanging_roots", new ItemAspects(Aspects.HERBA, 3));
        this.addItemAspect("minecraft:glow_lichen", new ItemAspects(Aspects.HERBA, 5).add(Aspects.LUX));
        this.addItemAspect("minecraft:small_dripleaf", new ItemAspects(Aspects.HERBA, 2));
        this.addItemAspect("minecraft:big_dripleaf", new ItemAspects(Aspects.HERBA, 5));
        this.addItemAspect("minecraft:twisting_vines", new ItemAspects(Aspects.HERBA, 5).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:weeping_vines", new ItemAspects(Aspects.HERBA, 5).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:crimsoon_roots", new ItemAspects(Aspects.HERBA, 3).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:warped_roots", new ItemAspects(Aspects.HERBA, 3).add(Aspects.IGNIS, 3).add(Aspects.INFERNO, 2));
        this.addItemAspect("minecraft:dead_bush", new ItemAspects(Aspects.HERBA, 5).add(Aspects.PERDITIO));
        this.addItemAspect("minecraft:fern", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect("minecraft:short_grass", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect("minecraft:crimson_fungus", new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2).add(Aspects.IGNIS, 5));
        this.addItemAspect("minecraft:warped_fungus", new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2).add(Aspects.IGNIS, 5));
        this.addItemAspect("minecraft:flowering_azalea", new ItemAspects(Aspects.HERBA, 15).add(Aspects.VICTUS, 7));
        this.addItemAspect("minecraft:azalea", new ItemAspects(Aspects.HERBA, 10).add(Aspects.VICTUS, 3));
        this.addItemAspect("minecraft:red_mushroom_stem", new ItemAspects(Aspects.HERBA, 15));
        this.addItemAspect("minecraft:brown_mushroom_stem", new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect("minecraft:mushroom_stem", new ItemAspects(Aspects.HERBA, 10));
        this.addItemAspect("minecraft:muddy_mangrove_roots", new ItemAspects(Aspects.HERBA, 15).add(Aspects.TERRA, 3).add(Aspects.AQUA, 3));
        this.addItemAspect("minecraft:mangrove_roots", new ItemAspects(Aspects.HERBA, 15));
        this.addItemAspect("minecraft:bone_block", new ItemAspects(Aspects.SENSUS, 33));
        this.addItemAspect("minecraft:soul_sand", new ItemAspects(Aspects.TERRA, 3).add(Aspects.SPIRITUS, 3)
                .add(Aspects.VINCULUM));
        this.addItemAspect("minecraft:soul_soil", new ItemAspects(Aspects.TERRA, 3).add(Aspects.SPIRITUS, 3)
                .add(Aspects.VINCULUM).add(Aspects.VOLATUS));
        this.addItemAspect("minecraft:warped_nylium", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect("minecraft:crimson_nylium", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2).add(Aspects.HERBA).add(Aspects.INFERNO));
        this.addItemAspect("minecraft:crying_obsidian", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 5).add(Aspects.TENEBRAE, 5)
                .add(Aspects.PRAECANTATIO));
        this.addItemAspect("minecraft:magma_block", new ItemAspects(Aspects.IGNIS, 10).add(Aspects.TERRA, 5).add(Aspects.INFERNO));
        this.addItemAspect("minecraft:pointed_dripstone", new ItemAspects(Aspects.TERRA, 3).add(Aspects.ORDO).add(Aspects.AQUA));
        this.addItemAspect("minecraft:dripstone_block", new ItemAspects(Aspects.TERRA, 10).add(Aspects.ORDO, 5).add(Aspects.AQUA, 3));
        this.addItemAspect("minecraft:calcite", new ItemAspects(Aspects.TERRA, 20).add(Aspects.ORDO, 5));
        this.addItemAspect("minecraft:moss_carpet", new ItemAspects(Aspects.TERRA).add(Aspects.HERBA).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:moss_block", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 5).add(Aspects.VICTUS, 5));
        this.addItemAspect("minecraft:snow", new ItemAspects(Aspects.GELUM, 1));
        this.addItemAspect("minecraft:snow_block", new ItemAspects(Aspects.GELUM, 3));
        this.addItemAspect("minecraft:blue_ice", new ItemAspects(Aspects.GELUM, 30));
        this.addItemAspect("minecraft:packed_ice", new ItemAspects(Aspects.GELUM, 25));
        this.addItemAspect("minecraft:clay", new ItemAspects(Aspects.TERRA, 15).add(Aspects.AQUA, 15));
        this.addItemAspect("minecraft:rooted_dirt", new ItemAspects(Aspects.TERRA, 4));
        this.addItemAspect("minecraft:coarse_dirt", new ItemAspects(Aspects.TERRA, 3));
        this.addItemAspect("minecraft:mycelium", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA).add(Aspects.VITIUM));
        this.addItemAspect("minecraft:string", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO));
        this.addItemAspect("minecraft:diamond_block", new ItemAspects(Aspects.VITREUS, 101).add(Aspects.DESIDERIUM, 10));
        this.addItemAspect("minecraft:lapis_block", new ItemAspects(Aspects.SENSUS, 33).add(Aspects.TERRA, 13).add(Aspects.DESIDERIUM, 13));
        this.addItemAspect("minecraft:emerald_block", new ItemAspects(Aspects.VITREUS, 101).add(Aspects.DESIDERIUM, 67));
        this.addItemAspect("minecraft:redstone_block", new ItemAspects(Aspects.DESIDERIUM, 67).add(Aspects.POTENTIA, 67));
        this.addItemAspect("minecraft:light_weighted_pressure_plate", new ItemAspects(Aspects.METALLUM, 15).add(Aspects.DESIDERIUM, 15)
                .add(Aspects.MACHINA, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:gold_block", new ItemAspects(Aspects.METALLUM, 67).add(Aspects.DESIDERIUM, 67));
        this.addItemAspect("minecraft:coal_block", new ItemAspects(Aspects.POTENTIA, 67).add(Aspects.IGNIS, 67));
        this.addItemAspect("minecraft:netherrack", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2));
        this.addItemAspect("minecraft:prismarine_shard", new ItemAspects(Aspects.AQUA, 5)
                .add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:prismarine_crystals", new ItemAspects(Aspects.AQUA, 5)
                .add(Aspects.LUX, 5).add(Aspects.VITREUS, 5));
        this.addItemAspect("minecraft:sea_lantern", new ItemAspects(Aspects.AQUA, 33).add(Aspects.VITREUS, 18).add(Aspects.LUX, 18)
                .add(Aspects.TERRA, 15));
        this.addItemAspect("minecraft:tuff", new ItemAspects(Aspects.TERRA, 5).add(Aspects.ORDO));
        this.addItemAspect("minecraft:chest", new ItemAspects(Aspects.HERBA, 18));
        this.addItemAspect("minecraft:grass_block", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 2));
        this.addItemAspect("minecraft:dirt", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA, 2));
        this.addItemAspect("minecraft:podzol", new ItemAspects(Aspects.TERRA, 5).add(Aspects.HERBA));
        this.addItemAspect("minecraft:bedrock", new ItemAspects(Aspects.VACOUS, 25).add(Aspects.PERDITIO, 25)
                .add(Aspects.TERRA, 25).add(Aspects.TENEBRAE, 25));
        this.addItemAspect("minecraft:sand", new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:red_sand", new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:gravel", new ItemAspects(Aspects.PERDITIO, 5).add(Aspects.TERRA, 2));
        this.addItemAspect("minecraft:wet_sponge", new ItemAspects(Aspects.TERRA, 5).add(Aspects.VINCULUM, 5)
                .add(Aspects.AQUA, 5));
        this.addItemAspect("minecraft:cobweb", new ItemAspects(Aspects.VINCULUM, 5).add(Aspects.BESTIA));
        this.addItemAspect("minecraft:brown_mushroom", new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2)
                .add(Aspects.TERRA, 2));
        this.addItemAspect("minecraft:red_mushroom", new ItemAspects(Aspects.HERBA, 5).add(Aspects.TENEBRAE, 2)
                .add(Aspects.IGNIS, 2));
        this.addItemAspect("minecraft:sugar_cane", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 3)
                .add(Aspects.AER, 2));
        this.addItemAspect("minecraft:obsidian", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 5)
                .add(Aspects.TENEBRAE, 5));
        this.addItemAspect("minecraft:chorus_plant", new ItemAspects(Aspects.ALIENIS, 5).add(Aspects.HERBA, 5));
        this.addItemAspect("minecraft:chorus_flower", new ItemAspects(Aspects.ALIENIS, 5).add(Aspects.HERBA, 5)
                .add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:ice", new ItemAspects(Aspects.GELUM, 20));
        this.addItemAspect("minecraft:cactus", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA, 5)
                .add(Aspects.AVERSIO));
        this.addItemAspect("minecraft:carved_pumpkin", new ItemAspects(Aspects.HERBA, 20));
        this.addItemAspect("minecraft:netherrack", new ItemAspects(Aspects.TERRA, 5).add(Aspects.IGNIS, 2));
        this.addItemAspect("minecraft:vine", new ItemAspects(Aspects.HERBA, 5));
        this.addItemAspect("minecraft:lily_pad", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AQUA));
        this.addItemAspect("minecraft:end_portal_frame", new ItemAspects(Aspects.POTENTIA, 10).add(Aspects.MOTUS, 10)
                .add(Aspects.PRAECANTATIO, 5).add(Aspects.ALIENIS, 10));
        this.addItemAspect("minecraft:dragon_egg", new ItemAspects(Aspects.ALIENIS, 15).add(Aspects.MOTUS, 15)
                .add(Aspects.TENEBRAE, 15).add(Aspects.PRAECANTATIO, 5).add(Aspects.BESTIA, 15));
        this.addItemAspect("minecraft:dirt_path", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 2)
                .add(Aspects.ORDO, 2));
        this.addItemAspect("minecraft:sunflower", new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:lilac", new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:rose_bush", new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:peony", new ItemAspects(Aspects.HERBA, 5).add(Aspects.SENSUS, 5)
                .add(Aspects.AER).add(Aspects.VICTUS));
        this.addItemAspect("minecraft:tall_grass", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect("minecraft:large_fern", new ItemAspects(Aspects.HERBA, 5).add(Aspects.AER));
        this.addItemAspect("minecraft:red_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2)
                .add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:black_concrete", new ItemAspects(Aspects.TERRA, 3).add(Aspects.PERDITIO, 2)
                .add(Aspects.AQUA).add(Aspects.ORDO));
        this.addItemAspect("minecraft:saddle", new ItemAspects(Aspects.BESTIA, 10).add(Aspects.MOTUS, 10)
                .add(Aspects.ORDO, 5));
        this.addItemAspect("minecraft:elytra", new ItemAspects(Aspects.MOTUS, 15).add(Aspects.VOLATUS, 20));
        this.addItemAspect("minecraft:apple", new ItemAspects(Aspects.HERBA, 5).add(Aspects.VICTUS, 5));
        this.addItemAspect("minecraft:gold_ingot", new ItemAspects(Aspects.METALLUM).add(Aspects.DESIDERIUM));
        this.addItemAspect("minecraft:string", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.FABRICO));
        this.addItemAspect("minecraft:feather", new ItemAspects(Aspects.AER, 5).add(Aspects.VOLATUS, 5));
        this.addItemAspect("minecraft:gunpowder", new ItemAspects(Aspects.IGNIS, 10).add(Aspects.PERDITIO, 10)
                .add(Aspects.ALKIMIA, 5));
        this.addItemAspect("minecraft:chainmail_helmet", new ItemAspects(Aspects.METALLUM, 42).add(Aspects.PRAEMUNIO, 8));
        this.addItemAspect("minecraft:chainmail_chestplate", new ItemAspects(Aspects.METALLUM, 67).add(Aspects.PRAEMUNIO, 20));
        this.addItemAspect("minecraft:chainmail_leggings", new ItemAspects(Aspects.METALLUM, 58).add(Aspects.PRAEMUNIO, 16));
        this.addItemAspect("minecraft:chainmail_boots", new ItemAspects(Aspects.METALLUM, 33).add(Aspects.PRAEMUNIO, 4));
        this.addItemAspect("minecraft:flint", new ItemAspects(Aspects.METALLUM, 11).add(Aspects.IGNIS, 10)
                .add(Aspects.TERRA, 3).add(Aspects.INSTRUMENTUM, 8));
        this.addItemAspect("minecraft:porkchop", new ItemAspects(Aspects.BESTIA, 5).add(Aspects.VICTUS, 5)
                .add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:enchanted_golden_apple", new ItemAspects(Aspects.VICTUS, 15)
                .add(Aspects.PRAEMUNIO, 15));
        this.addItemAspect("minecraft:water_bucket", new ItemAspects(Aspects.METALLUM, 33).add(Aspects.AQUA, 20)
                .add(Aspects.VACOUS, 5));
        this.addItemAspect("minecraft:lava_bucket", new ItemAspects(Aspects.METALLUM, 33).add(Aspects.IGNIS, 15)
                .add(Aspects.VACOUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:snowball", new ItemAspects(Aspects.GELUM));
        this.addItemAspect("minecraft:milk_bucket", new ItemAspects(Aspects.METALLUM, 33).add(Aspects.AQUA, 5)
                .add(Aspects.VACOUS, 5).add(Aspects.VICTUS, 10).add(Aspects.BESTIA, 5));
        this.addItemAspect("minecraft:clay_ball", new ItemAspects(Aspects.TERRA, 5).add(Aspects.AQUA, 5));
        this.addItemAspect("minecraft:egg", new ItemAspects(Aspects.VICTUS, 5).add(Aspects.BESTIA, 5));
        this.addItemAspect("minecraft:glowstone_dust", new ItemAspects(Aspects.LUX, 10).add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:ink_sac", new ItemAspects(Aspects.SENSUS, 5).add(Aspects.AQUA, 2)
                .add(Aspects.BESTIA, 2));
        this.addItemAspect("minecraft:cocoa_beans", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.POTENTIA, 2).add(Aspects.DESIDERIUM, 2));
        this.addItemAspect("minecraft:orange_dye", new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:magenta_dye", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect("minecraft:light_blue_dye", new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:yellow_dye", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect("minecraft:pink_dye", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect("minecraft:light_gray_dye", new ItemAspects(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:red_dye", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA));
        this.addItemAspect("minecraft:bone", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.HERBA).add(Aspects.VICTUS, 2).add(Aspects.MORTUS));
        this.addItemAspect("minecraft:sugar", new ItemAspects(Aspects.HERBA, 3)
                .add(Aspects.AQUA, 2).add(Aspects.AER).add(Aspects.POTENTIA)
                .add(Aspects.DESIDERIUM));
        this.addItemAspect("minecraft:beef", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:chicken", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.AER, 5));
        this.addItemAspect("minecraft:rotten_flesh", new ItemAspects(Aspects.VICTUS, 5)
                .add(Aspects.PERDITIO, 5).add(Aspects.HUMANUS, 5));
        this.addItemAspect("minecraft:ender_pearl", new ItemAspects(Aspects.MOTUS, 15)
                .add(Aspects.ALIENIS, 10));
        this.addItemAspect("minecraft:blaze_rod", new ItemAspects(Aspects.IGNIS, 15)
                .add(Aspects.MOTUS, 10));
        this.addItemAspect("minecraft:ghast_tear", new ItemAspects(Aspects.IGNIS, 6)
                .add(Aspects.PERDITIO, 2).add(Aspects.POTENTIA, 2).add(Aspects.ALKIMIA, 2));
        this.addItemAspect("minecraft:nether_wart", new ItemAspects(Aspects.ALKIMIA, 3)
                .add(Aspects.HERBA).add(Aspects.VITIUM, 2));
        this.addItemAspect("minecraft:spider_eye", new ItemAspects(Aspects.SENSUS, 5)
                .add(Aspects.BESTIA, 5).add(Aspects.MORTUS, 5));
        this.addItemAspect("minecraft:experience_bottle", new ItemAspects(Aspects.COGNITIO, 20));
        this.addItemAspect("minecraft:written_book", new ItemAspects(Aspects.COGNITIO, 3)
                .add(Aspects.AQUA, 4).add(Aspects.HERBA, 4).add(Aspects.AER, 5)
                .add(Aspects.BESTIA, 3).add(Aspects.SENSUS, 3).add(Aspects.VOLATUS, 3));
        this.addItemAspect("minecraft:carrot", new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.SENSUS, 5));
        this.addItemAspect("minecraft:potato", new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:poisonous_potato", new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.MORTUS, 5));
        this.addItemAspect("minecraft:skeleton_skull", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect("minecraft:wither_skeleton_skull", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect("minecraft:player_head", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.HUMANUS, 10));
        this.addItemAspect("minecraft:zombie_head", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.HUMANUS, 10));
        this.addItemAspect("minecraft:creeper_head", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.PERDITIO, 5).add(Aspects.IGNIS, 5));
        this.addItemAspect("minecraft:dragon_head", new ItemAspects(Aspects.MORTUS, 10)
                .add(Aspects.SPIRITUS, 10).add(Aspects.TENEBRAE, 10).add(Aspects.IGNIS, 10));
        this.addItemAspect("minecraft:nether_star", new ItemAspects(Aspects.ORDO, 20)
                .add(Aspects.ALIENIS, 20).add(Aspects.PRAECANTATIO, 20).add(Aspects.AURAM, 20));
        this.addItemAspect("minecraft:rabbit", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:rabbit_stew", new ItemAspects(Aspects.HERBA, 12)
                .add(Aspects.VICTUS, 11).add(Aspects.BESTIA, 3).add(Aspects.SENSUS, 3)
                .add(Aspects.VACOUS, 3).add(Aspects.IGNIS).add(Aspects.TENEBRAE));
        this.addItemAspect("minecraft:rabbit_foot", new ItemAspects(Aspects.MOTUS, 10)
                .add(Aspects.BESTIA, 5).add(Aspects.PRAEMUNIO, 5).add(Aspects.ALKIMIA, 5));
        this.addItemAspect("minecraft:rabbit_hide", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.PRAEMUNIO, 2));
        this.addItemAspect("minecraft:golden_horse_armor", new ItemAspects(Aspects.METALLUM, 10)
                .add(Aspects.PRAEMUNIO, 15).add(Aspects.BESTIA, 5));
        this.addItemAspect("minecraft:diamond_horse_armor", new ItemAspects(Aspects.PRAEMUNIO, 20)
                .add(Aspects.BESTIA, 5).add(Aspects.VITREUS, 15));
        this.addItemAspect("minecraft:name_tag", new ItemAspects(Aspects.BESTIA, 10)
                .add(Aspects.COGNITIO, 10));
        this.addItemAspect("minecraft:mutton", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:chorus_fruit", new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.SENSUS, 5).add(Aspects.ALIENIS, 5));
        this.addItemAspect("minecraft:beetroot", new ItemAspects(Aspects.HERBA, 5)
                .add(Aspects.VICTUS, 5).add(Aspects.DESIDERIUM, 5));
        this.addItemAspect("minecraft:dragon_breath", new ItemAspects(Aspects.IGNIS, 10)
                .add(Aspects.PERDITIO, 10).add(Aspects.TENEBRAE, 10).add(Aspects.ALKIMIA, 10));
        this.addItemAspect("minecraft:totem_of_undying", new ItemAspects(Aspects.VICTUS, 25)
                .add(Aspects.ORDO, 10).add(Aspects.PERDITIO, 10).add(Aspects.EXANIMIS, 10));
        this.addItemAspect("minecraft:shulker_shell", new ItemAspects(Aspects.BESTIA, 5)
                .add(Aspects.VACOUS, 5).add(Aspects.ALIENIS, 5).add(Aspects.PRAEMUNIO, 10));
        this.addItemAspect("minecraft:music_disc_13", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.AQUA, 5));
        this.addItemAspect("minecraft:music_disc_cat", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.BESTIA, 5));
        this.addItemAspect("minecraft:music_disc_blocks", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.INSTRUMENTUM, 5));
        this.addItemAspect("minecraft:music_disc_chirp", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.TERRA, 5));
        this.addItemAspect("minecraft:music_disc_far", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.ALIENIS, 5));
        this.addItemAspect("minecraft:music_disc_mall", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.HUMANUS, 5));
        this.addItemAspect("minecraft:music_disc_mellohi", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.FABRICO, 5));
        this.addItemAspect("minecraft:music_disc_stal", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.TENEBRAE, 5));
        this.addItemAspect("minecraft:music_disc_strad", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.POTENTIA, 5));
        this.addItemAspect("minecraft:music_disc_ward", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.VICTUS, 5));
        this.addItemAspect("minecraft:music_disc_11", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10));
        this.addItemAspect("minecraft:music_disc_wait", new ItemAspects(Aspects.SENSUS, 15)
                .add(Aspects.AER, 5).add(Aspects.DESIDERIUM, 10).add(Aspects.VINCULUM, 5));
        this.addItemAspect("thaumcraft:thaumonomicon", new ItemAspects(Aspects.AER)
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
            this.addItemAspect("thaumcraft:crystal", CrystalHelper.create(aspect).getNbt(), new ItemAspects(aspect));
        }
    }
}