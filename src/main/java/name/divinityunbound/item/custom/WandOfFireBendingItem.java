package name.divinityunbound.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WandOfFireBendingItem extends BucketItem {
    private final Fluid fluid;
    public WandOfFireBendingItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = BucketItem.raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos3;
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos2, direction, itemStack)) {
                return TypedActionResult.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos4 = blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
            if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
                this.onEmptied(user, world, itemStack, blockPos3);
                if (user instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
                }
                user.getStackInHand(hand).damage(1, user,
                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
                return TypedActionResult.success(itemStack);
            }
            return TypedActionResult.fail(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, player, entity, hand);
        if(placeFluid(player, player.getWorld(), entity.getBlockPos(), null)){
            player.getStackInHand(hand).damage(1, player,
                    playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        }
        return ActionResult.SUCCESS;
    }

    private void giveFireResistance(PlayerEntity player) {
        boolean hasPlayerEffect = player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE);
        player.addStatusEffect(new StatusEffectInstance(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,
                100, 0,
                false, false, true)));
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        FluidFillable fluidFillable;
        boolean bl2;
        Fluid fluid = this.fluid;
        if (!(fluid instanceof FlowableFluid)) {
            return false;
        }
        FlowableFluid flowableFluid = (FlowableFluid)fluid;
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        boolean bl = blockState.canBucketPlace(this.fluid);
        boolean bl3 = bl2 = blockState.isAir() || bl || block instanceof FluidFillable && (fluidFillable = (FluidFillable)((Object)block)).canFillWithFluid(player, world, pos, blockState, this.fluid);
        if (!bl2) {
            return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), null);
        }
//        if (!world.isClient && bl && !blockState.isLiquid()) {
//            world.breakBlock(pos, true);
//        }
        //BlockState fluidBlockState = ((FluidState) ((FluidState)flowableFluid.getFlowing().getDefaultState().with(Properties.LEVEL_1_8, 7)).with(Properties.FALLING, true)).getBlockState();
        BlockState fluidBlockState =  flowableFluid.getFlowing(6, false).getBlockState();
        giveFireResistance(player);
        if (world.setBlockState(pos, fluidBlockState, Block.NOTIFY_ALL_AND_REDRAW)) {
            fluidBlockState =  flowableFluid.getFlowing(4, false).getBlockState();
            if (world.getBlockState(pos.offset(Direction.NORTH)).isAir()) {
                world.setBlockState(pos.offset(Direction.NORTH), fluidBlockState, Block.NOTIFY_ALL_AND_REDRAW);
            }
            if (world.getBlockState(pos.offset(Direction.EAST)).isAir()) {
                world.setBlockState(pos.offset(Direction.EAST), fluidBlockState, Block.NOTIFY_ALL_AND_REDRAW);
            }
            if (world.getBlockState(pos.offset(Direction.SOUTH)).isAir()) {
                world.setBlockState(pos.offset(Direction.SOUTH), fluidBlockState, Block.NOTIFY_ALL_AND_REDRAW);
            }
            if (world.getBlockState(pos.offset(Direction.WEST)).isAir()) {
                world.setBlockState(pos.offset(Direction.WEST), fluidBlockState, Block.NOTIFY_ALL_AND_REDRAW);
            }
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_fire_bending"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
