package dev.proflix.holodropsx.util;

import com.cryptomorin.xseries.XMaterial;
import dev.proflix.holodropsx.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigReader {

    public static boolean getBoolean(@NotNull String option) {
        return Main.getInstance().getConfig().getBoolean(option);
    }

    public static @Nullable String getString(@NotNull String option) {
        return Main.getInstance().getConfig().getString(option);
    }

    public static @NotNull List<String> getStringList(@NotNull String option) {
        return Main.getInstance().getConfig().getStringList(option);
    }

    public static int getInt(@NotNull String option) {
        return Main.getInstance().getConfig().getInt(option);
    }

    public static ArrayList<XMaterial> getMissingItems() {
        ArrayList<XMaterial> configMats = new ArrayList<>();
        for (String configMaterial : Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("item-names")).getKeys(false)) {
            configMats.add(XMaterial.valueOf(configMaterial));
        }
        for (XMaterial m : XMaterial.values()) {
            if (!configMats.contains(m)) {
                configMats.add(m);
            } else {
                configMats.remove(m);
            }
        }
        return configMats;
    }

}
