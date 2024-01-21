package llama.thaumcraft;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EventHandler {

    private static void onItemTooltip() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {

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
        onItemTooltip();
    }
}
