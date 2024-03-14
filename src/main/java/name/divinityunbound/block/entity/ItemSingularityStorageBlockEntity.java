package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.ItemSingularityStorageScreenHandler;
import name.divinityunbound.screen.SpaceTimeEvaporatorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.FilteringStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
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
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Iterator;
import java.util.List;

public class ItemSingularityStorageBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    //private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int CHECK_UPGRADE_TICKS = 20;

    private int speedCount = 0;
    private int quantityCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int itemCount = 0;
    private int upgradeCheck = 0;
    public ItemSingularityStorageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_SINGULARITY_STORAGE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ItemSingularityStorageBlockEntity.this.progress;
                    case 1 -> ItemSingularityStorageBlockEntity.this.maxProgress;
                    case 2 -> ItemSingularityStorageBlockEntity.this.itemCount;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ItemSingularityStorageBlockEntity.this.progress = value;
                    case 1 -> ItemSingularityStorageBlockEntity.this.maxProgress = value;
                    case 2 -> ItemSingularityStorageBlockEntity.this.itemCount = value;
                };
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    public final SimpleInventory internalInventory = new SimpleInventory(1) { // 3 here is the slot count
        @Override
        public int getMaxCountPerStack() {
            return 1;
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return true;
        }

        @Override
        public void markDirty() {
            ItemSingularityStorageBlockEntity.this.markDirty();
        }
    };
    public final InventoryStorage inventoryWrapper = InventoryStorage.of(internalInventory, null);

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("item_singularity_storage.inventory", internalInventory.toNbtList());
        nbt.putInt("item_singularity_storage.progress", progress);
        nbt.putInt("item_singularity_storage.itemCount", itemCount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        internalInventory.readNbtList((NbtList) nbt.get("item_singularity_storage.inventory"));
        progress = nbt.getInt("item_singularity_storage.progress");
        itemCount = nbt.getInt("item_singularity_storage.itemCount");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Item Singularity Storage");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ItemSingularityStorageScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
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

    public static final List<BlockPos> UPGRADE_PROVIDER_OFFSETS = BlockPos.stream(-1, 0, -1, 1, 0, 1).filter((pos) -> {
        return Math.abs(pos.getX()) == 1 || Math.abs(pos.getZ()) == 1;
    }).map(BlockPos::toImmutable).toList();

    public void countUpgrades(World world, BlockPos pos) {
        speedCount = 0;
        quantityCount = 0;
        Iterator it = UPGRADE_PROVIDER_OFFSETS.iterator();
        while(it.hasNext()) {
            BlockPos blockPosOffset = (BlockPos)it.next();
            BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                    pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
            if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                speedCount++;
            }
            else if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                quantityCount++;
            }
        }
    }
}
