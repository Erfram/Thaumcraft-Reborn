package llama.thaumcraft;

import llama.thaumcraft.blocks.ThaumcraftBlocks;
import llama.thaumcraft.config.ItemAspects;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import llama.thaumcraft.tooltip.AspectTooltipComponent;
import llama.thaumcraft.tooltip.AspectTooltipData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

import java.util.HashMap;

@Environment(value = EnvType.CLIENT)
public class ThaumcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> CrystalHelper.getColor(stack), ThaumcraftItems.CRYSTAL);
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getWaterColor(view, pos), ThaumcraftBlocks.CRUCIBLE);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ThaumcraftBlocks.CRUCIBLE);


		TooltipComponentCallback.EVENT.register(data -> {
			if(data instanceof AspectTooltipData aspectTooltipData){
				return new AspectTooltipComponent(aspectTooltipData);
			}
            return null;
        });
	}
}