package advent_calendar.dager.gui;

import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GUIAdventCalendar extends SimpleGui {
    public int SIZE = 9 * 5;

    public GUIAdventCalendar(ServerPlayer player) {
        super(MenuType.GENERIC_9x5, player, false);
        this.setTitle(Component.literal("Advent Calendar"));
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            var element = this.getElement(i);
            if (element == null) {
                element = ItemStack.EMPTY;
            }
            var id = ResourceLocation.parse("minecraft:diamond");
            var item = BuiltInRegistries.ITEM.get(id);
            if (item.isEmpty()) {
                return;
            }
            var stack = new ItemStack(item.get());
            this.setSlot(
                i,
                new GuiElementBuilder(element)
                    .addLoreLine(Component.literal("Ya ebal"))
                    .setCallback((index, type1, action) -> {
                        this.player.getInventory().add(stack);
                    })
            );
        }
        this.open();
    }

    protected ItemStack getElement(int id) {
        if (!(id % 9 == 0 | id % 9 == 8)) {
            return null;
        }
        return Items.CYAN_STAINED_GLASS_PANE.getDefaultInstance();
    }
}
