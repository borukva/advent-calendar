package ua.dager.adventcalendar.config;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import ua.dager.adventcalendar.AdventCalendar;

import java.io.IOException;
import java.nio.file.Paths;

public class ModConfigs {
    public static void register() {
        AdventCalendar.configRepo = new JsonConfigRepository(Paths.get("advent-calendar/calendar_config.json"));
        AdventCalendar.claimedGiftsRepo = new JsonClaimedGiftsRepository(
            Paths.get("advent-calendar/claimed_gifts.json")
        );

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            try {
                AdventCalendar.claimedGiftsRepo.save();
            } catch (IOException ignored) {}
        });
    }
}
