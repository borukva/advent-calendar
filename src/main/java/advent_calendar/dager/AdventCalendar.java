package advent_calendar.dager;

import advent_calendar.dager.command.AdventCalendarMainCommand;
import advent_calendar.dager.util.JsonClaimedGiftsRepository;
import advent_calendar.dager.util.JsonConfigRepository;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class AdventCalendar implements ModInitializer {
	public static final String MOD_ID = "advent-calendar";
    public static JsonConfigRepository configRepo;
    public static JsonClaimedGiftsRepository claimedGiftsRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        configRepo = new JsonConfigRepository(Paths.get("advent-calendar/calendar_config.json"));
        claimedGiftsRepo = new JsonClaimedGiftsRepository(Paths.get("advent-calendar/claimed_gifts.json"));
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, commandRegistryAccess, registrationEnvironment) -> dispatcher.register(
            Commands.literal("calendar")
            .executes(AdventCalendarMainCommand::run))
        );
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            try {
                claimedGiftsRepo.save();
            } catch (IOException ignored) {}
        });
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
	}
}