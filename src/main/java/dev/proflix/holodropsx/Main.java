package dev.proflix.holodropsx;

import dev.proflix.holodropsx.commands.Check;
import dev.proflix.holodropsx.commands.Reload;
import dev.proflix.holodropsx.listeners.ItemDropListener;
import dev.proflix.holodropsx.listeners.ItemFrameClickListener;
import dev.proflix.holodropsx.listeners.ItemMergeListener;
import dev.proflix.holodropsx.listeners.ItemPickupListener;
import dev.proflix.holodropsx.listeners.protection.BlockDropListener;
import dev.proflix.holodropsx.util.Glow;
import dev.proflix.holodropsx.util.Settings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main m;
    private static Settings settings;

    public static Main getInstance() {
        if (m == null) {
            try {
                m = new Main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    public static Settings getSettings() {
        return settings;
    }

    @Override
    public void onEnable() {
        m = this;
        saveDefaultConfig();
        settings = new Settings();
        settings.initialize();
        registerListeners();
        registerCommands();
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

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ItemDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemMergeListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemFrameClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickupListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockDropListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("hdxreload")).setExecutor(new Reload());
        Objects.requireNonNull(getCommand("hdxcheck")).setExecutor(new Check());
    }
}
