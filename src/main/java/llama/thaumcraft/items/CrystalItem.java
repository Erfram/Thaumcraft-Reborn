package llama.thaumcraft.items;

import llama.thaumcraft.Aspects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalItem extends Item {
    public CrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Aspects aspect = Aspects.findByColor(CrystalHelper.getColor(stack));
        String aspectName = aspect.getName().substring(0, 1).toUpperCase().concat(aspect.getName().substring(1));
        tooltip.add(Text.translatable(
                "item.thaumcraft.crystal.tooltip",
                Text.translatable(aspectName)
        ).styled(style -> style.withFormatting(Formatting.DARK_GRAY)));
    }
}