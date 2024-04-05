package name.divinityunbound.block.entity;

import name.divinityunbound.block.custom.VelesGathererBlock;
import name.divinityunbound.screen.VelesGathererScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;

public class VelesGathererBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int DEFAULT_RANGE = 8;
    private static final int SHEAR_SLOT = 0;
    private static final int BUCKET_SLOT = 1;

    private static final ItemStack milkBucket = new ItemStack(Items.MILK_BUCKET);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;
    public VelesGathererBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VELES_GATHERER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> VelesGathererBlockEntity.this.progress;
                    case 1 -> VelesGathererBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> VelesGathererBlockEntity.this.progress = value;
                    case 1 -> VelesGathererBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    public final SimpleInventory internalInventory = new SimpleInventory(2) {
        @Override
        public boolean isValid(int slot, ItemStack stack) {
            if (slot == SHEAR_SLOT && stack.getItem().equals(Items.SHEARS)) {
                return true;
            }
            if (slot == BUCKET_SLOT && stack.getItem().equals(Items.BUCKET)) {
                return true;
            }
            return false;
        }

        @Override
        public void markDirty() {
            VelesGathererBlockEntity.this.markDirty();
        }
    };
    public final InventoryStorage inventoryWrapper = InventoryStorage.of(internalInventory, null);

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
        nbt.put("veles_gatherer.internalInventory", internalInventory.toNbtList());
        nbt.putInt("veles_gatherer.progress", progress);
        nbt.putInt("veles_gatherer.speedCount", speedCount);
        nbt.putInt("veles_gatherer.quantityCount", quantityCount);
        nbt.putInt("veles_gatherer.rangeCount", rangeCount);
        nbt.putLong(("veles_gatherer.energy"), energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        internalInventory.readNbtList((NbtList) nbt.get("veles_gatherer.internalInventory"));
        progress = nbt.getInt("veles_gatherer.progress");
        speedCount = nbt.getInt("veles_gatherer.speedCount");
        quantityCount = nbt.getInt("veles_gatherer.quantityCount");
        rangeCount = nbt.getInt("veles_gatherer.rangeCount");
        energyStorage.amount = nbt.getLong("veles_gatherer.energy");
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
        return Text.literal("Veles Gatherer");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new VelesGathererScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private int cooldown = 0;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (!state.get(VelesGathererBlock.ENABLED)) return;

        int range = (DEFAULT_RANGE + (rangeCount * 2)) / 2;
        if (hasEnoughEnergyToCraft() && (hasShears() || hasBucket())) {
            if (cooldown > (60 - (speedCount * 6))) {
                List<AnimalEntity> animals = getEntities();
                for (AnimalEntity animal : animals) {
                    if (animal instanceof SheepEntity && !animal.isBaby()) {
                        if (hasShears() && ((SheepEntity) animal).isShearable()) {
                            ((SheepEntity) animal).sheared(SoundCategory.PLAYERS);
                            ((SheepEntity) animal).emitGameEvent(GameEvent.SHEAR);
                            damageShears();
                        }
                    }
                    else if (animal instanceof CowEntity && !animal.isBaby()) {
                        if (hasBucket()) {
                            internalInventory.getStack(BUCKET_SLOT).decrement(1);
                            ItemEntity itemEntity = new ItemEntity(world, (double)animal.getX() + 0.5, (double)animal.getY() + 0.5, (double)animal.getZ() + 0.5, milkBucket);
                            itemEntity.setToDefaultPickupDelay();
                            world.spawnEntity(itemEntity);
                        }
                    }
                }
                cooldown = 0;
            }
            else {
                cooldown++;
            }

            extractEnergy();
        }
    }

    private void damageShears() {
        if(internalInventory.getStack(SHEAR_SLOT).damage(1, world.getRandom(), null)) {
            internalInventory.getStack(SHEAR_SLOT).setCount(0);
        }
    }

    public boolean hasShears() {
        return !internalInventory.getStack(SHEAR_SLOT).isEmpty() && internalInventory.getStack(SHEAR_SLOT).getItem().equals(Items.SHEARS);
    }

    public boolean hasBucket() {
        return !internalInventory.getStack(BUCKET_SLOT).isEmpty() && internalInventory.getStack(BUCKET_SLOT).getItem().equals(Items.BUCKET);
    }

    private List<AnimalEntity> getEntities() {
        int range = (DEFAULT_RANGE + (rangeCount * 2)) / 2;
        int posx = pos.getX();
        int posy = pos.getY();
        int posz = pos.getZ();
        Box box = new Box(posx - range, posy - range, posz - range,
                posx + range, posy + range, posz + range);
        List<AnimalEntity> livingEntities = world.getEntitiesByClass(AnimalEntity.class, box, e -> (
                !e.isPlayer()
        ));
        return livingEntities;
    }

    private boolean hasItemInSlot() {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < internalInventory.size(); i++) {
            itemStack = internalInventory.getStack(i);
        }
        return true;
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= 120L;
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(120L, transaction);
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
