package name.divinityunbound.block;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ModBlockConstants {
    public static final List<BlockPos> UPGRADE_PROVIDER_OFFSETS = BlockPos.stream(-1, 0, -1, 1, 0, 1).filter((pos) -> {
        return Math.abs(pos.getX()) == 1 || Math.abs(pos.getZ()) == 1;
    }).map(BlockPos::toImmutable).toList();
}
