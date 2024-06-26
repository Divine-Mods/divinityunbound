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

import java.util.Optional;

public class VelesGathererScreen extends HandledScreen<VelesGathererScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(DivinityUnbound.MOD_ID, "textures/gui/veles_gatherer_gui.png");
    private EnergyInfoArea energyInfoArea;
//    private FluidStackRenderer fluidStackRenderer;
//    private long fluidCapacity;

    public VelesGathererScreen(VelesGathererScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        //titleY = 1000;
        //playerInventoryTitleY = 1000;

        //this.fluidCapacity = handler.blockEntity.fluidStorage.getCapacity() / 81;
        assignEnergyInfoArea();
        //assignFluidStackRenderer();
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 156,
                ((height - backgroundHeight) / 2 ) + 11, handler.blockEntity.energyStorage);
    }

//    private void assignFluidStackRenderer() {
//        fluidStackRenderer = new FluidStackRenderer(this.fluidCapacity, true, 16, 39);
//    }

    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 64)) {
            context.drawTooltip(Screens.getTextRenderer(this), energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

//    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
//        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
//            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, TooltipContext.Default.BASIC),
//                    Optional.empty(), mouseX - x, mouseY - y);
//        }
//    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        //renderFluidTooltip(context, mouseX, mouseY, x, y, 129, 24, fluidStackRenderer);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        energyInfoArea.draw(context);
//        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorage, x + 129, y + 24, 16, 39,
//                this.fluidCapacity);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

//    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
//        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
//    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
