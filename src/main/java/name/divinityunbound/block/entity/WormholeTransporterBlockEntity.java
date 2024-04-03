package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.WormholeTransporterBlock;
import name.divinityunbound.fluid.ModFluids;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.WormholeTransporterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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

public class WormholeTransporterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory,
        ImplementedInventory, SidedInventory, GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int CARD_SLOT = 0;
    private static final int ITEM_SLOT = 1;
    private static final int CHECK_UPGRADE_TICKS = 20;
    private Direction localDir;

    private int speedCount = 0;
    private int quantityCount = 0;
    private int progress = 0;
    private int maxProgress = 72;
    private int upgradeCheck = 0;

    private int itemsActive = 0;
    private int energyActive = 0;
    private int fluidActive = 0;

    private boolean itemsEnabled;
    private boolean fluidsEnabled;
    private boolean energyEnabled;

    protected final PropertyDelegate propertyDelegate;
    public WormholeTransporterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WORMHOLE_TRANSPORTER_BLOCK_ENTITY, pos, state);
        localDir = state.get(WormholeTransporterBlock.FACING).getOpposite();
        itemsEnabled = false;
        fluidsEnabled = false;
        energyEnabled = false;
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WormholeTransporterBlockEntity.this.itemsActive;
                    case 1 -> WormholeTransporterBlockEntity.this.energyActive;
                    case 2 -> WormholeTransporterBlockEntity.this.fluidActive;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WormholeTransporterBlockEntity.this.itemsActive = value;
                    case 1 -> WormholeTransporterBlockEntity.this.energyActive = value;
                    case 2 -> WormholeTransporterBlockEntity.this.fluidActive = value;
                };
            }

            @Override
            public int size() {
                return 3;
            }
        };

