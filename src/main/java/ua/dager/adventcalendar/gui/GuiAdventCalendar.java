package ua.dager.adventcalendar.gui;

import ua.dager.adventcalendar.AdventCalendar;
import ua.dager.adventcalendar.calendar.Calendar;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;
import ua.dager.adventcalendar.datagen.ui.GuiTextures;
import ua.dager.adventcalendar.util.Utils;

import java.io.IOException;
import java.util.stream.Collectors;

public class GuiAdventCalendar extends SimpleGui {
    public int SIZE = 9 * 6;

    public GuiAdventCalendar(ServerPlayer player) {
        super(MenuType.GENERIC_9x6, player, false);
        this.setTitle(GuiTextures.CALENDAR_MENU.apply(Utils.formatDisplayName("")));
        this.update();
    }

    private void update() {
        for (int i = 0; i < SIZE; i++) {
            var element = this.getElement(i);
            if (element != null) {
                this.setSlot(i, element);
            }
        }
    }

    private GuiElementBuilder getElement(int id) {
        int day_id = (id % 9 + 7 * ((int) Math.floor((double) id / 9 - 1))) + AdventCalendar.configRepo.getDaysOffset();
        var reward = Calendar.getDay(day_id, this.player);

        if ((id % 9 == 0 || id % 9 == 8 || id < 9) || (reward == null))
            return null;

        var element = new GuiElementBuilder(Items.PLAYER_HEAD)
            .setName(reward.name())
            .setSkullOwner(reward.headTexture());

        if (reward.lore() != null)
            element.setLore(
                reward.lore().stream().map(Component::translatable).collect(Collectors.toUnmodifiableList())
            );
        if (reward.callback() != null) {
            element.setCallback((index, type1, action) -> {
                try {
                    AdventCalendar.claimedGiftsRepo.addClaimedGift(player.getUUID(), day_id);
                    reward.callback().run();
                } catch (IOException exception) {
                    AdventCalendar.LOGGER.error(
                        "Error during claiming reward for player {}", player.getUUID(), exception
                    );
                }
                this.update();
            });
        }

        return element;
    }
}
