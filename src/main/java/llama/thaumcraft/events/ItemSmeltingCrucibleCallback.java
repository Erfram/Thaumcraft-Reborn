package llama.thaumcraft.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ItemSmeltingCrucibleCallback {

    Event<ItemSmeltingCrucibleCallback> EVENT = EventFactory.createArrayBacked(ItemSmeltingCrucibleCallback.class,
        (listeners) -> (BlockState state, World world, BlockPos pos, ItemEntity entity) -> {
            for (ItemSmeltingCrucibleCallback listener : listeners) {
                ActionResult result = listener.interact(state, world, pos, entity);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult interact(BlockState state, World world, BlockPos pos, ItemEntity entity);
}
