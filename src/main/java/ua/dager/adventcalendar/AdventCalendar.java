package ua.dager.adventcalendar;

import ua.dager.adventcalendar.command.ModCommands;
import ua.dager.adventcalendar.config.JsonClaimedGiftsRepository;
import ua.dager.adventcalendar.config.JsonConfigRepository;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.dager.adventcalendar.config.ModConfigs;

public class AdventCalendar implements ModInitializer {
	public static final String MOD_ID = "advent-calendar";
    public static JsonConfigRepository configRepo;
    public static JsonClaimedGiftsRepository claimedGiftsRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModConfigs.register();

        ModCommands.register();

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
	}
}