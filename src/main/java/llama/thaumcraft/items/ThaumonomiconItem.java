package llama.thaumcraft.items;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import llama.thaumcraft.Thaumcraft;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class ThaumonomiconItem extends ModonomiconItem {
    public static final Identifier THAUMONOMICON = new Identifier(Thaumcraft.MOD_ID, "thaumonomicon");
    public ThaumonomiconItem(Settings properties) {
        super(properties);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        var itemInHand = player.getStackInHand(hand);

        if (world.isClient) {
            if (itemInHand.hasNbt()) {
                var book = BookDataManager.get().getBook(THAUMONOMICON);
                BookGuiManager.get().openBook(book.getId());
            } else {
                Modonomicon.LOG.error("ModonomiconItem: ItemStack has no tag!");
            }
        }

        return TypedActionResult.success(itemInHand, world.isClient);
    }

    @Override
    public Text getName(ItemStack stack) {
        Book book = BookDataManager.get().getBook(THAUMONOMICON);
        if (book != null) {
            return Text.translatable(book.getName());
        }

        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext flag) {
        Book book = getBook(stack);
        if (book == null) {
            tooltip.add(Text.translatable(ModonomiconConstants.I18n.Tooltips.ITEM_NO_BOOK_FOUND_FOR_STACK,
                            !stack.hasNbt() ? Text.literal("{}") : NbtHelper.toPrettyPrintedText(stack.getNbt()))
                    .formatted(Formatting.DARK_GRAY));
        } else {
            if (!book.getTooltip().isBlank()) {
                tooltip.add(Text.translatable(book.getTooltip()).formatted(Formatting.GRAY));
            }
        }
    }

    public ItemStack getCreativeModeTabDisplayStack() {
        ItemStack stack = new ItemStack(this);

        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, THAUMONOMICON.toString());
        stack.setNbt(nbtCompound);

        return stack;
    }
}
