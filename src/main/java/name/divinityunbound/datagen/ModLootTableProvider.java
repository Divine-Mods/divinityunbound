package name.divinityunbound.datagen;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CELESTITE_BLOCK);
        addDrop(ModBlocks.RAW_CELESTITE_BLOCK);
        addDrop(ModBlocks.CELESTITE_INFUSED_STONE);
        addDrop(ModBlocks.GOLDEN_CELESTITE_INFUSED_STONE);
        addDrop(ModBlocks.SAND_OF_TIME);

        addDrop(ModBlocks.CELESTITE_ORE, copperLikeOreDrops(ModBlocks.CELESTITE_ORE, ModItems.RAW_CELESTITE));
        addDrop(ModBlocks.DEEPSLATE_CELESTITE_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_CELESTITE_ORE, ModItems.RAW_CELESTITE));

        addDrop(ModBlocks.WILDERSUNG_LOG);
        addDrop(ModBlocks.WILDERSUNG_PLANKS);
        addDrop(ModBlocks.CELESTITE_INFUSED_WILDERSUNG_PLANKS);
        addDrop(ModBlocks.WILDERSUNG_WOOD);
        addDrop(ModBlocks.STRIPPED_WILDERSUNG_LOG);
        addDrop(ModBlocks.STRIPPED_WILDERSUNG_WOOD);
        addDrop(ModBlocks.WILDERSUNG_SAPLING);

        addDrop(ModBlocks.WILDERSUNG_LEAVES, leavesDrops(ModBlocks.WILDERSUNG_LEAVES, ModBlocks.WILDERSUNG_SAPLING, 0.005f));

        addDrop(ModBlocks.GENERATION_STATION);
        addDrop(ModBlocks.MYSTIC_CHRONOGRAPH);
        addDrop(ModBlocks.CHRONOS_TIME_ACCUMULATOR);
        addDrop(ModBlocks.DIVINE_REPLICATOR);
        addDrop(ModBlocks.SPEED_UPGRADE);
        addDrop(ModBlocks.QUANTITY_UPGRADE);
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop,
                (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                        ((LeafEntry.Builder) ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
