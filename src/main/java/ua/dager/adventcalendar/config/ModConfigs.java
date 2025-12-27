package ua.dager.adventcalendar.config;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;
import ua.dager.adventcalendar.AdventCalendar;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
                InputStream in;
                @Nullable ZipFile zip = null;
                if (Files.isDirectory(modJarPath)) {
                    // 💡 Dev environment — read directly from the expanded resources folder
                    var file = modJarPath
                        .getParent()
                        .getParent()
                        .getParent()
                        .resolve("resources/main")
                        .resolve("assets/advent-calendar/demo-configs/calendar_config.json");
                    in = Files.newInputStream(file);
                } else {
                    zip = new ZipFile(modJarPath.toFile());
                    var entry = zip.getEntry("assets/advent-calendar/demo-configs/calendar_config.json");
                    in = zip.getInputStream(entry);
                }
                AdventCalendar.configRepo = new JsonConfigRepository(
                    Paths.get("config/advent-calendar/calendar_config.json"),
                    in
                );
                if (zip != null) zip.close();
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
