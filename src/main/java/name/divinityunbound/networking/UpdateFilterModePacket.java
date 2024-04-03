package name.divinityunbound.networking;

import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.custom.FilterItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Hashtable;

public class UpdateFilterModePacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        FilterItem.MODE mode = buf.readEnumConstant(FilterItem.MODE.class);

        server.execute(() -> {
            if (player.getStackInHand(player.getActiveHand()).getItem() == ModItems.FILTER_ITEM) {
                FilterItem.saveModeNbt(player.getStackInHand(player.getActiveHand()), mode);
            }
        });
    }
}
