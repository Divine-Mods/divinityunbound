package name.divinityunbound.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Stream;

public class ProteusConverterBlockEntity extends BlockEntity {

    public ProteusConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTEUS_CONVERTER_BLOCK_ENTITY, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("proteus_converter_block.cooldown", cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        cooldown = nbt.getInt("proteus_converter_block.cooldown");
    }

    private int cooldown = 0;
    private static final int DEFAULT_MAX_COOLDOWN = 20;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (cooldown < DEFAULT_MAX_COOLDOWN) {
            cooldown++;
            return;
        }
        cooldown = 0;
    }
}
