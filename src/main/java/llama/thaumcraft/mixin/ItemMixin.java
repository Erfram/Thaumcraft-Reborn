package llama.thaumcraft.mixin;

import llama.thaumcraft.magic.Aspect;
import llama.thaumcraft.magic.AspectRegistry;
import llama.thaumcraft.tooltip.AspectTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        AspectTooltipData data = new AspectTooltipData(stack);

        Map<Aspect, Integer> aspects = AspectRegistry.getAspectsByItemStack(stack);

        if(aspects != null) {
            byte aspectsCount = (byte) aspects.size();
            int width;
            byte lineCount = (byte) Math.floor(aspectsCount / 16);
            int height = 16 * (lineCount + 1) + 2 * lineCount;
            if(aspectsCount < 6) {
                width = 16 * aspectsCount + 2 * (aspectsCount - 1);
            } else {
                width = 16 * Math.min(aspectsCount, 16) + 2 * Math.min(aspectsCount, 16);
                if(lineCount > 0) {
                    height += 2;
                }
            }

            data.setWidth(width);
            data.setHeight(height);

        } else {
            data.setWidth(0);
            data.setHeight(0);
        }

        return Optional.of(data);
    }
}
