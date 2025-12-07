package ua.dager.adventcalendar.util;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.resources.ResourceLocation;

public class ModPlaceholders {
    public static void register() {
        Placeholders.register(
            ResourceLocation.parse("player"),
            (ctx, arg) -> {
                if (ctx.player() == null)
                    return PlaceholderResult.invalid("No player!");
                return PlaceholderResult.value(ctx.player().getScoreboardName());
            }
        );
    }
}
