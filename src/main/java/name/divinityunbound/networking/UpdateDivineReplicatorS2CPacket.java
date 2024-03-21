package name.divinityunbound.networking;

import name.divinityunbound.block.entity.DivineReplicatorBlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;

public class UpdateDivineReplicatorS2CPacket
        implements Packet<ServerPlayPacketListener> {
    private final BlockPos pos;
    private final DivineReplicatorBlockEntity.SPAWN_TYPE spawnType;
    public UpdateDivineReplicatorS2CPacket(BlockPos pos, DivineReplicatorBlockEntity.SPAWN_TYPE spawnType) {
        this.pos = pos;
        this.spawnType = spawnType;
    }

    public UpdateDivineReplicatorS2CPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.spawnType = buf.readEnumConstant(DivineReplicatorBlockEntity.SPAWN_TYPE.class);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeEnumConstant(this.spawnType);
    }

    @Override
    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
    }

    public BlockPos getPos() { return this.pos; }
    public DivineReplicatorBlockEntity.SPAWN_TYPE getSpawnType() { return this.spawnType; }

//    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
//                               PacketByteBuf buf, PacketSender responseSender) {
//
//        DivineReplicatorBlockEntity.SPAWN_TYPE spawnType = buf.readEnumConstant(DivineReplicatorBlockEntity.SPAWN_TYPE.class);
//        BlockPos position = buf.readBlockPos();
//
//        if(client.world.getBlockEntity(position) instanceof DivineReplicatorBlockEntity blockEntity) {
//            blockEntity.setSpawnType(spawnType);
//        }
//    }
}
