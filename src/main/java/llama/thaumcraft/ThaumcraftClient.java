package llama.thaumcraft;

import llama.thaumcraft.blocks.ThaumcraftBlocks;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

@Environment(value = EnvType.CLIENT)
public class ThaumcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> CrystalHelper.getColor(stack), ThaumcraftItems.CRYSTAL);
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getWaterColor(view, pos), ThaumcraftBlocks.CRUCIBLE);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ThaumcraftBlocks.CRUCIBLE);

		ItemTooltipCallback.EVENT.register((ItemStack stack, TooltipContext context, List<Text> lines) -> {
			if(Screen.hasShiftDown()){
				lines.add(Text.empty());
				lines.add(Text.empty());
			}
		});
	}
}