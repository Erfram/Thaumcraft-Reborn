package llama.thaumcraft;

import llama.thaumcraft.events.ItemSmeltingCrucibleCallback;
import llama.thaumcraft.magic.AspectRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EventHandler {
    private static void onItemSmeltingCrucible() {
        ItemSmeltingCrucibleCallback.EVENT.register((state, world, pos, entity) -> {
            if(!world.isClient) {
                world.getServer().getPlayerManager().broadcast(Text.of("Кинул предмет: " + Registries.ITEM.getId(entity.getStack().getItem())), false);
                //code...
            }

            return ActionResult.PASS;
        });
    }

    private static void onUseBlock() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(world.isClient && hand.equals(Hand.MAIN_HAND) && player.getMainHandStack().isEmpty()) {
                player.sendMessage(Text.of("Ты взаимодействуешь с блоком"));
            }
            return ActionResult.PASS;
        });
    }

    private static void onServerStart() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            AspectRegistry.register();
        });
    }

    public static void register() {
        Thaumcraft.LOGGER.debug("Registering events, for: "+Thaumcraft.MOD_NAME);

        onServerStart();
        onUseBlock();
        onItemSmeltingCrucible();
    }
}
