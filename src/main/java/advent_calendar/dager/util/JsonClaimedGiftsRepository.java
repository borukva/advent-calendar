package advent_calendar.dager.util;

import com.google.common.collect.BiMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonClaimedGiftsRepository {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path filePath;

    private Map<String, List<Integer>> claimedGifts;

    public JsonClaimedGiftsRepository(Path filePath) {
        this.filePath = filePath;
        this.load();
    }

    public List<Integer> getPlayerClaimedGifts(UUID player_uuid) {
        var res = this.claimedGifts.get(player_uuid.toString());

        if (res == null) return new ArrayList<>();

        return res;
    }

    public void addClaimedGift(UUID player_uuid, Integer day) throws IOException {
        var claimed = this.claimedGifts.get(player_uuid.toString());
        if (claimed == null) claimed = new ArrayList<>();
        claimed.add(day);
        this.claimedGifts.put(player_uuid.toString(), claimed);
        this.save();
    }

    public void load() {
        try {
            if (Files.exists(filePath)) {
                var content = Files.readString(filePath);
                var parsed = GSON.fromJson(content, new TypeToken<Map<String, List<Integer>>>() {});
                if (parsed != null) claimedGifts = parsed;
            } else {
                Files.createDirectories(filePath.getParent());
                claimedGifts = new HashMap<>();
                save();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON data at " + filePath, e);
        }
    }

    public void save() throws IOException {
        Files.writeString(filePath, GSON.toJson(claimedGifts));
    }
}
