package ua.dager.advent_calendar.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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
            if (Files.exists(filePath)) {
                var content = Files.readString(filePath);
                var parsed = GSON.fromJson(content, Config.class);
                if (parsed != null) config = parsed;
            } else {
                throw new RuntimeException("Config not found at " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON data at " + filePath, e);
        }
    }

    public record Config(boolean allowExpired, int year, int month, Map<String, Reward> rewards) {}
    public record Reward(String item, int count, @Nullable String lore) {}
}
