package name.divinityunbound.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.entity.DivineReplicatorBlockEntity;
import name.divinityunbound.networking.ModMessages;
import name.divinityunbound.networking.UpdateDivineReplicatorS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DivineReplicatorScreen extends HandledScreen<DivineReplicatorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier((DivinityUnbound.MOD_ID), "textures/gui/divine_replicator_gui.png");
    private final DivineReplicatorBlockEntity blockEntity;
    private CyclingButtonWidget<Object> spawnTypeButton;
    private DivineReplicatorBlockEntity.SPAWN_TYPE spawnType;
    public DivineReplicatorScreen(DivineReplicatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.blockEntity = handler.blockEntity;
        this.spawnType = this.blockEntity.getSpawnType();
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.spawnTypeButton = this.addDrawableChild(CyclingButtonWidget.builder(value -> switch ((DivineReplicatorBlockEntity.SPAWN_TYPE) value) {
            default -> throw new IncompatibleClassChangeError();
            case EXACT_MATCH -> Text.translatable("divinityunbound.divine_replicator.exact_match");
            case SIMILAR -> Text.translatable("divinityunbound.divine_replicator.similar");
        }).values(DivineReplicatorBlockEntity.SPAWN_TYPE.values())
                .omitKeyText().initially(this.spawnType)
                .build(x + 115, y + 9, 40, 20, Text.translatable("advMode.mode"), (button, mode) -> {
            this.spawnType = (DivineReplicatorBlockEntity.SPAWN_TYPE) mode;
            this.blockEntity.setSpawnType((DivineReplicatorBlockEntity.SPAWN_TYPE)mode);
            this.syncSettings();
        }));
        this.spawnTypeButton.active = true;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Mods:"),
                x + 18, y + 35, 0xffffff);

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("No AI"),
                x + 18, y + 47, 0xffffff);
    }

    @Override
    public void close() {
        super.close();
    }

    public void syncSettings() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(this.spawnType);
        buf.writeBlockPos(this.blockEntity.getPos());
        this.blockEntity.getWorld().getDimension();
        ClientPlayNetworking.send(ModMessages.DR_SPAWN_TYPE, buf);
//        this.client.getNetworkHandler()
//                .sendPacket(new UpdateDivineReplicatorS2CPacket(
//                        this.blockEntity.getPos(),
//                        this.spawnType
//                        ));
    }
}
