package name.divinityunbound.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.screen.renderer.EnergyInfoArea;
import name.divinityunbound.util.MouseUtil;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ZeusBatteryScreen extends HandledScreen<ZeusBatteryScreenHandler> {
    private static final Identifier TEXTURE = new Identifier((DivinityUnbound.MOD_ID), "textures/gui/zeus_battery_gui.png");
    private EnergyInfoArea energyInfoArea;
    public ZeusBatteryScreen(ZeusBatteryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        //titleY = 1000;
        //playerInventoryTitleY = 1000;

        assignEnergyInfoArea();
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 84,
                ((height - backgroundHeight) / 2 ) + 11, handler.blockEntity.energyStorage);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        energyInfoArea.draw(context);
    }

    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 84, 11, 8, 64)) {
            context.drawTooltip(Screens.getTextRenderer(this),
                    handler.blockEntity.isCreative ? List.of(Text.literal("Infinite")) : energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
