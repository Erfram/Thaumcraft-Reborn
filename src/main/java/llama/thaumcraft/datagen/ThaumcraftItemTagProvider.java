package llama.thaumcraft.datagen;

import llama.thaumcraft.items.ThaumcraftItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ThaumcraftItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ThaumcraftItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ThaumcraftTags.Items.type).add(ThaumcraftItems.CRYSTAL);
    }
}
