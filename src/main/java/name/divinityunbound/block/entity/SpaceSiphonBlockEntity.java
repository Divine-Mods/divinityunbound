package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class SpaceSiphonBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {

    private final DefaultedList<ItemEntity> detectedList = DefaultedList.ofSize(0);
    private final Hashtable<ItemEntity, Long> detectedListTimes = new Hashtable<>();
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int DEFAULT_RANGE = 6;

    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public SpaceSiphonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPACE_SIPHON_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SpaceSiphonBlockEntity.this.progress;
                    case 1 -> SpaceSiphonBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpaceSiphonBlockEntity.this.progress = value;
                    case 1 -> SpaceSiphonBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("space_siphon.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("space_siphon.progress");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        //PlayerEntity pe = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);
        BlockPos bp = pos.offset(Direction.DOWN);
        Inventory belowInventory = getInventoryAt(world, (double) bp.getX() + 0.5,
                (double) bp.getY() + 0.5, (double) bp.getZ() + 0.5);
        //pe.sendMessage(Text.literal("Stack nbt "));
        if (belowInventory != null) {
            this.detectAndRetrieveItems(world, pos);
            ItemStack itemStack = this.getStack(INPUT_SLOT);
            if (!itemStack.isEmpty()) {
                attemptExtractionToBelowInv(belowInventory, itemStack);
            }
            markDirty(world, pos, state);
        }
        else {
            markDirty(world, pos, state);
        }
    }

    private void attemptExtractionToBelowInv(Inventory belowInventory, ItemStack itemStack) {
        for (int i = 0; i < belowInventory.size(); i++) {
            if (this.canInsertIntoInv(belowInventory, itemStack, i, Direction.DOWN)) {
                ItemStack checkedStack = belowInventory.getStack(i);

                if (checkedStack.isEmpty()) {
                    belowInventory.setStack(i, itemStack.copy());
                    this.removeStack(INPUT_SLOT);
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

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return slot == INPUT_SLOT && direction == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == INPUT_SLOT && direction == Direction.DOWN;
    }

    public void detectAndRetrieveItems(World world, BlockPos pos) {
        int range = DEFAULT_RANGE / 2;
        int posx = pos.getX();
        int posy = pos.getY();
        int posz = pos.getZ();
        Box box = new Box(posx - range, posy - range, posz - range,
                posx + range, posy + range, posz + range);
        List<ItemEntity> itemEntities = world.getEntitiesByClass(ItemEntity.class, box, e -> (
                !e.isInLava()
        ));

        for (ItemEntity itemEntity: itemEntities) {
            //ItemEntity newItem = new ItemEntity((EntityType<? extends ItemEntity>) itemEntity.getType(), world);
            addStack(itemEntity);
        }
    }

    private void addStack(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getStack();
        boolean markedForRemoval = false;

        ItemStack checkedStack = getStack(INPUT_SLOT);
        if (checkedStack.isEmpty()) {
            // set empty slot with stack
            itemEntity.remove(Entity.RemovalReason.DISCARDED);
            setStack(INPUT_SLOT, stack);
            return;
        }
        if (stack.getItem() == checkedStack.getItem()) {
            // add as much to stack as possible
            if (checkedStack.isStackable()) {
                int originalCount = stack.getCount();
                boolean removed = false;
                for (int c = 1; c <= originalCount; c++) {
                    if (checkedStack.getCount() < checkedStack.getMaxCount()) {
                        // add to checked stack
                        checkedStack.setCount(checkedStack.getCount() + 1);
                        stack.setCount(stack.getCount() - 1);
                        if (!removed) {
                            itemEntity.remove(Entity.RemovalReason.DISCARDED);
                        }
                    } else {
                        if (!removed) {
                            itemEntity.remove(Entity.RemovalReason.DISCARDED);
                        }
                        // throw out stack that is trying to be added if it can't find a slot
                        markedForRemoval = true;
                    }
                }
                if (!markedForRemoval) {
                    setStack(INPUT_SLOT, checkedStack);
                    return;
                }
            }
        }
        if (markedForRemoval) {
            ItemEntity itemStackEntity = new ItemEntity(this.getWorld(), this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ(), stack);
            this.getWorld().spawnEntity(itemStackEntity);
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

    public void addToDetectedList(ItemEntity ie) {
        if (!this.detectedList.contains(ie)) {
            this.detectedList.add(ie);
        }
    }

    public void removeFromDetectedList(ItemEntity ie) {
        this.detectedList.remove(ie);
    }
}
