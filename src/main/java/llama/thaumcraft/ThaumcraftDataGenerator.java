package llama.thaumcraft;

import llama.thaumcraft.datagen.ThaumcraftItemTagProvider;
import llama.thaumcraft.datagen.ThaumcraftTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ThaumcraftDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(ThaumcraftItemTagProvider::new);
	}
}
