package ua.dager.advent_calendar.gui;

import ua.dager.advent_calendar.AdventCalendar;
import ua.dager.advent_calendar.calendar.Calendar;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;

import java.io.IOException;

public class GuiAdventCalendar extends SimpleGui {
    public int SIZE = 9 * 5;

    public GuiAdventCalendar(ServerPlayer player) {
        super(MenuType.GENERIC_9x5, player, false);
        this.setTitle(Component.translatable("advent_calendar.title"));
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
        int day_id = id % 9 + 7 * (int) Math.floor((double) id / 9);

        var reward = Calendar.getDay(day_id, this.player);

        // set sides of GUI and missing days to glass
        if ((id % 9 == 0 || id % 9 == 8) | (reward == null)){
            return new GuiElementBuilder(Items.CYAN_STAINED_GLASS_PANE).setName(Component.empty());
        }

        var element = new GuiElementBuilder(Items.PLAYER_HEAD)
            .setName(Component.translatable(reward.name()))
            .setSkullOwner(reward.headTexture());

        if (reward.lore() != null) element.addLoreLine(Component.translatable(reward.lore()));
        if (reward.item() != null) {
            element.setCallback((index, type1, action) -> {
                try {
                    AdventCalendar.claimedGiftsRepo.addClaimedGift(player.getUUID(), day_id);
                    this.player.getInventory().add(reward.item());
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
