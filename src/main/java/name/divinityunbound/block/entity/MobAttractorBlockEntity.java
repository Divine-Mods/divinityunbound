package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.MobAttractorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MobAttractorBlockEntity extends BlockEntity {
    private static final int DEFAULT_RANGE = 6;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int attackCooldown = 0;
    private int upgradeCheck = 0;

    public MobAttractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOB_ATTRACTOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MobAttractorBlockEntity.this.progress;
                    case 1 -> MobAttractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MobAttractorBlockEntity.this.progress = value;
                    case 1 -> MobAttractorBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("mob_attractor.progress", progress);
        nbt.putInt("mob_attractor.speedCount", speedCount);
        nbt.putInt("mob_attractor.quantityCount", quantityCount);
        nbt.putInt("mob_attractor.rangeCount", rangeCount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        progress = nbt.getInt("mob_attractor.progress");
        speedCount = nbt.getInt("mob_attractor.speedCount");
        quantityCount = nbt.getInt("mob_attractor.quantityCount");
        rangeCount = nbt.getInt("mob_attractor.rangeCount");
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (state.get(MobAttractorBlock.ENABLED).booleanValue()) {
            for (int i = 0; i <= speedCount; i++) {
                increaseCraftProgress();

                markDirty(world, pos, state);

                if (hasCraftingFinished()) {
                    List<LivingEntity> livingEntities = getLivingEntitiesInRange(world, pos);

                    // TODO: Maybe change this to pull default_amount + quantityCount
                    for (LivingEntity entity : livingEntities) {
                        BlockPos entityPos = entity.getBlockPos();
                        //((MobEntity)entity).setAiDisabled(true);
                        Vec3d vec = new Vec3d(
                                pos.getX() - entityPos.getX(),
                                pos.getY() - entityPos.getY(),
                                pos.getZ() - entityPos.getZ()
                        );
                        entity.move(MovementType.SELF, vec);
                    }
                    resetProgress();
                }
            }
        }
        else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private List<LivingEntity> getLivingEntitiesInRange(World world, BlockPos pos) {
        int range = (DEFAULT_RANGE + (rangeCount*2)) / 2;
        int posx = pos.getX();
        int posy = pos.getY();
        int posz = pos.getZ();
        Box box = new Box(posx - range, posy - range, posz - range,
                posx + range, posy + range, posz + range);
        List<LivingEntity> livingEntities = world.getEntitiesByClass(LivingEntity.class, box, e -> (
                !e.isPlayer()
        ));

        return livingEntities;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public void resetUpgrades() {
        this.speedCount = 0;
        this.quantityCount = 0;
        this.rangeCount = 0;
    }

    public void increaseSpeed() {
        this.speedCount++;
    }

    public void increaseQuantity() {
        this.quantityCount++;
    }

    public void increaseRange() {
        this.rangeCount++;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftProgress() {
        this.progress++;
    }
}
