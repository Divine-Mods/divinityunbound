package name.divinityunbound.fluid;

import name.divinityunbound.DivinityUnbound;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final FlowableFluid STILL_SPACE_TIME = Registry.register(Registries.FLUID,
            new Identifier(DivinityUnbound.MOD_ID, "space_time"), new SpaceTimeFluid.Still());
    public static final FlowableFluid FLOWING_SPACE_TIME = Registry.register(Registries.FLUID,
            new Identifier(DivinityUnbound.MOD_ID, "flowing_space_time"), new SpaceTimeFluid.Flowing());

    public static final Block SPACE_TIME_BLOCK = Registry.register(Registries.BLOCK, new Identifier(DivinityUnbound.MOD_ID,
            "space_time_block"), new FluidBlock(ModFluids.STILL_SPACE_TIME, FabricBlockSettings.copyOf(Blocks.WATER)
            .replaceable().liquid()));
    public static final Item SPACE_TIME_BUCKET = Registry.register(Registries.ITEM, new Identifier(DivinityUnbound.MOD_ID,
            "space_time_bucket"), new BucketItem(ModFluids.STILL_SPACE_TIME,
            new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static void registerFluids() {
        DivinityUnbound.LOGGER.info("Registering Fluid for " + DivinityUnbound.MOD_ID);
    }
}
