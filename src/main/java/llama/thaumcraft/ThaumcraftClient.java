package llama.thaumcraft;

import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

@Environment(value = EnvType.CLIENT)
public class ThaumcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> CrystalHelper.getColor(stack), ThaumcraftItems.CRYSTAL);
	}
}