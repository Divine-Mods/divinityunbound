package name.divinityunbound.block.entity;

import name.divinityunbound.item.ModItems;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.screen.CoalGeneratorScreenHandler;
import name.divinityunbound.screen.ProteusConverterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ProteusConverterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public ProteusConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROTEUS_CONVERTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ProteusConverterBlockEntity.this.progress;
                    case 1 -> ProteusConverterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ProteusConverterBlockEntity.this.progress = value;
                    case 1 -> ProteusConverterBlockEntity.this.maxProgress = value;
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
        public int getMaxCountPerStack() {
            return 64;
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return true;
        }

        @Override
        public void markDirty() {
            ProteusConverterBlockEntity.this.markDirty();
        }
    };
    public final InventoryStorage inventoryWrapper = InventoryStorage.of(internalInventory, null);

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("proteus_converter.inventory", internalInventory.toNbtList());
        nbt.putInt("proteus_converter.cooldown", cooldown);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        internalInventory.readNbtList((NbtList) nbt.get("proteus_converter.inventory"));
        cooldown = nbt.getInt("proteus_converter.cooldown");
    }

    private int cooldown = 0;
    private static final int DEFAULT_MAX_COOLDOWN = 20;
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
//        if (cooldown < DEFAULT_MAX_COOLDOWN) {
//            cooldown++;
//            return;
//        }
//        cooldown = 0;
        if (isOutputSlotEmptyOrReceivable()) {
            if (this.hasRecipe()) {
                this.increaseCraftProgress();

                if (hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
                markDirty(world, pos, state);
            }
            else {
                this.resetProgress();
            }
        }
        else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void craftItem() {
        this.internalInventory.getStack(INPUT_SLOT).setCount(this.internalInventory.getStack(INPUT_SLOT).getCount() - 1);

        this.internalInventory.setStack(OUTPUT_SLOT, new ItemStack(ModItems.CELESTIUM_DUST,
                this.internalInventory.getStack(OUTPUT_SLOT).getCount() + 1));
    }

    private boolean hasRecipe() {
        return hasItemInInputSlot() && canInsertAmountIntoOutputSlot(new ItemStack(ModItems.CELESTIUM_DUST, 1)) &&
                canInsertItemIntoOutputSlot(ModItems.CELESTIUM_DUST);
    }

    private boolean hasItemInInputSlot() {
        return !this.internalInventory.getStack(INPUT_SLOT).isEmpty()
                && (this.internalInventory.getStack(INPUT_SLOT).getItem().equals(ModItems.GRAIN_OF_TIME));
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.internalInventory.getStack(OUTPUT_SLOT).getItem() == item || this.internalInventory.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.internalInventory.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= this.internalInventory.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.internalInventory.getStack(OUTPUT_SLOT).isEmpty() || this.internalInventory.getStack(OUTPUT_SLOT).getCount() < this.internalInventory.getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Coal Generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ProteusConverterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
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

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }
    private void resetProgress() {
        this.progress = 0;
    }
}
