package name.divinityunbound.block.entity;

import name.divinityunbound.block.custom.DivineReplicatorBlock;
import name.divinityunbound.block.custom.PortunusPropagatorBlock;
import name.divinityunbound.screen.PortunusPropagatorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;

public class PortunusPropagatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int DEFAULT_RANGE = 8;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;
    public PortunusPropagatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PORTUNUS_PROPAGATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PortunusPropagatorBlockEntity.this.progress;
                    case 1 -> PortunusPropagatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> PortunusPropagatorBlockEntity.this.progress = value;
                    case 1 -> PortunusPropagatorBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    public final SimpleInventory internalInventory = new SimpleInventory(9) {
        @Override
        public void markDirty() {
            PortunusPropagatorBlockEntity.this.markDirty();
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
        nbt.put("portunus_propagator.internalInventory", internalInventory.toNbtList());
        nbt.putInt("portunus_propagator.progress", progress);
        nbt.putInt("portunus_propagator.speedCount", speedCount);
        nbt.putInt("portunus_propagator.quantityCount", quantityCount);
        nbt.putInt("portunus_propagator.rangeCount", rangeCount);
        nbt.putLong(("portunus_propagator.energy"), energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        internalInventory.readNbtList((NbtList) nbt.get("portunus_propagator.internalInventory"));
        progress = nbt.getInt("portunus_propagator.progress");
        speedCount = nbt.getInt("portunus_propagator.speedCount");
        quantityCount = nbt.getInt("portunus_propagator.quantityCount");
        rangeCount = nbt.getInt("portunus_propagator.rangeCount");
        energyStorage.amount = nbt.getLong("portunus_propagator.energy");
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
        return Text.literal("Portunus Propagator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PortunusPropagatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private int cooldown = 0;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (!state.get(PortunusPropagatorBlock.ENABLED)) return;

        int range = (DEFAULT_RANGE + (rangeCount * 2)) / 2;
        if (hasEnoughEnergyToCraft()) {
            if (cooldown > (60 - (speedCount * 6))) {
                List<AnimalEntity> animals = getEntities();
                if (countBreedableAnimals(animals) > 1) {
                    ItemStack itemStack = ItemStack.EMPTY;
                    for (int i = 0; i < internalInventory.size(); i++) {
                        itemStack = internalInventory.getStack(i);
                        if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
                            breedAnimals(itemStack, animals);
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

    private int countBreedableAnimals(List<AnimalEntity> animals) {
        int count = 0;
        for (AnimalEntity animal : animals) {
            if (animal.getBreedingAge() == 0 && animal.canEat()) { count++; }
        }
        return count;
    }

    private void breedAnimals(ItemStack itemStack, List<AnimalEntity> animals) {
        if (animals.size() > 1) {
            AnimalEntity first = null;
            for (AnimalEntity animal : animals) {
                if (animal.isBreedingItem(itemStack)) {
                    if (first != null && animal.getBreedingAge() == 0 && animal.canEat()) {
                        first.lovePlayer(null);
                        animal.lovePlayer(null);
                        if (animal.canBreedWith(first)) {
                            animal.breed((ServerWorld) world, first);
                            itemStack.decrement(2);
                            first = null;
                        }
                    }
                    else if (first == null && animal.getBreedingAge() == 0 && animal.canEat()) {
                        first = animal;
                        continue;
                    }
                }
            }
        }
    }

    private boolean hasItemInSlot() {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < internalInventory.size(); i++) {
            itemStack = internalInventory.getStack(i);
        }
        return true;
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= 333L;
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(333L, transaction);
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
