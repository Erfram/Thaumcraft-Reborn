package llama.thaumcraft;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import llama.thaumcraft.datagen.ThaumcraftItemTagProvider;
import llama.thaumcraft.datagen.Thaumonomicon;
import llama.thaumcraft.items.CrystalHelper;
import llama.thaumcraft.items.ThaumcraftItems;
import llama.thaumcraft.magic.Aspect;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ThaumcraftDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(ThaumcraftItemTagProvider::new);
		pack.addProvider(AdvancementsProvider::new);

		var enUsCache = new LanguageProviderCache("en_us");
		pack.addProvider((FabricDataOutput output) -> new Thaumonomicon("thaumonomicon", output, Thaumcraft.MOD_ID, enUsCache));
	}

	static class AdvancementsProvider extends FabricAdvancementProvider {
		protected AdvancementsProvider(FabricDataOutput dataGenerator) {
			super(dataGenerator);
		}

		@Override
		public void generateAdvancement(Consumer<AdvancementEntry> consumer) {
			AdvancementEntry rootAdvancement = Advancement.Builder.create()
					.display(
							ThaumcraftItems.SALIS_MUNDUS, // The display icon
							Text.translatable("advancements.thaumcraft.salis_mundus.title"),
							Text.translatable("advancements.thaumcraft.salis_mundus.description"),
							new Identifier(Thaumcraft.MOD_ID, "textures/gui/advancements/backgrounds/thaumcraft.png"),
							AdvancementFrame.TASK,
							true,
							true,
							false
					)
					.criterion("craft_salis_mundus", InventoryChangedCriterion.Conditions.items(ThaumcraftItems.SALIS_MUNDUS))
					.build(consumer, Thaumcraft.MOD_ID + "/root");
		}
	}
}