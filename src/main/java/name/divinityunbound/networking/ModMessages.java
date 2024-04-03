package name.divinityunbound.networking;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier DR_SPAWN_TYPE = new Identifier(DivinityUnbound.MOD_ID, "divine_replicator.spawn_type");
    public static final Identifier WORMHOLE_ENABLES = new Identifier(DivinityUnbound.MOD_ID, "wormhole_transporter.enables");

    public static final Identifier UPDATE_FILTER_ITEM_PACKET_ID = new Identifier(DivinityUnbound.MOD_ID, "update_filter_item_packet");
    public static final Identifier UPDATE_FILTER_MODE_PACKET_ID = new Identifier(DivinityUnbound.MOD_ID, "update_filter_item_mode_packet");


    public static void registerS2CPackets() {
        //ClientPlayNetworking.registerGlobalReceiver(DR_SPAWN_TYPE, DivineReplicatorS2CPacket::receive);
        //new PacketHandlerInitializer().setup(NetworkSide.CLIENTBOUND, new NetworkState.InternalPacketHandler<T>().register(DivineReplicatorS2CPacket.class, DivineReplicatorS2CPacket::new);
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DR_SPAWN_TYPE, DivineReplicatorS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(WORMHOLE_ENABLES, WormholeTransporterC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_FILTER_ITEM_PACKET_ID, UpdateFilterItemPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ModMessages.UPDATE_FILTER_MODE_PACKET_ID, UpdateFilterModePacket::receive);
    }
}
