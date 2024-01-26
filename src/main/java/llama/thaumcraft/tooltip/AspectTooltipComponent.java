package llama.thaumcraft.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import llama.thaumcraft.Aspects;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.config.AspectRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.io.FileNotFoundException;
import java.util.Map;

public class AspectTooltipComponent implements TooltipComponent {
    private ItemStack stack;
    private int height;
    private int width;

    public AspectTooltipComponent(AspectTooltipData data) {
        this.stack = data.getStack();
        this.width = data.getWidth();
        this.height = data.getHeight();
    }

    @Override
    public int getHeight() {
        if(Screen.hasShiftDown()){
            return this.height;
        } else {
            return 0;
        }
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        if(Screen.hasShiftDown()){
            return this.width;
        } else {
            return 0;
        }
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        TooltipComponent.super.drawText(textRenderer, x, y, matrix, vertexConsumers);
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        TooltipComponent.super.drawItems(textRenderer, x, y, context);

        if(Screen.hasShiftDown()){
            Map<Aspects, Integer> aspects = AspectRegistry.getAspectsByItemStack(this.stack);
            if(aspects == null) {
                return;
            }

            int i = 0;
            for (Map.Entry<Aspects, Integer> entry : aspects.entrySet()) {
                Aspects aspect = entry.getKey();
                int amount = entry.getValue();
                int lineCount = (int) Math.floor(i / 16);
                int xi = i - lineCount * 16;

                int xOffset = x + 16 * xi + 2 * xi;
                int xText;
                if(amount >= 100) {
                    xText = xOffset - 1;
                }else if (amount >= 10) {
                    xText = xOffset + 4;
                } else {
                    xText = xOffset + 10;
                }

                RenderSystem.enableBlend();
                renderImage(context, aspect.getName(), xOffset, y - 2 + 20 * lineCount);
                RenderSystem.disableBlend();
                context.drawText(MinecraftClient.getInstance().textRenderer, String.valueOf(amount), xText, y + 6 + 20 * lineCount, 0xFFFFFF, true);

                i++;
            }
        }
    }

    public static void renderImage(DrawContext context, String texture, int x, int y) {
        context.drawTexture(new Identifier(Thaumcraft.MOD_ID, "textures/gui/aspects/" + texture + ".png"), x, y, 0, 0, 16, 16, 16, 16);
    }
}