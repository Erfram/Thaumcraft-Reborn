package llama.thaumcraft.tooltip;

import llama.thaumcraft.Thaumcraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.util.Identifier;

public class AspectTooltipComponent implements TooltipComponent {
    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 16;
    }

    public void renderImage(DrawContext context, String texture, int leftX, int topY, int width, int height, int textureWidth, int textureHeight) {
        context.drawTexture(new Identifier(Thaumcraft.MOD_ID, texture), leftX, topY, 0, 0, width, height, textureWidth, textureHeight);
    }
}