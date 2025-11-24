package ua.dager.adventcalendar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;
import ua.dager.adventcalendar.AdventCalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JsonConfigRepository {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path filePath;

    private Config config;

    public JsonConfigRepository(Path filePath) {
        this.filePath = filePath;
        this.load();
    }

    public int getYear() {
        return this.config.year;
    }

    public int getMonth() {
        return this.config.month;
    }

    public boolean getAllowExpired() {
        return this.config.allowExpired;
    }

    public @Nullable Reward getReward(int day) {
        return this.config.rewards.get(Integer.toString(day));
    }

    public void load() {
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                File zipPath = new File("polymer/resource_pack.zip");

                String target = "assets/advent-calendar/demo-configs/calendar_config.json";

                try (ZipFile zipFile = new ZipFile(zipPath)) {
                    ZipEntry entry = zipFile.getEntry(target);

                    if (entry == null) {
                        throw new FileNotFoundException("File not found in ZIP: " + target);
                    }

                    try (InputStream is = zipFile.getInputStream(entry)) {
                        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                        Files.writeString(filePath, content);
                    }

                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy calendar config from resource pack");
                }
            }

            var content = Files.readString(filePath);
            var parsed = GSON.fromJson(content, Config.class);
            if (parsed != null) config = parsed;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON data at " + filePath, e);
        }
    }

    public record Config(boolean allowExpired, int year, int month, Map<String, Reward> rewards) {}
    public record Reward(String item, int count, @Nullable String lore) {}
}
