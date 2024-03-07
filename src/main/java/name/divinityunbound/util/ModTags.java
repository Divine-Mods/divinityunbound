package name.divinityunbound.util;

import name.divinityunbound.DivinityUnbound;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MAGIC_ORE_DETECTOR_DETECTABLE_BLOCKS =
                createTag("magic_ore_detector_detectable_blocks");

        public static final TagKey<Block> PAXEL_MINABLE =
                createTag("paxel_minable_blocks");
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier((DivinityUnbound.MOD_ID), name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier((DivinityUnbound.MOD_ID), name));
        }
    }
}
