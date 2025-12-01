package ua.dager.adventcalendar.datagen.ui;

import net.minecraft.network.chat.Component;

import java.util.function.Function;

import static ua.dager.adventcalendar.datagen.ui.UiResourceCreator.*;

public class GuiTextures {
    public static final Function<Component, Component> CALENDAR_MENU = background("calendar_menu");

    public static void register(){
    }
}
