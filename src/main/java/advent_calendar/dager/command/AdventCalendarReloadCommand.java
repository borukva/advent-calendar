package advent_calendar.dager.command;

import advent_calendar.dager.AdventCalendar;
import advent_calendar.dager.gui.GuiAdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;

public class AdventCalendarReloadCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        AdventCalendar.configRepo.load();
        try {
            AdventCalendar.claimedGiftsRepo.save();
            AdventCalendar.claimedGiftsRepo.load();
        } catch (IOException exception) {
            AdventCalendar.LOGGER.error(
                "Error during reloading config {}", String.valueOf(exception)
            );
        }
        return 0;
    }
}
