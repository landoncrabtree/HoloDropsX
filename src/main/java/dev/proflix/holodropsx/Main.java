package dev.proflix.holodropsx;

import dev.proflix.holodropsx.util.Glow;
import dev.proflix.holodropsx.util.Settings;
import dev.proflix.holodropsx.commands.Check;
import dev.proflix.holodropsx.commands.Reload;
import dev.proflix.holodropsx.listeners.ItemDropListener;
import dev.proflix.holodropsx.listeners.ItemFrameClickListener;
import dev.proflix.holodropsx.listeners.ItemMergeListener;
import dev.proflix.holodropsx.listeners.ItemPickupListener;
import dev.proflix.holodropsx.listeners.protection.BlockDropListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Main extends JavaPlugin {
    
    private static @Nullable Main m;
    private static Settings settings;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        m = this;
        settings = new Settings();
        settings.initialize();
        getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        getServer().getPluginManager().registerEvents(new ItemMergeListener(), this);
        getServer().getPluginManager().registerEvents(new ItemFrameClickListener(), this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        getServer().getPluginManager().registerEvents(new BlockDropListener(), this);
        Objects.requireNonNull(getCommand("hdxreload")).setExecutor(new Reload());
        Objects.requireNonNull(getCommand("hdxcheck")).setExecutor(new Check());
    
    }

    @Override
    public void onDisable() {
        m = null;
        try {
            Glow.unregister();
        } catch (NoClassDefFoundError error) {
            // this try/catch block is to prevent console spam
        }
        settings.fixNames();
    }

    public static @Nullable Main getInstance() {
        return m;
    }

    public static Settings getSettings() {
        return settings;
    }
}