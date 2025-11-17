package advent_calendar.dager.command;

import advent_calendar.dager.gui.GUIAdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

public class AdventCalendarMainCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        GUIAdventCalendar gui = new GUIAdventCalendar(context.getSource().getPlayer());
        gui.display();
        return 0;
    }
}
