package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class WandOfTeleportationItem extends Item {
    public WandOfTeleportationItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
            super.useOnBlock(context);
            PlayerEntity player = context.getPlayer();
            BlockPos pos = context.getBlockPos();
            Direction facing = context.getSide();
            World worldIn = context.getWorld();
            ItemStack stack = context.getStack();
            Hand hand = context.getHand();
        if (!worldIn.isClient) {
            if (player.isSneaking()) {
                stack.removeSubNbt("blockpos");
                NbtCompound nbt = new NbtCompound();
                nbt.putIntArray("blockpos", Arrays.asList(pos.getX(), pos.getY(), pos.getZ()));
                nbt.putString("dimension", worldIn.getDimensionKey().getValue().toString());
                stack.setNbt(nbt);
                player.sendMessage(Text.literal("Saved block position and dimension!"));
//            player.sendMessage(Text.literal("Saved block pos: " +
//                    pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
            } else {
                teleport(stack, player, worldIn, hand);
            }
        }
        return ActionResult.SUCCESS;
    }

    private void teleport(ItemStack stack, PlayerEntity player, World world, Hand hand) {
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("blockpos")) {
                int[] pos = stack.getNbt().getIntArray("blockpos");
                String dimension = stack.getNbt().getString("dimension");
                MinecraftServer minecraftServer = world.getServer();
                if (minecraftServer == null) return;

                if (!world.getDimensionKey().getValue().toString().equals(dimension)) {
                    RegistryKey<World> dimWorld = RegistryKey.of(RegistryKeys.WORLD, new Identifier(dimension.substring(9)));
                    //ServerWorld serverWorld2 = minecraftServer.getWorld(dimWorld);
                    RegistryKey<World> registryKey = dimension.equals("minecraft:overworld") ? World.OVERWORLD : (dimension.equals("minecraft:the_nether") ? World.NETHER : World.END);
                    ServerWorld serverWorld2 = ((ServerWorld)world).getServer().getWorld(registryKey);
                    if (serverWorld2 != null) {
                        if (registryKey.equals(World.NETHER)) {
                            player.setPortalCooldown(0);
                            player.setInNetherPortal(player.getBlockPos());
                            player = (PlayerEntity) ((ServerPlayerEntity)player).moveToWorld(serverWorld2);
                            player.setPortalCooldown(10);
                            //player.getWorld().getProfiler().pop();
                        }
                        else {
                            player = (PlayerEntity) ((ServerPlayerEntity)player).moveToWorld(serverWorld2);
                        }
                    }
                }
                player.teleport(pos[0] + 0.5, pos[1] + 1, pos[2] + 0.5, true);
                playUseSound(world, player.getBlockPos());
                player.getStackInHand(hand).damage(1, player,
                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if(player.isSneaking()) {
                stack.removeSubNbt("blockpos");
                stack.removeSubNbt("dimension");
                player.sendMessage(Text.literal("Cleared saved block position and dimension!"));
            }
            else {
                if (!player.getAbilities().creativeMode) {
                    player.getItemCooldownManager().set(this, 6000);
                }
                teleport(stack, player, world, hand);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains("blockpos") && stack.getNbt().contains("dimension");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_teleportation"));
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("blockpos")) {
                int[] pos = stack.getNbt().getIntArray("blockpos");
                tooltip.add(Text.literal("Saved Block Pos: " + pos[0] + ", " + pos[1] + ", " + pos[2]));
            }
            if (stack.getNbt().contains("dimension")) {
                String dimension = stack.getNbt().getString("dimension");
                tooltip.add(Text.literal("Dimension: " + dimension));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_PLAYER_TELEPORT,
                SoundCategory.BLOCKS, 1.0F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
