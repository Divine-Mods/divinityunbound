package name.divinityunbound.block.entity;

import name.divinityunbound.screen.DemetersHarvesterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class PersephonesBlessingBlockEntity extends BlockEntity {
    private static final int DEFAULT_RANGE = 8;
    private static final int MAX_CROPS_GROWN = 8;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;
    public PersephonesBlessingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PERSEPHONES_BLESSING_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PersephonesBlessingBlockEntity.this.progress;
                    case 1 -> PersephonesBlessingBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PersephonesBlessingBlockEntity.this.progress = value;
                    case 1 -> PersephonesBlessingBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(500000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        // nbt.put("persephones_blessing.internalInventory", internalInventory.toNbtList());
        nbt.putInt("persephones_blessing.progress", progress);
        nbt.putInt("persephones_blessing.speedCount", speedCount);
        nbt.putInt("persephones_blessing.quantityCount", quantityCount);
        nbt.putInt("persephones_blessing.rangeCount", rangeCount);
        nbt.putLong(("persephones_blessing.energy"), energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        // internalInventory.readNbtList((NbtList) nbt.get("persephones_blessing.internalInventory"));
        progress = nbt.getInt("persephones_blessing.progress");
        speedCount = nbt.getInt("persephones_blessing.speedCount");
        quantityCount = nbt.getInt("persephones_blessing.quantityCount");
        rangeCount = nbt.getInt("persephones_blessing.rangeCount");
        energyStorage.amount = nbt.getLong("persephones_blessing.energy");
    }

    public void setEnergyAmount(long amount) {
        try(Transaction transaction = Transaction.openOuter()) {
            energyStorage.insert(amount, transaction);
            transaction.commit();
        }
    }

    private int cooldown = 0;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (cooldown < (50 - (speedCount * 6))) {
            cooldown++;
            return;
        }
        cooldown = 0;

        int range = (DEFAULT_RANGE + (rangeCount * 2)) / 2;
        fertilizeCrops(world, pos, range);
    }

    private void fertilizeCrops(World world, BlockPos pos, int range) {
        int cropsGrown = 0;
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos offsetPos = pos.add(i, 0, j);
                BlockState blockState = world.getBlockState(offsetPos);
                if (blockState.getBlock() instanceof CropBlock) {
                    if (((CropBlock) blockState.getBlock()).getAge(blockState) <
                            ((CropBlock) blockState.getBlock()).getMaxAge()) {
                        if(world.getRandom().nextInt(100) < 8) {
                            ((CropBlock) blockState.getBlock()).applyGrowth(world, offsetPos, blockState);
                            cropsGrown++;
                            if (cropsGrown > MAX_CROPS_GROWN) {
                                return;
                            }
                        }
                    }
                }
            }
        }
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
