package llama.thaumcraft.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

public class AspectTooltipData implements TooltipData {
    private ItemStack stack;
    private int width;
    private int height;

    public AspectTooltipData(ItemStack stack) {
        this.stack = stack;
    }
    public ItemStack getStack() {
        return stack;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
