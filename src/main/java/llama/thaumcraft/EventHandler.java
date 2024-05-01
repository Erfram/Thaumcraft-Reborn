package llama.thaumcraft;

import llama.thaumcraft.events.ItemSmeltingCrucibleCallback;
import llama.thaumcraft.magic.AspectRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class EventHandler {
    private static void onItemSmeltingCrucible() {
        ItemSmeltingCrucibleCallback.EVENT.register((state, world, pos, entity) -> {
            if(!world.isClient) {
                List<? extends PlayerEntity> players = world.getPlayers();

                for(PlayerEntity player : players) {
                    player.sendMessage(Text.of("Кинул предмет: " + entity.getStack().getItem().getName()));
                }
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
