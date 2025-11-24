package ua.dager.adventcalendar.command;

import ua.dager.adventcalendar.gui.GuiAdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

public class AdventCalendarMainCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        GuiAdventCalendar gui = new GuiAdventCalendar(context.getSource().getPlayer());
        gui.open();
        return 0;
    }
}
