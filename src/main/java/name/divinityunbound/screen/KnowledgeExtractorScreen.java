package name.divinityunbound.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KnowledgeExtractorScreen extends HandledScreen<KnowledgeExtractorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier((DivinityUnbound.MOD_ID), "textures/gui/knowledge_extractor_gui.png");
    public KnowledgeExtractorScreen(KnowledgeExtractorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Levels: " + Integer.toString(handler.getStoredExperienceLevel())),
                x + 87, y + 40, 0xffffff);

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Total XP: " + Integer.toString(handler.getStoredTotalExperience())),
                x + 87, y + 60, 0xffffff);
    }
}
