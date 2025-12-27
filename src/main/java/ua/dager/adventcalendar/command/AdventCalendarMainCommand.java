package ua.dager.adventcalendar.command;

import net.minecraft.server.players.NameAndId;
import ua.dager.adventcalendar.gui.GuiAdventCalendar;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

public class AdventCalendarMainCommand {
    public static int run(CommandContext<CommandSourceStack> context) {
        var player = context.getSource().getPlayer();
        if (player == null) return 0;
        // If player is a guest
        if (
            !context.getSource()
            .getServer()
            .getPlayerList()
            .getWhiteList()
            .isWhiteListed(new NameAndId(player.getGameProfile()))
        ) return 0;

        GuiAdventCalendar gui = new GuiAdventCalendar(player);
        gui.open();
        return 0;
    }
}
