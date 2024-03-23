package name.divinityunbound.networking;


import name.divinityunbound.block.entity.DivineReplicatorBlockEntity;
import name.divinityunbound.block.entity.WormholeTransporterBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class WormholeTransporterC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        boolean itemsEnabled = buf.readBoolean();
        boolean fluidsEnabled = buf.readBoolean();
        boolean energyEnabled = buf.readBoolean();
        BlockPos position = buf.readBlockPos();
        ServerWorld world = player.getServerWorld();
        server.execute(() -> {
            BlockEntity blockEntity = world.getBlockEntity(position);
            if(blockEntity instanceof WormholeTransporterBlockEntity) {
                ((WormholeTransporterBlockEntity)blockEntity).setItemsEnabled(itemsEnabled);
                ((WormholeTransporterBlockEntity)blockEntity).setFluidsEnabled(fluidsEnabled);
                ((WormholeTransporterBlockEntity)blockEntity).setEnergyEnabled(energyEnabled);
            }
        });
    }
}
