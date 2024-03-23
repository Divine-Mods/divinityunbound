package name.divinityunbound.screen;

import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.WormholeTransporterBlockEntity;
import name.divinityunbound.networking.DivinityUnboundNetworkingConstants;
import name.divinityunbound.networking.ModMessages;
import name.divinityunbound.screen.renderer.EnergyInfoArea;
import name.divinityunbound.screen.renderer.FluidStackRenderer;
import name.divinityunbound.util.MouseUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class WormholeTransporterScreen extends HandledScreen<WormholeTransporterScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(DivinityUnbound.MOD_ID, "textures/gui/wormhole_transporter_gui.png");
    private EnergyInfoArea energyInfoArea;
    private FluidStackRenderer fluidStackRenderer;
    private long fluidCapacity;
    private WormholeTransporterBlockEntity blockEntity;

    private CyclingButtonWidget<Boolean> itemButton;
    private CyclingButtonWidget<Boolean> fluidButton;
    private CyclingButtonWidget<Boolean> energyButton;
    private boolean itemsActive;
    private boolean energyActive;
    private boolean fluidsActive;

    //private WormholeTransporterBlockEntity wormholeTransporter;

    public WormholeTransporterScreen(WormholeTransporterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.blockEntity = handler.blockEntity;
        this.itemsActive = this.blockEntity.getItemsEnabled();
        this.fluidsActive = this.blockEntity.getFluidsEnabled();
        this.energyActive = this.blockEntity.getEnergyEnabled();
        //this.wormholeTransporter = handler.blockEntity;
//        BlockPos pos = handler.blockEntity.getPos();
//        this.wormholeTransporter = (WormholeTransporterBlockEntity)handler.blockEntity.getWorld().getBlockEntity(pos);
//        PlayerEntity player = this.wormholeTransporter.getWorld().getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
//        if (player != null) {
//            player.sendMessage(Text.literal("Wormhole transporter: " + this.wormholeTransporter.getPos().toString()));
//        }
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.itemButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(
                Text.translatable("divinityunbound.mode.on"), Text.translatable("divinityunbound.mode.off"))
                .omitKeyText().initially(this.itemsActive)
                .build(x + 45, y + 10, 25, 16,
                        Text.translatable("advMode.type"),
                        (button, conditional) -> {
                    this.itemsActive = conditional;
                    this.blockEntity.setItemsEnabled(conditional);
                    this.syncSettings();
                }));
        this.fluidButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(
                        Text.translatable("divinityunbound.mode.on"), Text.translatable("divinityunbound.mode.off"))
                .omitKeyText().initially(this.fluidsActive)
                .build(x + 45, y + 28, 25, 16,
                        Text.translatable("advMode.type"),
                        (button, conditional) -> {
                            this.fluidsActive = conditional;
                            this.blockEntity.setFluidsEnabled(conditional);
                            this.syncSettings();
                        }));
        this.energyButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(
                        Text.translatable("divinityunbound.mode.on"), Text.translatable("divinityunbound.mode.off"))
                .omitKeyText().initially(this.energyActive)
                .build(x + 45, y + 46, 25, 16,
                        Text.translatable("advMode.type"),
                        (button, conditional) -> {
                            this.energyActive = conditional;
                            this.blockEntity.setEnergyEnabled(conditional);
                            this.syncSettings();
                        }));
        this.itemButton.active = true;
        this.fluidButton.active = true;
        this.energyButton.active = true;
        this.fluidCapacity = handler.blockEntity.fluidStorage.getCapacity() / 81;
        assignEnergyInfoArea();
        assignFluidStackRenderer();
    }

    public void syncSettings() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(this.itemsActive);
        buf.writeBoolean(this.fluidsActive);
        buf.writeBoolean(this.energyActive);
        buf.writeBlockPos(this.blockEntity.getPos());
        //this.blockEntity.getWorld().getDimension();
        ClientPlayNetworking.send(ModMessages.WORMHOLE_ENABLES, buf);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(((width - backgroundWidth) / 2) + 156,
                ((height - backgroundHeight) / 2 ) + 11, handler.blockEntity.energyStorage);
    }

    private void assignFluidStackRenderer() {
        fluidStackRenderer = new FluidStackRenderer(this.fluidCapacity, true, 16, 39);
    }

    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 64)) {
            context.drawTooltip(Screens.getTextRenderer(this), energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if(isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(Screens.getTextRenderer(this), renderer.getTooltip(handler.blockEntity.fluidStorage, TooltipContext.Default.BASIC),
                    Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        renderEnergyAreaTooltips(context, mouseX, mouseY, x, y);
        renderFluidTooltip(context, mouseX, mouseY, x, y, 129, 24, fluidStackRenderer);
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
        fluidStackRenderer.drawFluid(context, handler.blockEntity.fluidStorage, x + 129, y + 24, 16, 39,
                this.fluidCapacity);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("divinityunbound.wormhole_transporter.items"), x + 24, y + 14, 0xffffff);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("divinityunbound.wormhole_transporter.fluids"), x + 24, y + 32, 0xffffff);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("divinityunbound.wormhole_transporter.energy"), x + 24, y + 50, 0xffffff);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidStackRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
