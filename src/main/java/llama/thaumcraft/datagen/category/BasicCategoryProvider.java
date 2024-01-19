package llama.thaumcraft.datagen.category;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.items.ThaumcraftItems;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BasicCategoryProvider extends CategoryProvider {
    public BasicCategoryProvider(BookProvider parent) {
        super(parent, "basic");
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "__m______________d___",
                "__________r__________",
                "__c__________________",
                "__________2___3___i__",
                "__s_____e____________"
        };
    }

    @Override
    protected void generateEntries() {

    }

    @Override
    protected BookCategoryModel generateCategory() {
        return BookCategoryModel.create(
            this.modLoc(this.context().categoryId()),
            this.context().categoryName()
        )
        .withIcon(ThaumcraftItems.THAUMONOMICON)
        .withBackgroundTextureZoomMultiplier(1f/2f)
        .withBackground(new Identifier(Thaumcraft.MOD_ID, "textures/gui/basic_background.png"));
    }
}
