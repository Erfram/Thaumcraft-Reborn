package llama.thaumcraft.datagen;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.datagen.category.BasicsCategoryProvider;
import llama.thaumcraft.items.ThaumcraftItemGroups;
import net.minecraft.data.DataOutput;
import net.minecraft.util.Identifier;

public class Thaumonomicon extends BookProvider {
    /**
     * @param bookId
     * @param packOutput
     * @param modid
     * @param defaultLang  The LanguageProvider to fill with this book provider. IMPORTANT: the Languag Provider needs to be added to the DataGenerator AFTER the BookProvider.
     * @param translations
     */
    public Thaumonomicon(String bookId, DataOutput packOutput, String modid, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations) {
        super(bookId, packOutput, modid, defaultLang, translations);
    }

    @Override
    protected void registerDefaultMacros() {

    }

    @Override
    protected BookModel generateBook() {
        this.lang().add(this.context().bookName(), "thaumonomicon");
        this.lang().add(this.context().bookTooltip(), "An ancient manuscript containing knowledge of..");

        var BasicsCategory = new BasicsCategoryProvider(this).generate();

        return BookModel.create(this.modLoc("thaumonomicon"), this.context().bookName())
            .withModel(new Identifier(Thaumcraft.MOD_ID, "thaumonomicon"))
            .withTooltip(this.context().bookTooltip())
            .withCategories(BasicsCategory)
            .withGenerateBookItem(false)
            .withCustomBookItem(this.modLoc("thaumonomicon"));
    }
}
