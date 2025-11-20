package advent_calendar.dager.command;

import advent_calendar.dager.gui.GuiAdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

public class AdventCalendarMainCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        GuiAdventCalendar gui = new GuiAdventCalendar(context.getSource().getPlayer());
        gui.open();
        return 0;
    }
}
