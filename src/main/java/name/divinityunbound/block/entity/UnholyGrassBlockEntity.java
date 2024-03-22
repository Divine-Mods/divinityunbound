package name.divinityunbound.block.entity;

import com.mojang.authlib.GameProfile;
import name.divinityunbound.block.custom.UnholySilencerBlock;
import name.divinityunbound.item.ModItems;
import name.divinityunbound.networking.DivinityUnboundFakePlayer;
import name.divinityunbound.screen.UnholySilencerScreenHandler;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnholyGrassBlockEntity extends BlockEntity {
    private static final Stream<RegistryEntry.Reference<EntityType<?>>> entityTypeStream = Registries.ENTITY_TYPE
            .streamEntries();
    private static final List<RegistryEntry.Reference<EntityType<?>>> entityTypes = entityTypeStream
            .filter(entityType -> entityType.value().getSpawnGroup() == SpawnGroup.MONSTER
            && entityType.value() != EntityType.WITHER
            && entityType.value() != EntityType.ENDER_DRAGON
            && entityType.value() != EntityType.WARDEN
            && entityType.value() != EntityType.RAVAGER
            && entityType.value() != EntityType.GUARDIAN
            && entityType.value() != EntityType.ELDER_GUARDIAN
            && entityType.value() != EntityType.GIANT
            && entityType.value() != EntityType.SHULKER
            && entityType.value() != EntityType.GHAST
            && entityType.value() != EntityType.VEX
            && entityType.value() != EntityType.HOGLIN
            && entityType.value() != EntityType.ZOGLIN
            && entityType.value() != EntityType.EVOKER
            && entityType.value() != EntityType.PHANTOM)
            .toList();

    public UnholyGrassBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UNHOLY_GRASS_BLOCK_ENTITY, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
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
        Random rand = Random.create();

        if(world.getLightLevel(pos.up()) < 8) {
            RegistryEntry.Reference<EntityType<?>> type = entityTypes.get(rand.nextInt(entityTypes.size()));
            Entity entity = type.value().create(world);

            if (entity != null) {
                entity.refreshPositionAndAngles(pos.getX() + 0.5,
                        pos.getY() + 1, pos.getZ() + 0.5,
                        world.getRandom().nextFloat() * 360.0F, 0.0F);
                boolean spawned = world.spawnEntity(entity);
            }
        }
        //PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4, false);

    }

    private List<LivingEntity> getLivingEntitiesInRange(World world, BlockPos pos) {
        int range = 6 / 2;
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
}
