package name.divinityunbound.networking;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.item.custom.FilterItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Hashtable;

public class UpdateFilterItemPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        Hashtable<Integer, ItemStack> itemsToFilter = FilterItem.readItemsToFilterBuf(buf);

        server.execute(() -> {
            if (player.getStackInHand(player.getActiveHand()).getItem() == ModItems.FILTER_ITEM) {
                FilterItem.saveItemsToFilterToNbt(player.getStackInHand(player.getActiveHand()), itemsToFilter);
            }
        });
    }
}
