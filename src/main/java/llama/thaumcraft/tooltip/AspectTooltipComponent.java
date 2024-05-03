package llama.thaumcraft.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import llama.thaumcraft.magic.Aspect;
import llama.thaumcraft.Thaumcraft;
import llama.thaumcraft.magic.AspectRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.LinkedHashMap;
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
            Map<Aspect, Integer> aspects = AspectRegistry.getAspectsByItemStack(this.stack);
            if(aspects == null) {
                return;
            }

            Map<Aspect, Integer> sortedMap = new LinkedHashMap<>();

            aspects.entrySet().stream()
                    .sorted(Map.Entry.<Aspect, Integer>comparingByValue().reversed())
                    .forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

            byte aspectIndex = 0;
            for (Map.Entry<Aspect, Integer> entry : sortedMap.entrySet()) {
                Aspect aspect = entry.getKey();
                int aspectAmount = Screen.hasControlDown() ? entry.getValue() * stack.getCount() : entry.getValue();
                byte lineCount = (byte) Math.floor(aspectIndex / 16);
                int columnIndex = aspectIndex - lineCount * 16;

                int imageX = x + 16 * columnIndex + 2 * columnIndex;
                int textX;
                if(aspectAmount >= 100) {
                    textX = imageX - 1;
                }else if (aspectAmount >= 10) {
                    textX = imageX + 4;
                } else {
                    textX = imageX + 10;
                }

                RenderSystem.enableBlend();
                renderImage(context, aspect.getName(), imageX, y - 2 + 20 * lineCount);
                RenderSystem.disableBlend();
                context.drawText(MinecraftClient.getInstance().textRenderer, String.valueOf(aspectAmount), textX, y + 6 + 20 * lineCount, 0xFFFFFF, true);

                aspectIndex++;
            }
        }
    }

    public static void renderImage(DrawContext context, String texture, int x, int y) {
        context.drawTexture(new Identifier(Thaumcraft.MOD_ID, "textures/gui/aspects/" + texture + ".png"), x, y, 0, 0, 16, 16, 16, 16);
    }
}