package ua.dager.adventcalendar;

import net.minecraft.resources.ResourceLocation;
import ua.dager.adventcalendar.command.ModCommands;
import ua.dager.adventcalendar.config.JsonClaimedGiftsRepository;
import ua.dager.adventcalendar.config.JsonConfigRepository;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.dager.adventcalendar.config.ModConfigs;
import ua.dager.adventcalendar.datagen.ui.GuiTextures;
import ua.dager.adventcalendar.datagen.ui.UiResourceCreator;
import ua.dager.adventcalendar.util.ModPlaceholders;

public class AdventCalendar implements ModInitializer {
	public static final String MOD_ID = "advent-calendar";
    public static JsonConfigRepository configRepo;
    public static JsonClaimedGiftsRepository claimedGiftsRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        GuiTextures.register();
        UiResourceCreator.setup();

        ModConfigs.register();

        ModCommands.register();

        ModPlaceholders.register();

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
	}

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}