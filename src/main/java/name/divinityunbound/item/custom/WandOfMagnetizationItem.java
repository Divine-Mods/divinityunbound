package name.divinityunbound.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class WandOfMagnetizationItem extends Item {
    public WandOfMagnetizationItem(Settings settings) {
        super(settings);
    }

    private static final int DEFAULT_RANGE = 10;
    private boolean isEnabled = false;
    private int cooldown = 0;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if(player.isSneaking()) {
                stack.removeSubNbt("isEnabled");
                NbtCompound nbt = new NbtCompound();
                this.isEnabled = !this.isEnabled;
                nbt.putBoolean("isEnabled", this.isEnabled);
                stack.setNbt(nbt);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public boolean getIsEnabled(ItemStack stack) {
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("isEnabled")) {
                this.isEnabled = stack.getNbt().getBoolean("isEnabled");
            }
        }
        return this.isEnabled;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(!world.isClient()) {
            if (entity instanceof PlayerEntity && getIsEnabled(stack)) {
                if (cooldown > 6) {
                    cooldown = 0;
                    BlockPos pos = entity.getBlockPos();
                    int range = (DEFAULT_RANGE) / 2;
                    int posx = pos.getX();
                    int posy = pos.getY();
                    int posz = pos.getZ();
                    Box box = new Box(posx - range, posy - range, posz - range,
                            posx + range, posy + range, posz + range);
                    List<ItemEntity> itemEntities = world.getEntitiesByClass(ItemEntity.class, box, e -> (
                            true
                    ));

                    for (ItemEntity itemEntity : itemEntities) {
                        itemEntity.onPlayerCollision((PlayerEntity) entity);
//                        Vec3d vec = new Vec3d(
//                                posx - itemEntity.getX(),
//                                posy - itemEntity.getY(),
//                                posz - itemEntity.getZ()
//                        );
//                        itemEntity.move(MovementType.SELF, vec);
                    }
                } else {
                    cooldown++;
                }
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.isEnabled;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal(this.isEnabled ? "Enabled" : "Disabled"));
        tooltip.add(Text.translatable("tooltip.divinityunbound.wand_of_magnetization"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_PLAYER_TELEPORT,
                SoundCategory.BLOCKS, 1.0F,
                (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }
}
