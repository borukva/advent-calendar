package ua.dager.adventcalendar.calendar;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.network.chat.Component;
import ua.dager.adventcalendar.AdventCalendar;
import ua.dager.adventcalendar.util.HeadTextures;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

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

        var claimed = AdventCalendar.claimedGiftsRepo.getPlayerClaimedGifts(player.getUUID());

        if (claimed.contains(day)) {
            return new DayReward(
                HeadTextures.GIFT_CLAIMED,
                "advent_calendar.gift_claimed",
                null,
                reward.claimedLore() != null ? reward.claimedLore() : reward.lore()
            );
        }

        Runnable callback = () -> {
            var placeholder_context = PlaceholderContext.of(player);
            var dispatcher = Objects.requireNonNull(player.getServer()).getCommands().getDispatcher();
            var stack = player.getServer().createCommandSourceStack();
            for (var r : reward.rewards()) {
                var msg = Placeholders.parseText(Component.literal(r.command()), placeholder_context)
                    .getString()
                    .replaceFirst("/", "");
                try {
                    dispatcher.execute(msg, stack);
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        DayReward claimableGift = new DayReward(
            HeadTextures.GIFT_CLAIMABLE,
            "advent_calendar.gift_available",
            callback,
            reward.lore()
        );

        if (today.getDayOfMonth() > day) {
            if (AdventCalendar.configRepo.getAllowExpired()) {
                return claimableGift;
            }
            return new DayReward(
                HeadTextures.GIFT_UNAVAILABLE,
                "advent_calendar.gift_unavailable",
                null,
                reward.lore()
            );
        } else if (today.getDayOfMonth() == day) {
            return claimableGift;
        } else {
            return new DayReward(
                HeadTextures.GIFT_NOT_YET_AVAILABLE,
                "advent_calendar.gift_not_yet_available",
                null,
                reward.lore()
            );
        }
    }

    public record DayReward(String headTexture, String name, @Nullable Runnable callback, @Nullable String lore) {}
}
