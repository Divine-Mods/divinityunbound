package name.divinityunbound.item;

import name.divinityunbound.DivinityUnbound;
import name.divinityunbound.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CELESTITE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DivinityUnbound.MOD_ID, "celestite"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.celestite"))
                    .icon(() -> new ItemStack(ModItems.CELESTITE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CELESTITE);
                        entries.add(ModItems.CELESTITE_NUGGET);
                        entries.add(ModItems.RAW_CELESTITE);

                        entries.add(ModBlocks.CELESTITE_BLOCK);
                        entries.add(ModBlocks.RAW_CELESTITE_BLOCK);

                        entries.add(ModBlocks.CELESTITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_CELESTITE_ORE);

                        entries.add(ModItems.MAGIC_CELESTITE_DETECTOR);

                        /* Tools */
                        entries.add(ModItems.CELESTITE_PICKAXE);
                        entries.add(ModItems.CELESTITE_AXE);
                        entries.add(ModItems.CELESTITE_SHOVEL);
                        entries.add(ModItems.CELESTITE_SWORD);
                        entries.add(ModItems.CELESTITE_HOE);

                        /* Armor */
                        entries.add(ModItems.CELESTITE_HELMET);
                        entries.add(ModItems.CELESTITE_CHESTPLATE);
                        entries.add(ModItems.CELESTITE_LEGGINGS);
                        entries.add(ModItems.CELESTITE_BOOTS);

                        /* Wood */
                        entries.add(ModBlocks.WILDERSUNG_LOG);
                        entries.add(ModBlocks.WILDERSUNG_WOOD);
                        entries.add(ModBlocks.STRIPPED_WILDERSUNG_LOG);
                        entries.add(ModBlocks.STRIPPED_WILDERSUNG_WOOD);
                        entries.add(ModBlocks.WILDERSUNG_PLANKS);
                        entries.add(ModBlocks.WILDERSUNG_LEAVES);
                        entries.add(ModBlocks.WILDERSUNG_SAPLING);

                    }).build());
    public static void registerItemGroups() {
        DivinityUnbound.LOGGER.info("Registering Item Groups for " + DivinityUnbound.MOD_ID);
    }
}
