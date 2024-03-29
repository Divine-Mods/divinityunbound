package name.divinityunbound.block.entity;

import name.divinityunbound.screen.ZeusBatteryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;
import java.util.stream.Collectors;

public class DemetersHarvesterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int DEFAULT_RANGE = 12;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;
    public DemetersHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEMETERS_HARVESTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DemetersHarvesterBlockEntity.this.progress;
                    case 1 -> DemetersHarvesterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DemetersHarvesterBlockEntity.this.progress = value;
                    case 1 -> DemetersHarvesterBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(5000000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("demeters_harvester.progress", progress);
        nbt.putInt("demeters_harvester.speedCount", speedCount);
        nbt.putInt("demeters_harvester.quantityCount", quantityCount);
        nbt.putInt("demeters_harvester.rangeCount", rangeCount);
        nbt.putLong(("demeters_harvester.energy"), energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        progress = nbt.getInt("demeters_harvester.progress");
        speedCount = nbt.getInt("demeters_harvester.speedCount");
        quantityCount = nbt.getInt("demeters_harvester.quantityCount");
        rangeCount = nbt.getInt("demeters_harvester.rangeCount");
        energyStorage.amount = nbt.getLong("demeters_harvester.energy");
    }

    public void setEnergyAmount(long amount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.insert(amount, transaction);
            transaction.commit();
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Demeters Harvester");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;//new DemetersHarvesterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private int cooldown = 0;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (cooldown < 20) {
            cooldown++;
            return;
        }
        cooldown = 0;

        int range = (DEFAULT_RANGE + (rangeCount*2)) / 2;
        int posx = pos.getX();
        int posy = pos.getY();
        int posz = pos.getZ();
        Box box = new Box(posx - range, posy - range, posz - range,
                posx + range, posy + range, posz + range);

        List<BlockPos> blockPositions = BlockPos.stream(-4, 0, -4, 4, 0, 4).filter((position) -> {
            return Math.abs(position.getX()) == 4 || Math.abs(position.getZ()) == 4;
        }).map(BlockPos::toImmutable).toList();

        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                BlockPos offsetPos = pos.add(i, 0, j);
                BlockState blockState = world.getBlockState(offsetPos);
                if (blockState.getBlock() instanceof CropBlock) {
                    if (((CropBlock) blockState.getBlock()).getAge(blockState) ==
                            ((CropBlock) blockState.getBlock()).getMaxAge()) {
                        ((ModifiableWorld) world).breakBlock(offsetPos, true);
                    }
                    else if (blockState.getBlock() instanceof TorchflowerBlock) {
                        if (((CropBlock) blockState.getBlock()).getAge(blockState) ==
                                ((CropBlock) blockState.getBlock()).getMaxAge() - 1) {
                            ((ModifiableWorld) world).breakBlock(offsetPos, true);
                        }
                    }
                }
                else if (blockState.getBlock().equals(Blocks.MELON) ||
                        blockState.getBlock().equals(Blocks.PUMPKIN)) {
                    ((ModifiableWorld) world).breakBlock(offsetPos, true);
                }
            }
        }
//        blockPositions.forEach((offset) -> {
//
//        });

//        List<BlockState> blocks = world.getStatesInBoxIfLoaded(box)
//                .filter((blockState) -> blockState.getBlock() instanceof CropBlock)
//                .toList();
//
//        if (!blocks.isEmpty()) {
//            blocks.forEach((block) -> {
//                if (((CropBlock) block.getBlock()).getAge(block) ==
//                        ((CropBlock) block.getBlock()).getMaxAge()) {
//                    //((ModifiableWorld) world).breakBlock(block)
//                }
//            });
//        }
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(25000L, transaction);
            transaction.commit();
        }
    }

    private void fillUpOnEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            if (this.energyStorage.getCapacity() - this.energyStorage.getAmount() <= 64) {
                this.energyStorage.insert(
                        this.energyStorage.getCapacity() - this.energyStorage.getAmount(),
                        transaction);
            }
            else {
                this.energyStorage.insert(64, transaction);
            }
            transaction.commit();
        }
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
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
}
