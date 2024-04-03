package name.divinityunbound.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.item.custom.FilterItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;

@Environment(EnvType.CLIENT)
public class FilterItemScreen extends HandledScreen<FilterItemScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(DivinityUnbound.MOD_ID, "textures/gui/filter_item_gui.png");

    FilterItemScreenHandler screenHandler;
    private Hashtable<Integer, ItemStack> itemsToFilter;
    private ItemStack heldStack = ItemStack.EMPTY;
    private boolean holdingStack = false;
    private FilterItem.MODE mode = FilterItem.MODE.INVALID;
    private CustomTexturedButtonWidget modeButton = null;

    public FilterItemScreen(FilterItemScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        itemsToFilter = getItemsToFilter(handler);
        mode = getMode(handler);

        screenHandler = handler;
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

        itemsToFilter = getItemsToFilter(screenHandler);
        if (itemsToFilter != null) {
            for (int m = 0; m < 2; ++m) {
                for (int l = 0; l < 9; ++l) {
                    int itemIndex = l + m * 8 + m;
                    int x = (8 + l * 18) + (width - backgroundWidth) / 2;
                    int y = (30 + m * 18) + (height - backgroundHeight) / 2;

                    ItemStack itemStack = itemsToFilter.get(itemIndex);
                    if (itemStack != null) {
//                        client.getItemRenderer().renderItem(itemStack, ModelTransformationMode.GUI,
//                                false, context.getMatrices(), context.getVertexConsumers(), 0xF000F0, );
                        context.drawItem(itemStack, x, y);
                    }
                }
            }
        }

        if (modeButton != null) {
            modeButton.setTooltip(Tooltip.of(Text.translatable("gui." + DivinityUnbound.MOD_ID + ".vacuum_filter.mode." + mode)));
        }
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        if (slot != null) {
            slotId = slot.id;

            if (!holdingStack && !slot.getStack().isEmpty() && slotId > 17) {
                holdingStack = true;
                heldStack = slot.getStack().copy();
            } else {
                // Holding stack, check to see if placed in inventory
                if (slotId > 17) {
                    // clicked in inventory
                    holdingStack = false;
                    heldStack = ItemStack.EMPTY;
                } else {
                    // clicked in vacuum filter
                    if (!heldStack.isEmpty()) {
                        this.itemsToFilter.remove(slotId);
                        this.itemsToFilter.put(slotId, heldStack);
                    } else {
                        this.itemsToFilter.remove(slotId);
                    }
                }
            }

            if (client != null) {
                updateItemsToFilter(this.itemsToFilter, client);
            }
        }

        super.onMouseClick(slot, slotId, button, actionType);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (modeButton == null && mode != FilterItem.MODE.INVALID) {
            int u = 176;
            int v = 1;

            if (mode == FilterItem.MODE.WHITELIST) {
                v = 1;
            }
            if (mode == FilterItem.MODE.BLACKLIST) {
                v = 9;
            }

            modeButton = this.addDrawableChild(createModeWidget(
                    x, y, 6, 6, 7, 7, u, v, 0, TEXTURE, 256, 256, ButtonWidget::onPress, Tooltip.of(Text.translatable("gui." + DivinityUnbound.MOD_ID + ".vacuum_filter.mode." + mode)), Text.literal(""), client));
        }

        if (mode == FilterItem.MODE.INVALID) {
            DivinityUnbound.LOGGER.error("[Divinity Unbound] Mode for a vacuum filter is -1! (Failed to get value from screen handler)");
        }
    }

    public CustomTexturedButtonWidget createModeWidget(int x, int y, int xMargin, int yMargin, int width, int height, int u, int v, int hoveredVOffset, Identifier guiTexture, int guiTextureWidth, int guiTextureHeight, ButtonWidget.PressAction pressAction, Tooltip tooltip, Text text, MinecraftClient client) {
        CustomTexturedButtonWidget buttonWidget = new CustomTexturedButtonWidget(x + xMargin, y + yMargin, width, height, u, v, hoveredVOffset, guiTexture, guiTextureWidth, guiTextureHeight, pressAction, text) {
            @Override
            public void onPress() {
                if (mode == FilterItem.MODE.BLACKLIST) {
                    mode = FilterItem.MODE.WHITELIST;
                }
                else if (mode == FilterItem.MODE.WHITELIST) {
                    mode = FilterItem.MODE.BLACKLIST;
                }

                switch (mode) {
                    case WHITELIST -> {
                        this.setUV(176, 1);
                    }
                    case BLACKLIST -> {
                        this.setUV(176, 9);
                    }
                }

                updateMode(mode, client);
            }
        };
        buttonWidget.setTooltip(tooltip);
        return buttonWidget;
    }

    @Nullable
    private static Hashtable<Integer, ItemStack> getItemsToFilter(ScreenHandler handler) {
        if (handler instanceof FilterItemScreenHandler) {
            return ((FilterItemScreenHandler) handler).getItemsToFilter();
        } else {
            return null;
        }
    }

    private void updateItemsToFilter(Hashtable<Integer, ItemStack> itemsToFilter, MinecraftClient client) {
        client.execute(() -> {
            PacketByteBuf buf = PacketByteBufs.create();

            FilterItem.createItemsToFilterBuf(itemsToFilter, buf);

            ClientPlayNetworking.send(new Identifier(DivinityUnbound.MOD_ID, "update_filter_item_packet"), buf);
        });
    }

    private void updateMode(FilterItem.MODE newMode, MinecraftClient client) {
        client.execute(() -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeEnumConstant(newMode);

            ClientPlayNetworking.send(new Identifier(DivinityUnbound.MOD_ID, "update_filter_item_mode_packet"), buf);
        });
    }

    private static FilterItem.MODE getMode(ScreenHandler handler) {
        if (handler instanceof FilterItemScreenHandler) {
            return ((FilterItemScreenHandler) handler).getMode();
        } else {
            return FilterItem.MODE.INVALID;
        }
    }
}

