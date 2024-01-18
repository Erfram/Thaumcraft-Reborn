package llama.thaumcraft;

import llama.thaumcraft.items.CrystalItem;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import java.util.Objects;

@Environment(value = EnvType.CLIENT)
public class ThaumcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> CrystalItem.getColor(stack), ThaumcraftItems.CRYSTAL);
	}
}