package llama.thaumcraft.items;

import llama.thaumcraft.Aspects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class CrystalHelper {
    public static ItemStack create(Aspects aspect) {
        ItemStack stack = ThaumcraftItems.CRYSTAL.getDefaultStack();
        stack.setNbt(buildType(aspect));
        String aspectName = aspect.getName().substring(0, 1).toUpperCase().concat(aspect.getName().substring(1));
        stack.setCustomName(
                Text.translatable(
                        "item.thaumcraft.crystal",
                        Text.translatable(aspectName).styled(style -> style.withColor(aspect.getColor()))
                ).styled(style -> style.withItalic(false))
        );

        stack.getItem().appendTooltip(stack, null, new ArrayList<>(), TooltipContext.BASIC);
        return stack;
    }

    public static NbtCompound buildType(Aspects aspect) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("type", aspect.getName());
        return nbt;
    }

    public static int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && !nbtCompound.contains("type", 99) ? Aspects.valueOf(nbtCompound.getString("type").toUpperCase()).getColor() : 16777215;
    }
}