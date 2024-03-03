package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.screen.UnholySilencerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.entity.FakePlayer;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import org.jetbrains.annotations.Nullable;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class UnholySilencerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int SWORD_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int DEFAULT_RANGE = 6;

    private static final int CHECK_UPGRADE_TICKS = 20;

    private int speedCount = 0;
    private int quantityCount = 0;
    private int rangeCount = 0;

    protected final PropertyDelegate propertyDelegate;

    //private static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("fakeplayer.unholy_silencer".getBytes()),
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(),
            "fakeplayer.unholy_silencer");
    private FakePlayer fakePlayer = null;
    private DamageSource damageSource = null;
    private int progress = 0;
    private int maxProgress = 72;
    private int attackCooldown = 0;
    private int upgradeCheck = 0;

    public UnholySilencerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UNHOLY_SILENCER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> UnholySilencerBlockEntity.this.progress;
                    case 1 -> UnholySilencerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> UnholySilencerBlockEntity.this.progress = value;
                    case 1 -> UnholySilencerBlockEntity.this.maxProgress = value;
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
        nbt.putInt("unholy_silencer.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("unholy_silencer.progress");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Unholy Silencer");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new UnholySilencerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (world.isReceivingRedstonePower(pos)) {
            return;
        }

        // TODO: tweak attack cooldown and add range upgrade
//        attackCooldown += 1 + (speedCount * 7);
//        if (attackCooldown < 60) {
//            return;
//        }
//        attackCooldown = 0;
        //PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4, false);
        if (hasWeapon() && hasFuel()) {
            if (upgradeCheck >= CHECK_UPGRADE_TICKS) {
                countUpgrades(world, pos);
                upgradeCheck = 0;
            }
            upgradeCheck++;
            for (int i = 0; i <= speedCount; i++) {
                increaseCraftProgress();

                markDirty(world, pos, state);

                if (hasCraftingFinished()) {
                    ItemStack swordItem = getStack(SWORD_SLOT);
                    float swordDamage = ((SwordItem) swordItem.getItem()).getAttackDamage();


                    List<LivingEntity> livingEntities = getLivingEntitiesInRange(world, pos);
                    if (fakePlayer == null) {
                        fakePlayer = FakePlayer.get((ServerWorld) world, GAME_PROFILE);
                    }
                    if (damageSource == null) {
                        damageSource = world.getDamageSources().playerAttack(fakePlayer);
                    }
                    if (fakePlayer.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                        fakePlayer.equipStack(EquipmentSlot.MAINHAND, swordItem);
                    }

                    if (livingEntities.size() > 0) {
                        spendFuel();
                    }
                    for (LivingEntity entity : livingEntities) {
                        entity.damage(damageSource, swordDamage);
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
        int range = DEFAULT_RANGE / 2;
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

    private void spendFuel() {
        this.getStack(FUEL_SLOT).setCount(this.getStack(FUEL_SLOT).getCount() - 1);
    }

    private boolean hasWeapon() {
        return this.getStack(SWORD_SLOT).getCount() > 0 && this.getStack(SWORD_SLOT).isIn(ItemTags.SWORDS);
    }

    private boolean hasFuel() {
        return this.getStack(FUEL_SLOT).getCount() > 0 && this.getStack(FUEL_SLOT).getItem().equals(ModItems.UNHOLY_DUST);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public static final List<BlockPos> UPGRADE_PROVIDER_OFFSETS = BlockPos.stream(-1, 0, -1, 1, 0, 1).filter((pos) -> {
        return Math.abs(pos.getX()) == 1 || Math.abs(pos.getZ()) == 1;
    }).map(BlockPos::toImmutable).toList();

    public void countUpgrades(World world, BlockPos pos) {
        this.speedCount = 0;
        this.quantityCount = 0;
        Iterator it = UPGRADE_PROVIDER_OFFSETS.iterator();
        while(it.hasNext()) {
            BlockPos blockPosOffset = (BlockPos)it.next();
            BlockPos calcPos = new BlockPos(pos.getX() + blockPosOffset.getX(),
                    pos.getY() + blockPosOffset.getY(), pos.getZ() + blockPosOffset.getZ());
            if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.SPEED_UPGRADE)) {
                this.speedCount++;
            }
            else if(world.getBlockState(calcPos).getBlock().equals(ModBlocks.QUANTITY_UPGRADE)) {
                this.quantityCount++;
            }
        }
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftProgress() {
        this.progress++;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return slot == SWORD_SLOT && direction == Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == SWORD_SLOT && direction == Direction.DOWN;
    }
}
