package name.divinityunbound.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.screen.renderer.EnergyInfoArea;
import name.divinityunbound.screen.renderer.FluidStackRenderer;
import name.divinityunbound.util.MouseUtil;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ItemSingularityStorageScreen extends HandledScreen<ItemSingularityStorageScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(DivinityUnbound.MOD_ID, "textures/gui/item_singularity_storage_gui.png");

    public ItemSingularityStorageScreen(ItemSingularityStorageScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(Integer.toString(handler.getItemCount())),
                (width / 2) + 89, (height / 2) + 42, 0xffffff);
    }
}
