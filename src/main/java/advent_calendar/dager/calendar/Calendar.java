package advent_calendar.dager.calendar;

import advent_calendar.dager.AdventCalendar;
import advent_calendar.dager.util.HeadTextures;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Calendar {
    public static final String TIMEZONE = "Europe/Kyiv";

    private static LocalDate getDate() {
        return ZonedDateTime.now(ZoneId.of(TIMEZONE)).toLocalDate();
    }

    public static @Nullable Calendar.DayReward getDay(Integer day, ServerPlayer player) {
        var today = getDate();

        if (today.getMonthValue() != AdventCalendar.configRepo.getMonth()) return null;
        if (today.getYear() != AdventCalendar.configRepo.getYear()) return null;

        var reward = AdventCalendar.configRepo.getReward(day);
        if (reward == null) return null;

        var item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(reward.item()));
        if (item.isEmpty()) return null;

        var claimed = AdventCalendar.claimedGiftsRepo.getPlayerClaimedGifts(player.getUUID());

        if (claimed.contains(day)) {
            return new DayReward(
                HeadTextures.GIFT_CLAIMED,
                "advent_calendar.gift_claimed",
                null,
                reward.lore()
            );
        }

        if (today.getDayOfMonth() > day) {
            if (AdventCalendar.configRepo.getAllowExpired()) {
                return new DayReward(
                    HeadTextures.GIFT_CLAIMABLE,
                    "advent_calendar.gift_available",
                    new ItemStack(item.get(), reward.count()),
                    reward.lore()
                );
            }
            return new DayReward(
                HeadTextures.GIFT_UNAVAILABLE,
                "advent_calendar.gift_unavailable",
                null,
                reward.lore()
            );
        } else if (today.getDayOfMonth() == day) {
            return new DayReward(
                HeadTextures.GIFT_CLAIMABLE,
                "advent_calendar.gift_available",
                new ItemStack(item.get(), reward.count()),
                reward.lore()
            );
        } else {
            return new DayReward(
                HeadTextures.GIFT_NOT_YET_AVAILABLE,
                "advent_calendar.gift_not_yet_available",
                null,
                reward.lore()
            );
        }
    }

    public record DayReward(String head_texture, String name, @Nullable ItemStack item, @Nullable String lore) {}
}
