package name.divinityunbound.block.entity;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.block.custom.KnowledgeExtractorBlock;
import name.divinityunbound.recipe.GenerationStationRecipe;
import name.divinityunbound.screen.GenerationStationScreenHandler;
import name.divinityunbound.screen.KnowledgeExtractorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class KnowledgeExtractorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private int speedCount = 0;
    private int quantityCount = 0;

    protected final PropertyDelegate propertyDelegate;
    private static final int MAX_EXPERIENCE = 9999999;
    private float experienceProgress = 0;
    private int totalExperience = 0;
    private int experienceLevel = 0;

    private int transferCooldown = -1;

    public PlayerEntity playerSteppingOn = null;
    public KnowledgeExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KNOWLEDGE_EXTRACTOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KnowledgeExtractorBlockEntity.this.experienceLevel;
                    case 1 -> KnowledgeExtractorBlockEntity.this.totalExperience;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> KnowledgeExtractorBlockEntity.this.experienceLevel = value;
                    case 1 -> KnowledgeExtractorBlockEntity.this.totalExperience = value;
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
        nbt.putInt("generation_station.experienceLevel", experienceLevel);
        nbt.putFloat("generation_station.experienceProgress", experienceProgress);
        nbt.putInt("generation_station.totalExperience", totalExperience);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        experienceLevel = nbt.getInt("generation_station.experienceLevel");
        experienceProgress = nbt.getFloat("generation_station.experienceProgress");
        totalExperience = nbt.getInt("generation_station.totalExperience");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Knowledge Extractor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KnowledgeExtractorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        --this.transferCooldown;
        if (!needsCooldown()) {
            setTransferCooldown(0);
            if (state.get(KnowledgeExtractorBlock.ENABLED).booleanValue()) {
                dispenseXP(world, pos, state);
            }
            else {
                List<ExperienceOrbEntity> xp = getExperienceInRange(world, pos);
                xp.forEach((orb) -> {
                    int amount = orb.getExperienceAmount();
                    this.addExperience(amount);
                    orb.discard();
                });
            }
        }
        if (playerSteppingOn != null && playerSteppingOn.getBlockPos().isWithinDistance(pos, 1.25)) {
            if (playerSteppingOn.experienceLevel > 0) {
                int playerXP = playerSteppingOn.totalExperience;
                //int maxDrainedXp = 4;//Math.min(4, playerXP);
                int maxDrainedXp = calculateLevelExperience(playerSteppingOn.experienceLevel)/4;

                int prevExp = this.totalExperience;
                this.addExperience(maxDrainedXp);
                playerSteppingOn.addExperience(-maxDrainedXp);
            }
            else {
                playerSteppingOn = null;
            }
        }
    }

    private List<ExperienceOrbEntity> getExperienceInRange(World world, BlockPos pos) {
        int range = 10 / 2;
        int posx = pos.getX();
        int posy = pos.getY();
        int posz = pos.getZ();
        Box box = new Box(posx - range, posy - range, posz - range,
                posx + range, posy + range, posz + range);
        List<ExperienceOrbEntity> xpEntities = world.getEntitiesByClass(ExperienceOrbEntity.class, box, e -> (
                !e.isPlayer()
        ));

        return xpEntities;
    }

    public void dispenseXP(World world, BlockPos pos, BlockState state) {
        if (world.isClient) {
            return;
        }
        if (!needsCooldown() && state.get(KnowledgeExtractorBlock.ENABLED).booleanValue()) {
            for (int i = 0; i < 2; i++) {
                int xpDispensed = Math.min(45, totalExperience);
                if (xpDispensed > 0) {
                    ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1.0, pos.getZ(), xpDispensed);
                    if (world.spawnEntity(xp)) {
                        addExperience(-xpDispensed);
                    }
                }
            }
            setTransferCooldown(4);
        }
    }

    private void setTransferCooldown(int transferCooldown) {
        this.transferCooldown = transferCooldown;
    }

    private boolean needsCooldown() {
        return this.transferCooldown > 0;
    }

    public void addExperience(int experience) {
        this.experienceProgress += (float)experience / (float)this.getNextLevelExperience();
        this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);
        while (this.experienceProgress < 0.0f) {
            float f = this.experienceProgress * (float)this.getNextLevelExperience();
            if (this.experienceLevel > 0) {
                this.addExperienceLevels(-1);
                this.experienceProgress = 1.0f + f / (float)this.getNextLevelExperience();
                continue;
            }
            this.addExperienceLevels(-1);
            this.experienceProgress = 0.0f;
        }
        while (this.experienceProgress >= 1.0f) {
            this.experienceProgress = (this.experienceProgress - 1.0f) * (float)this.getNextLevelExperience();
            this.addExperienceLevels(1);
            this.experienceProgress /= (float)this.getNextLevelExperience();
        }
    }

    public void addExperienceLevels(int levels) {
        this.experienceLevel += levels;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0f;
            this.totalExperience = 0;
        }
        if (levels > 0 && this.experienceLevel % 5 == 0) {
            float f = this.experienceLevel > 30 ? 1.0f : (float)this.experienceLevel / 30.0f;
        }
    }

    public int getNextLevelExperience() {
        if (this.experienceLevel >= 30) {
            return 112 + (this.experienceLevel - 30) * 9;
        }
        if (this.experienceLevel >= 15) {
            return 37 + (this.experienceLevel - 15) * 5;
        }
        return 7 + this.experienceLevel * 2;
    }

    public int calculateLevelExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        }
        if (level >= 15) {
            return 37 + (level - 15) * 5;
        }
        return 7 + level * 2;
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
