package ua.dager.adventcalendar.config;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import ua.dager.adventcalendar.AdventCalendar;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

public class ModConfigs {
    public static void register() {
        AdventCalendar.claimedGiftsRepo = new JsonClaimedGiftsRepository(
            Paths.get("config/advent-calendar/claimed_gifts.json")
        );

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            var container = FabricLoader.getInstance().getModContainer(AdventCalendar.MOD_ID).orElseThrow();
            try {
                var modJarPath = container.getOrigin().getPaths().getFirst();
                var zip = new ZipFile(modJarPath.toFile());
                var entry = zip.getEntry("assets/advent-calendar/demo-configs/calendar_config.json");
                var in = zip.getInputStream(entry);
                AdventCalendar.configRepo = new JsonConfigRepository(
                    Paths.get("config/advent-calendar/calendar_config.json"),
                    in
                );
                zip.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            try {
                AdventCalendar.claimedGiftsRepo.save();
            } catch (IOException ignored) {}
        });
    }
}
