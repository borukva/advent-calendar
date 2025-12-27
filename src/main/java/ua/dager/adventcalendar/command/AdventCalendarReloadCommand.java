package ua.dager.adventcalendar.command;

import ua.dager.adventcalendar.AdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;

public class AdventCalendarReloadCommand {
  public static int run(CommandContext<CommandSourceStack> context) {
    AdventCalendar.configRepo.load();
    AdventCalendar.claimedGiftsRepo.load();
    AdventCalendar.LOGGER.info("Configs reloaded successfully");
    //        try {
    //            AdventCalendar.claimedGiftsRepo.save();
    //            AdventCalendar.claimedGiftsRepo.load();
    //            AdventCalendar.LOGGER.info("Configs reloaded successfully");
    //        } catch (IOException exception) {
    //            AdventCalendar.LOGGER.error(
    //                "Error during reloading config {}", String.valueOf(exception)
    //            );
    //        }
    return 0;
  }
}
