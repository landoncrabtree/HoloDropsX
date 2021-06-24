package dev.proflix.holodropsx.util;

import dev.proflix.holodropsx.Main;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigReader {

    public static boolean getBoolean(@NotNull String option) {
        assert Main.getInstance() != null;
        return Main.getInstance().getConfig().getBoolean(option);
    }

    public static @Nullable String getString(@NotNull String option) {
        assert Main.getInstance() != null;
        return Main.getInstance().getConfig().getString(option);
    }

    public static @NotNull List<String> getStringList(@NotNull String option) {
        assert Main.getInstance() != null;
        return Main.getInstance().getConfig().getStringList(option);
    }

    public static int getInt(@NotNull String option) {
        assert Main.getInstance() != null;
        return Main.getInstance().getConfig().getInt(option);
    }

    public static @NotNull List<Material> getMissingItems() {
        ArrayList<Material> configMats = new ArrayList<>();
        assert Main.getInstance() != null;
        for (String configMaterial : Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("item-names")).getKeys(false)) {
            configMats.add(Material.getMaterial(configMaterial));
        }
        for (Material m : Material.values()) {
            if (!configMats.contains(m)) {
                configMats.add(m);
            } else {
                configMats.remove(m);
            }
        }
        return configMats;
    }

}
