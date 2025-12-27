package ua.dager.adventcalendar.datagen;

import com.google.common.hash.HashCode;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import ua.dager.adventcalendar.AdventCalendar;
import ua.dager.adventcalendar.datagen.ui.UiResourceCreator;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CustomAssetsProvider implements DataProvider {
    private final PackOutput output;

    public CustomAssetsProvider(FabricDataOutput output) {
        this.output = output;
    }
    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        BiConsumer<String, byte[]> assetWriter = (path, data) -> {
            try {
                writer.writeIfNeeded(this.output.getOutputFolder().resolve(path), data, HashCode.fromBytes(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        return CompletableFuture.runAsync(() -> {
            try {
                UiResourceCreator.generateAssets(assetWriter);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return AdventCalendar.MOD_ID + ":assets";
    }
}
