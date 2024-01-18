package llama.thaumcraft.items;

import llama.thaumcraft.Aspect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class CrystalItem {
    public static ItemStack create(Aspect aspect) {
        ItemStack stack = ThaumcraftItems.CRYSTAL.getDefaultStack();
        stack.setNbt(buildType(aspect));
        stack.setCustomName(Text.translatable("item.thaumcraft.crystal", aspect.getName()));

        NbtCompound display = stack.getNbt().getCompound("display").getCompound("Name");
        display.putBoolean("italic", false);
        return stack;
    }
    public static NbtCompound buildType(Aspect aspect) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("type", aspect.getName());
        return nbt;
    }

    public static int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && !nbtCompound.contains("type", 99) ? Aspect.valueOf(nbtCompound.getString("type").toUpperCase()).getColor() : 16777215;
    }
}