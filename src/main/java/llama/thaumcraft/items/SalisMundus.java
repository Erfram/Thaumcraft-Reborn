package llama.thaumcraft.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class SalisMundus extends Item {
    public SalisMundus(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext flag) {
        tooltip.add(Text.translatable("item.thaumcraft.salis_mundus.tooltip").formatted(Formatting.GRAY));
    }
}
