package name.divinityunbound;

import name.divinityunbound.block.ModBlocks;
import name.divinityunbound.item.ModItemGroups;
import name.divinityunbound.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivinityUnbound implements ModInitializer {
	public static final String MOD_ID = "divinityunbound";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}