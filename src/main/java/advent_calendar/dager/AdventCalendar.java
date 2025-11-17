package advent_calendar.dager;

import advent_calendar.dager.command.AdventCalendarMainCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventCalendar implements ModInitializer {
	public static final String MOD_ID = "advent-calendar";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, commandRegistryAccess, registrationEnvironment) -> dispatcher.register(
            Commands.literal("calendar")
            .executes(AdventCalendarMainCommand::run))
        );
	}
}