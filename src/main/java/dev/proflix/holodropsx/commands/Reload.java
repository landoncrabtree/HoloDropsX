package dev.proflix.holodropsx.commands;

import dev.proflix.holodropsx.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    private final String prefix = "" + ChatColor.DARK_RED + ChatColor.BOLD + "HoloDropsX ";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        reload(sender);
        return true;
    }

    private void reload(@NotNull CommandSender sender) {
        Main.getSettings().initialize();
        sender.sendMessage(prefix + ChatColor.RESET + ChatColor.GREEN + "Successfully reloaded the config");
    }

}