//        ServerPlayNetworking.registerGlobalReceiver(DivinityUnboundNetworkingConstants.WORMHOLE_TRANSPORTER_SYNC_ID,
//                ((server, player, handler, buf, responseSender) -> {
//                    int temp = buf.readInt();
//                    server.execute(() -> {
//                        this.itemsActive = temp;
//                        itemsActive = temp;
//                        propertyDelegate.set(0, temp);
//                        this.propertyDelegate.set(0, temp);
//                    });
//                }));
    }

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(2500000, Integer.MAX_VALUE, Integer.MAX_VALUE) {
        @Override
        public boolean supportsExtraction() {
            return getEnergyEnabled() && isImportMode();
        }

        @Override
        public boolean supportsInsertion() {
            return getEnergyEnabled() && isExportMode();
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BUCKET * 32;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }

        @Override
        public boolean supportsExtraction() {
            return getFluidsEnabled();
        }

        @Override
        public boolean supportsInsertion() {
            return getFluidsEnabled();
        }
    };
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("wormhole_transporter.progress", this.progress);
        nbt.putLong(("wormhole_transporter.energy"), this.energyStorage.amount);
        nbt.put("wormhole_transporter.variant", this.fluidStorage.variant.toNbt());
        nbt.putLong("wormhole_transporter.fluid_amount", this.fluidStorage.amount);
        nbt.putBoolean("wormhole_transporter.itemsEnabled", itemsEnabled);
        nbt.putBoolean("wormhole_transporter.fluidsEnabled", fluidsEnabled);
        nbt.putBoolean("wormhole_transporter.energyEnabled", energyEnabled);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.progress = nbt.getInt("wormhole_transporter.progress");
        this.energyStorage.amount = nbt.getLong("wormhole_transporter.energy");
        if (energyStorage.getAmount() > energyStorage.getCapacity()) {
            energyStorage.amount = energyStorage.getCapacity();
        }
        this.fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("wormhole_transporter.variant"));
        this.fluidStorage.amount = nbt.getLong("wormhole_transporter.fluid_amount");
        itemsEnabled = nbt.getBoolean("wormhole_transporter.itemsEnabled");
        fluidsEnabled = nbt.getBoolean("wormhole_transporter.fluidsEnabled");
        energyEnabled = nbt.getBoolean("wormhole_transporter.energyEnabled");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Wormhole Transporter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        WormholeTransporterScreenHandler sh = new WormholeTransporterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);

        return sh;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        //fillUpOnEnergy();

        if (hasItemInInputSlot()) {
            BlockPos neighbor = pos.offset(localDir);
            //Inventory neighborInv = getInventoryAt(world, neighbor.getX(), neighbor.getY(), neighbor.getZ());
            Storage<ItemVariant> neighborInv = ItemStorage.SIDED.find(world, neighbor, localDir);

            EnergyStorage neighborEnergyStorage = findEnergyStorage(world, pos);
            Storage<FluidVariant> neighborFluidStorage = findFluidStorage(world, pos);
//            PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, false);
//            if (player != null) {
//            player.sendMessage(
//                        Text.literal("Items active: " + this.itemsActive + " " + itemsActive + " " + getItemsActive())
//                );
//                player.sendMessage(
//                        Text.literal("Items active: " + this.propertyDelegate.get(0) + " " + propertyDelegate.get(0) + " " + getItemsActive())
//                );
//            }
            if (isImportMode()) {

                if (ioCardHasBlockPos()) {
                    int[] savedPos = this.getStack(CARD_SLOT).getNbt().getIntArray("blockpos");

                    if (world.getBlockState(new BlockPos(savedPos[0], savedPos[1], savedPos[2]))
                            .getBlock().equals(ModBlocks.WORMHOLE_TRANSPORTER)) {
                        WormholeTransporterBlockEntity wormhole = ((WormholeTransporterBlockEntity) world
                                .getBlockEntity(new BlockPos(savedPos[0], savedPos[1], savedPos[2])));
                        // Import Items from Linked Wormhole
                        if (this.getItemsEnabled()) {
                            attemptExtractInvToInternalInv(wormhole);
                        }
                        // Import Energy from Linked Wormhole
                        if (this.getEnergyEnabled()) {
                            pushEnergyToAdjacentStorage(wormhole.energyStorage, this.energyStorage);
                        }
                        // Import Fluid from Linked Wormhole
                        if (this.getFluidsEnabled()) {
                            pushFluidToAdjacentStorage(wormhole.fluidStorage, this.fluidStorage);
                        }
                    }
                }

                if (neighborInv != null && !this.getStack(ITEM_SLOT).isEmpty() && this.getItemsEnabled()) { // Import Items to Neighbor Inv
                    //attemptExtractionToExternalInv(neighborInv, this.getStack(ITEM_SLOT));
                    // TODO: cleanup and move to separate method
                    if (this.getStack(ITEM_SLOT).getCount() > 0) {
                        try (Transaction transaction = Transaction.openOuter()) {
                            //neighborInv.extract(neighborInv.iterator().next().getResource(), 64, transaction);
                            int amountTransferred = (int) neighborInv.insert(ItemVariant.of(this.getStack(ITEM_SLOT)), this.getStack(ITEM_SLOT).getCount(), transaction);
                            this.getStack(ITEM_SLOT).setCount(this.getStack(ITEM_SLOT).getCount() - amountTransferred);
                            transaction.commit();
                        }
                    }
                }

                if (neighborEnergyStorage != null && this.getEnergyEnabled()) { // Import Energy into Neighbor Energy Storage
                    pushEnergyToAdjacentStorage(this.energyStorage, neighborEnergyStorage);
                }

                if (neighborFluidStorage != null && this.getFluidsEnabled()) { // Import Fluid into Neighbor Fluid Storage
                    pushFluidToAdjacentStorage(this.fluidStorage, neighborFluidStorage);
                }
            }
            else if (isExportMode()) {  // Export Mode
                if (neighborInv != null && this.getItemsEnabled()) { // Import Items from Neighbor Inv
                    //attemptExtractionToInternalInv(neighborInv);
                    // TODO: cleanup and move to separate method
                    try (Transaction transaction = Transaction.openOuter()) {
                        Iterator it = neighborInv.iterator();
                        while (it.hasNext()) {
                            StorageView<ItemVariant> storage = (StorageView<ItemVariant>) it.next();
                            ItemVariant itemVar = storage.getResource();

                            if (!itemVar.isBlank() && this.getStack(ITEM_SLOT).isEmpty()) {
                                int amountTransferred = (int) neighborInv.extract(itemVar, 64, transaction);
                                if (amountTransferred > 0) {
                                    this.setStack(ITEM_SLOT, new ItemStack(itemVar.getItem(), amountTransferred));
                                    transaction.commit();
                                    break;
                                }
                            } else if (!itemVar.isBlank() && this.getStack(ITEM_SLOT).getCount() > 0 && this.getStack(ITEM_SLOT).getItem().equals(itemVar.getItem())) {
                                int amountTransferred = (int) neighborInv.extract(itemVar, this.getStack(ITEM_SLOT).getMaxCount() - this.getStack(ITEM_SLOT).getCount(), transaction);
                                if (amountTransferred > 0) {
                                    this.getStack(ITEM_SLOT).setCount(this.getStack(ITEM_SLOT).getCount() + amountTransferred);
                                    transaction.commit();
                                    break;
                                }
                            }
                        }
                    }
                }

                // NOTE: According to API docs, do not pull from other blocks
                //if (neighborEnergyStorage != null && this.getEnergyEnabled()) { // Import Energy from Neighbor Energy Storage
                //    pushEnergyToAdjacentStorage(neighborEnergyStorage, this.energyStorage);
                //}

                if (neighborFluidStorage != null && this.getFluidsEnabled()) { // Import Fluid from Neighbor Fluid Storage
                    pushFluidToAdjacentStorage(neighborFluidStorage, this.fluidStorage);
                }

                if (ioCardHasBlockPos()) {
                    // Export to wormhole inv
                    int[] savedPos = this.getStack(CARD_SLOT).getNbt().getIntArray("blockpos");
                    if (world.getBlockState(new BlockPos(savedPos[0], savedPos[1], savedPos[2]))
                            .getBlock().equals(ModBlocks.WORMHOLE_TRANSPORTER)) {
                        WormholeTransporterBlockEntity wormhole = ((WormholeTransporterBlockEntity) world
                                .getBlockEntity(new BlockPos(savedPos[0], savedPos[1], savedPos[2])));
                        // Export Items to Linked wormhole
                        if (this.getItemsEnabled()) {
                            attemptExtractInvToExternalInv(wormhole);
                        }
                        // Export Energy to Linked Wormhole
                        if (this.getEnergyEnabled()) {
                            pushEnergyToAdjacentStorage(this.energyStorage, wormhole.energyStorage);
                        }
                        // Export Fluid to Linked Wormhole
                        if (this.getFluidsEnabled()) {
                            pushFluidToAdjacentStorage(this.fluidStorage, wormhole.fluidStorage);
                        }
                    }
                }
            }

            markDirty(world, pos, state);
        }
    }

    private EnergyStorage findEnergyStorage(World world, BlockPos pos) {
        return EnergyStorage.SIDED.find(world, pos.offset(localDir), localDir);
    }

    private void pushEnergyToAdjacentStorage(EnergyStorage source, EnergyStorage target) {
        try(Transaction transaction = Transaction.openOuter()) {
            long amountMoved = EnergyStorageUtil.move(
                    source, // from source
                    target, // into target
                    Integer.MAX_VALUE, // no limit on the amount
                    transaction // create a new transaction for this operation
            );
            transaction.commit();
        }
    }

    private void pushFluidToAdjacentStorage(Storage<FluidVariant> source, Storage<FluidVariant> target) {
        try(Transaction transaction = Transaction.openOuter()) {
            StorageUtil.move(source, target, variant -> true, Integer.MAX_VALUE, transaction);
            transaction.commit();
        }
    }

    private Storage<FluidVariant> findFluidStorage(World world, BlockPos pos) {
        return FluidStorage.SIDED.find(world, pos.offset(localDir), localDir);
    }

    private boolean ioCardHasBlockPos() {
        return this.getStack(CARD_SLOT).getNbt() != null && this.getStack(CARD_SLOT).getNbt().contains("blockpos");
    }

    private void attemptExtractionToInternalInv(Inventory neighbor) {
        for (int i = 0; i < neighbor.size(); i++) {
            if (this.canExtractFromInv(neighbor, this.getStack(ITEM_SLOT), i, localDir)) {
                ItemStack checkedStack = neighbor.getStack(i);

                if (checkedStack.isEmpty()) {
                    continue;
                }
                if (this.getStack(ITEM_SLOT).isEmpty()) {
                    this.setStack(ITEM_SLOT, checkedStack.copy());
                    neighbor.removeStack(i);
                    break;

                } else if (this.getStack(ITEM_SLOT).getItem() == checkedStack.getItem()) {
                    // check to see if the items can combine

                    if (this.getStack(ITEM_SLOT).isStackable()) {
                        int originalCount = checkedStack.getCount();

                        for (int c = 1; c <= originalCount; c++) {
                            if (this.getStack(ITEM_SLOT).getCount() < this.getStack(ITEM_SLOT).getMaxCount()) {
                                // add to checked stack
                                this.getStack(ITEM_SLOT).setCount(this.getStack(ITEM_SLOT).getCount() + 1);
                                checkedStack.setCount(checkedStack.getCount() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void attemptExtractInvToInternalInv(WormholeTransporterBlockEntity blockEntity) {
        ItemStack checkedStack = blockEntity.getStack(ITEM_SLOT);
        if (this.getStack(ITEM_SLOT).isEmpty()) {
            this.setStack(ITEM_SLOT, checkedStack.copy());
            blockEntity.removeStack(ITEM_SLOT);
        } else if (this.getStack(ITEM_SLOT).getItem() == checkedStack.getItem()) {
            // check to see if the items can combine
            if (checkedStack.isStackable()) {
                int originalCount = blockEntity.getStack(ITEM_SLOT).getCount();

                for (int c = 1; c <= originalCount; c++) {
                    if (this.getStack(ITEM_SLOT).getCount() < this.getStack(ITEM_SLOT).getMaxCount()) {
                        // add to checked stack
                        this.getStack(ITEM_SLOT).setCount(this.getStack(ITEM_SLOT).getCount() + 1);
                        blockEntity.getStack(ITEM_SLOT).setCount(blockEntity.getStack(ITEM_SLOT).getCount() - 1);
                    }
                }
            }
        }
    }

    private void attemptExtractInvToExternalInv(WormholeTransporterBlockEntity blockEntity) {
        ItemStack checkedStack = blockEntity.getStack(ITEM_SLOT);
        if (checkedStack.isEmpty()) {
            blockEntity.setStack(ITEM_SLOT, checkedStack.copy());
            this.removeStack(ITEM_SLOT);
        } else if (this.getStack(ITEM_SLOT).getItem() == checkedStack.getItem()) {
            // check to see if the items can combine
            if (checkedStack.isStackable()) {
                int originalCount = this.getStack(ITEM_SLOT).getCount();

                for (int c = 1; c <= originalCount; c++) {
                    if (blockEntity.getStack(ITEM_SLOT).getCount() < blockEntity.getStack(ITEM_SLOT).getMaxCount()) {
                        // add to checked stack
                        blockEntity.getStack(ITEM_SLOT).setCount(blockEntity.getStack(ITEM_SLOT).getCount() + 1);
                        this.getStack(ITEM_SLOT).setCount(this.getStack(ITEM_SLOT).getCount() - 1);
                    }
                }
            }
        }
    }

    private boolean isImportMode() {
        return this.getStack(CARD_SLOT).getItem().equals(ModItems.IMPORT_CARD);
    }

    private boolean isExportMode() {
        return this.getStack(CARD_SLOT).getItem().equals(ModItems.EXPORT_CARD);
    }

    private void extractFluid() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.extract(FluidVariant.of(ModFluids.STILL_SPACE_TIME), 500, transaction);
            transaction.commit();
        }
    }

    private void produceFluid() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_SPACE_TIME),
                    500, transaction);
            transaction.commit();
        }
    }

    private void extractEnergy() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(250000L, transaction);
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

    private boolean hasItemInInputSlot() {
        return !this.getStack(CARD_SLOT).isEmpty()
                && (this.getStack(CARD_SLOT).getItem().equals(ModItems.IMPORT_CARD)
                || this.getStack(CARD_SLOT).getItem().equals(ModItems.EXPORT_CARD));
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

    @Override
    public int[] getAvailableSlots(Direction side) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    private void attemptExtractionToExternalInv(Inventory belowInventory, ItemStack itemStack) {
        for (int i = 0; i < belowInventory.size(); i++) {
            if (this.canInsertIntoInv(belowInventory, itemStack, i, localDir)) {
                ItemStack checkedStack = belowInventory.getStack(i);

                if (checkedStack.isEmpty()) {
                    belowInventory.setStack(i, itemStack.copy());
                    this.removeStack(ITEM_SLOT);
                    break;

                } else if (itemStack.getItem() == checkedStack.getItem()) {
                    // check to see if the items can combine

                    if (checkedStack.isStackable()) {
                        int originalCount = itemStack.getCount();

                        for (int c = 1; c <= originalCount; c++) {
                            if (checkedStack.getCount() < checkedStack.getMaxCount()) {
                                // add to checked stack
                                checkedStack.setCount(checkedStack.getCount() + 1);
                                itemStack.setCount(itemStack.getCount() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

    // From HopperBlockEntity
    @Nullable
    private static Inventory getInventoryAt(World world, double x, double y, double z) {
        Inventory inventory = null;
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof InventoryProvider) {
            inventory = ((InventoryProvider)block).getInventory(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity()) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof Inventory) {
                inventory = (Inventory)blockEntity;
                if (inventory instanceof ChestBlockEntity && block instanceof ChestBlock) {
                    inventory = ChestBlock.getInventory((ChestBlock)block, blockState, world, blockPos, true);
                }
            }
        }

        if (inventory == null) {
            List<Entity> list = world.getOtherEntities((Entity)null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES);
            if (!list.isEmpty()) {
                inventory = (Inventory)list.get(world.random.nextInt(list.size()));
            }
        }

        return (Inventory)inventory;
    }

    // From HopperBlockEntity
    private static boolean canInsertIntoInv(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        } else {
            return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
        }
    }

    private static boolean canExtractFromInv(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canExtract(slot, stack, side);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return slot == ITEM_SLOT;
//        Direction localDir = this.getWorld().getBlockState(pos).get(WormholeTransporterBlock.FACING);
//
//        if(side == Direction.DOWN) {
//            return false;
//        }
//
//        if(side == Direction.UP) {
//            return slot == INPUT_SLOT;
//        }
//
//        return switch (localDir) {
//            default -> //NORTH
//                    side.getOpposite() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.getOpposite() == Direction.WEST && slot == INPUT_SLOT;
//            case EAST ->
//                    side.rotateYClockwise() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.rotateYClockwise() == Direction.WEST && slot == INPUT_SLOT;
//            case SOUTH ->
//                    side == Direction.NORTH && slot == INPUT_SLOT ||
//                            side == Direction.WEST && slot == INPUT_SLOT;
//            case WEST ->
//                    side.rotateYCounterclockwise() == Direction.NORTH && slot == INPUT_SLOT ||
//                            side.rotateYCounterclockwise() == Direction.WEST && slot == INPUT_SLOT;
//        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return slot == ITEM_SLOT;
//        Direction localDir = this.getWorld().getBlockState(this.pos).get(WormholeTransporterBlock.FACING);
//
//        if(side == Direction.UP) {
//            return false;
//        }
//
//        // Down extract 2
//        if(side == Direction.DOWN) {
//            return slot == OUTPUT_SLOT;
//        }
//
//        // bottom extract 2
//        // right extract 2
//        return switch (localDir) {
//            default ->  side.getOpposite() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.getOpposite() == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case SOUTH ->   side == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side == Direction.EAST && slot == OUTPUT_SLOT;
//
//            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
//                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;
//        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    public boolean getItemsEnabled() {
        return this.itemsEnabled;
    }

    public void setItemsEnabled(boolean itemsEnabled) {
        this.itemsEnabled = itemsEnabled;
    }

    public boolean getFluidsEnabled() {
        return this.fluidsEnabled;
    }

    public void setFluidsEnabled(boolean fluidsEnabled) {
        this.fluidsEnabled = fluidsEnabled;
    }

    public boolean getEnergyEnabled() {
        return this.energyEnabled;
    }

    public void setEnergyEnabled(boolean energyEnabled) {
        this.energyEnabled = energyEnabled;
    }

}
