package ua.dager.adventcalendar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class JsonClaimedGiftsRepository {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private final Path filePath;

  private Map<String, Set<Integer>> claimedGifts = new ConcurrentHashMap<>();

  public JsonClaimedGiftsRepository(Path filePath) {
    this.filePath = filePath;
    this.load();
  }

  public Set<Integer> getPlayerClaimedGifts(UUID player_uuid) {
    var res = this.claimedGifts.get(player_uuid.toString());

    if (res == null) return new CopyOnWriteArraySet<>();

    return res;
  }

  public synchronized void addClaimedGift(UUID player_uuid, Integer day) throws IOException {
    var claimed = this.claimedGifts.get(player_uuid.toString());
    if (claimed == null) claimed = new CopyOnWriteArraySet<>();
    claimed.add(day);
    this.claimedGifts.put(player_uuid.toString(), claimed);
    this.save();
  }

  public void load() {
    try {
      if (Files.exists(filePath)) {
        var content = Files.readString(filePath);
        var parsed = GSON.fromJson(content, new TypeToken<Map<String, Set<Integer>>>() {});
        if (parsed != null) {
          claimedGifts = new ConcurrentHashMap<>(parsed);
        } else {
          claimedGifts = new ConcurrentHashMap<>();
        }
      } else {
        Files.createDirectories(filePath.getParent());
        claimedGifts = new ConcurrentHashMap<>();
        save();
      }
    } catch (IOException | com.google.gson.JsonSyntaxException e) {
      System.err.println("Error loading claimed gifts: " + e.getMessage());
      claimedGifts = new ConcurrentHashMap<>();
      throw new RuntimeException("Failed to load JSON data at " + filePath, e);
    }
  }

  public synchronized void save() throws IOException {
    Path tempFile = filePath.resolveSibling(filePath.getFileName() + ".tmp");
    Files.writeString(tempFile, GSON.toJson(claimedGifts));
    Files.move(tempFile, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
  }
}
