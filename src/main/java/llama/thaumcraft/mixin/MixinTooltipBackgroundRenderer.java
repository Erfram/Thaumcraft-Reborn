package llama.thaumcraft.mixin;

import llama.thaumcraft.Thaumcraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
@Mixin(TooltipBackgroundRenderer.class)
public class MixinTooltipBackgroundRenderer {
    private static final int BACKGROUND_COLOR = -267386864;
    private static int variable = 0;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static void render(DrawContext context, int x, int y, int width, int height, int z) {
        int leftX = x - 3;
        int topY = y - 3;
        int fullWidth = width + 6;
        int fullHeight = height + 6;

        if(Screen.hasShiftDown()) {
            DrawContext drawContext = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
            renderImage(drawContext, "textures/gui/terra.png", leftX + 20, topY + 40, 16, 16, 16, 16);
        }

        //renderTopLine
        renderHorizontalLine(context, leftX, topY - 1, fullWidth, z, BACKGROUND_COLOR);
        //renderBottomLine
        renderHorizontalLine(context, leftX, topY + fullHeight, fullWidth, z, BACKGROUND_COLOR);
        //renderRectangle
        renderRectangle(context, leftX, topY, fullWidth, fullHeight, z, BACKGROUND_COLOR);
        //renderLeftLine
        renderVerticalLine(context, leftX - 1, topY, fullHeight, z, BACKGROUND_COLOR);
        //renderRightLine
        renderVerticalLine(context, leftX + fullWidth, topY, fullHeight, z, BACKGROUND_COLOR);
        //renderBorder
        renderBorder(context, leftX, topY + 1, fullWidth, fullHeight, z, 1347420415, 1344798847);
    }

    private static void renderBorder(DrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        renderVerticalLine(context, x, y, height - 2, z, startColor, endColor);
        renderVerticalLine(context, x + width - 1, y, height - 2, z, startColor, endColor);
        renderHorizontalLine(context, x, y - 1, width, z, startColor);
        renderHorizontalLine(context, x, y - 1 + height - 1, width, z, endColor);
    }

    private static void renderVerticalLine(DrawContext context, int x, int y, int height, int z, int color) {
        context.fill(x, y, x + 1, y + height, z, color);
    }

    private static void renderVerticalLine(DrawContext context, int x, int y, int height, int z, int startColor, int endColor) {
        context.fillGradient(x, y, x + 1, y + height, z, startColor, endColor);
    }

    private static void renderHorizontalLine(DrawContext context, int x, int y, int width, int z, int color) {
        context.fill(x, y, x + width, y + 1, z, color);
    }

    private static void renderRectangle(DrawContext context, int x, int y, int width, int height, int z, int color) {
        context.fill(x, y, x + width, y + height, z, color);
    }

    private static void renderImage(DrawContext context, String texture, int leftX, int topY, int width, int height, int textureWidth, int textureHeight) {
        context.drawTexture(new Identifier(Thaumcraft.MOD_ID, texture), leftX, topY, 0, 0, width, height, textureWidth, textureHeight);
    }
}
