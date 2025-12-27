package ua.dager.adventcalendar.command;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import ua.dager.adventcalendar.AdventCalendar;

public class ModCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, commandRegistryAccess, registrationEnvironment) -> dispatcher.register(
                        Commands.literal("calendar")
                                .requires(Permissions.require(AdventCalendar.MOD_ID + ".open_gui", 4))
                                .executes(AdventCalendarMainCommand::run)
                )
        );
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, commandRegistryAccess, registrationEnvironment) -> dispatcher.register(
                        Commands.literal("calendar_reload")
                                .requires(Permissions.require(AdventCalendar.MOD_ID + ".command.reload_config", 4))
                                .executes(AdventCalendarReloadCommand::run)
                )
        );
    }
}
