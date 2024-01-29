package llama.thaumcraft;

import llama.thaumcraft.events.ItemSmeltingCrucibleCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EventHandler {
    private static void onItemSmeltingCrucible() {
        ItemSmeltingCrucibleCallback.EVENT.register((state, world, pos, entity) -> {
            if(!world.isClient) {
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

    public static void register() {
        Thaumcraft.LOGGER.debug("Registering events, for: "+Thaumcraft.MOD_NAME);

        onUseBlock();
        onItemSmeltingCrucible();
    }
}
