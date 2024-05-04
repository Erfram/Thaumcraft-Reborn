package llama.thaumcraft.blocks;

import llama.thaumcraft.blocks.entity.CrucibleBlockEntity;
import llama.thaumcraft.events.ItemSmeltingCrucibleCallback;
import llama.thaumcraft.magic.Aspect;
import llama.thaumcraft.magic.AspectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class CrucibleBlock extends Block implements BlockEntityProvider {
    public static final IntProperty LEVEL = ThaumcraftProperties.LEVEL;
    public static final BooleanProperty IS_BOILING = ThaumcraftProperties.IS_BOILING;
    public static final BooleanProperty BOILED = ThaumcraftProperties.BOILED;

    public CrucibleBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(LEVEL, 0)
                .with(IS_BOILING, false)
                .with(BOILED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL, IS_BOILING, BOILED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item haindItem = player.getStackInHand(hand).getItem();

        if (haindItem.equals(Items.WATER_BUCKET)) {
            this.fill(player, hand, world, pos, state);

            return ActionResult.SUCCESS;
        } else if (haindItem.equals(Items.BUCKET)) {
            if (state.get(LEVEL) > 0) {
                this.removeWater(player, hand, world, pos, state);
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()) return null;
        return (world1, blockPos, blockState, blockEntity)->{
            if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
                crucibleBlockEntity.addBoilingPercentage(1);
                world.getServer().getPlayerManager().broadcast(Text.of(String.valueOf(crucibleBlockEntity.getBoilingPercentage())), false);
            }
        };
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(!world.isClient()) {
            random.nextDouble();
            this.scheduledTick(state, world, pos, random);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        CrucibleBlockEntity crucibleBlockEntity = (CrucibleBlockEntity) world.getBlockEntity(pos);

        crucibleBlockEntity.addBoilingPercentage(1);
        world.getServer().getPlayerManager().broadcast(Text.of(String.valueOf(crucibleBlockEntity.getBoilingPercentage())), false);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        Set<Block> hotBlocks = Set.of(Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.LAVA);
        BlockState sourceBlockState = world.getBlockState(sourcePos);
        sourceBlock = sourceBlockState.getBlock();

        boolean isBoiling = false;

        if (pos.down().equals(sourcePos) && hotBlocks.contains(sourceBlock)) {
            isBoiling = sourceBlockState.contains(Properties.LIT) ? sourceBlockState.get(Properties.LIT) : true;
        }

        this.setBoiling(world, pos, state, isBoiling);
        world.getServer().getPlayerManager().broadcast(Text.of(String.valueOf(isBoiling)), false);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (isEntityTouchingFluid(state, pos, entity) && entity instanceof ItemEntity) {
            ActionResult result = ItemSmeltingCrucibleCallback.EVENT.invoker().interact(state, world, pos, ((ItemEntity) entity));

            if (result != ActionResult.FAIL) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                CrucibleBlockEntity crucibleBlockEntity = (CrucibleBlockEntity) world.getBlockEntity(pos);

                Map<Aspect, Integer> aspectsMap = AspectRegistry.getAspectsByItemStack(stack);

                if (aspectsMap != null) {
                    for (Map.Entry<Aspect, Integer> entry : aspectsMap.entrySet()) {
                        Aspect aspect = entry.getKey();
                        int aspectCount = entry.getValue();

                        if (aspectCount > 0) {
                            crucibleBlockEntity.setAspect(aspect, aspectCount * stack.getCount());
                        }
                    }
                }

                entity.setRemoved(Entity.RemovalReason.DISCARDED);
            }
        } else if (entity.isOnFire() || !(entity.getFireTicks() <= 0)) {
            this.addWaterLayer(world, state, pos, -1);
            entity.setFireTicks(0);
        }
    }

    public double getFluidHeight(BlockState state) {
        return switch (state.get(LEVEL)) {
            case (0) -> 0.0;
            case (1) -> 0.1;
            case (2) -> 0.32;
            case (3) -> 0.6;
            default -> throw new IllegalStateException("Unexpected value: " + state.get(LEVEL));
        };
    }

    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
    }

    public void removeWater(PlayerEntity player, Hand hand, World world, BlockPos pos, BlockState state) {
        this.addWaterLayer(world, state, pos, -1);

        if (!player.isCreative()) {
            if (player.getStackInHand(hand).getCount() == 1) {
                player.setStackInHand(hand, Items.WATER_BUCKET.getDefaultStack());
            } else if (player.getStackInHand(hand).getCount() > 1) {
                player.giveItemStack(Items.WATER_BUCKET.getDefaultStack());
            }
        }

        world.playSound(pos.getX(), pos.getY(), pos.getZ(),
                SoundEvent.of(new Identifier("minecraft:item.bucket.fill")),
                SoundCategory.PLAYERS,
                1, 1,
                true
        );
    }

    public void fill(PlayerEntity player, Hand hand, World world, BlockPos pos, BlockState state) {
        if (!player.isCreative()) {
            player.setStackInHand(hand, Items.BUCKET.getDefaultStack());
        }
        world.playSound(pos.getX(), pos.getY(), pos.getZ(),
                SoundEvent.of(new Identifier("minecraft:item.bucket.empty")),
                SoundCategory.PLAYERS,
                1, 1,
                true
        );

        this.addWaterLayer(world, state, pos, 1);
    }

    public void setBoiling(World world, BlockPos pos, BlockState state, boolean boiling) {
        world.setBlockState(pos, state.with(IS_BOILING, boiling), Block.NOTIFY_ALL);
    }

    public void addWaterLayer(World world, BlockState state, BlockPos pos, int layer) {
        world.setBlockState(pos, state.with(LEVEL, Math.min(Math.max(state.get(LEVEL) + layer, 0), Block.NOTIFY_ALL)));
    }

    public void setWaterLayer(World world, BlockState state, BlockPos pos, int layer) {
        if (layer <= 3 && layer >= 0) {
            world.setBlockState(pos, state.with(LEVEL, layer));
        }
    }

    public boolean isEntityTouchingFluid(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double) pos.getY() + this.getFluidHeight(state) && entity.getBoundingBox().maxY > (double) pos.getY() + 0.25;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return VoxelShapes.combineAndSimplify(
                VoxelShapes.fullCube(),
                VoxelShapes.union(CrucibleBlock.createCuboidShape(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
                        CrucibleBlock.createCuboidShape(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
                        CrucibleBlock.createCuboidShape(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
                        CrucibleBlock.createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0)
                ),
                BooleanBiFunction.ONLY_FIRST
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrucibleBlockEntity(pos, state);
    }
}
