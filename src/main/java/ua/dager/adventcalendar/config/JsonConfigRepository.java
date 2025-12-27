package ua.dager.adventcalendar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class JsonConfigRepository {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path filePath;

    private Config config;

    public JsonConfigRepository(Path filePath, InputStream default_config) {
        this.filePath = filePath;
        this.createdDefaultConfig(default_config);
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

    public Integer getDaysOffset() {
        if (this.config.daysOffset == null) return 0;
        return this.config.daysOffset;
    }

    public @Nullable JsonConfigRepository.DayReward getReward(int day) {
        return this.config.rewards.get(Integer.toString(day));
    }

    private void createdDefaultConfig(InputStream file) {
        if (Files.exists(filePath)) return;
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create default config at " + filePath, e);
        }
    }

    public void load() {
        try {
            var content = Files.readString(filePath);
            var parsed = GSON.fromJson(content, Config.class);
            if (parsed != null) config = parsed;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON data at " + filePath, e);
        }
    }

    public record Config(boolean allowExpired, @Nullable Integer daysOffset, int year, int month, Map<String, DayReward> rewards) {}
    public record DayReward(
        ArrayList<Reward> rewards, @Nullable ArrayList<String> lore, @Nullable ArrayList<String> claimedLore
    ) {}
    public record Reward(RewardType type, String command) {}
    enum RewardType {command}
}
