package chorusfabricated;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.ChorusFlowerBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChorusFabricated implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("chorusfabricated");
	public static final chorusfabricated.ChorusConfig CONFIG = chorusfabricated.ChorusConfig.createAndLoad();

	@Override
	public void onInitialize() {
		LOGGER.info("ChorusFabricated has been loaded");

		ServerTickEvents.END_SERVER_TICK.register(server -> DelayedTask.tickTasks());
	}
}