package llama.thaumcraft.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.datagen.ThaumcraftItemTagProvider;
import llama.thaumcraft.datagen.Thaumonomicon;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ThaumcraftDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(ThaumcraftItemTagProvider::new);

		var enUsCache = new LanguageProviderCache("en_us");
		pack.addProvider((FabricDataOutput output) -> new Thaumonomicon("thaumonomicon", output, Thaumcraft.MOD_ID, enUsCache));
	}
}