package name.divinityunbound.networking;


import name.divinityunbound.block.entity.DivineReplicatorBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
public class DivineReplicatorS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        DivineReplicatorBlockEntity.SPAWN_TYPE spawnType = buf.readEnumConstant(DivineReplicatorBlockEntity.SPAWN_TYPE.class);
        BlockPos position = buf.readBlockPos();
        ServerWorld world = player.getServerWorld();
        //BlockEntity blockEntity = world.getBlockEntity(position);
        server.execute(() -> {
            BlockEntity blockEntity = world.getWorldChunk(position).getBlockEntity(position);
            BlockEntity blockEntity2 = world.getBlockEntity(position);
            if(blockEntity instanceof DivineReplicatorBlockEntity) {
                ((DivineReplicatorBlockEntity)blockEntity).setSpawnType(spawnType);
            }
        });
//        BlockEntity blockEntity = world.getWorldChunk(position).getBlockEntity(position);
//        if(blockEntity instanceof DivineReplicatorBlockEntity) {
//            ((DivineReplicatorBlockEntity)blockEntity).setSpawnType(spawnType);
//        }
    }
}
