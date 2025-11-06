package pl.maksiuhrino.realcook;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maksiuhrino.realcook.block.ModBlocks;
import pl.maksiuhrino.realcook.block.entity.ModBlockEntities;
import pl.maksiuhrino.realcook.item.ModItemGroups;
import pl.maksiuhrino.realcook.item.ModItems;
import pl.maksiuhrino.realcook.util.ModSounds;
import pl.maksiuhrino.realcook.util.StuffTimer;

public class RealisticCooking implements ModInitializer {
	public static final String MOD_ID = "cook_real";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		StuffTimer.register();
		ModSounds.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModItemGroups.initialize();
		ModBlockEntities.initialize();
		LOGGER.info("Hello Fabric world!");
	}
}