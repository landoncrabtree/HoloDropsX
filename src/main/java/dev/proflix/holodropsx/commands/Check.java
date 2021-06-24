package dev.proflix.holodropsx.commands;

import com.cryptomorin.xseries.XMaterial;
import dev.proflix.holodropsx.util.ConfigReader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Check implements CommandExecutor {

    private final String prefix = "" + ChatColor.DARK_RED + ChatColor.BOLD + "HoloDropsX ";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        check(sender);
        return true;
    }

    private void check(@NotNull CommandSender sender) {
        ArrayList<XMaterial> mats = ConfigReader.getMissingItems();
        if (mats.size() > 0) {
            sender.sendMessage(prefix + ChatColor.RESET + ChatColor.RED + "Your config is missing:");
            for (XMaterial m : mats) {
                sender.sendMessage(m.toString());
            }
        } else {
            sender.sendMessage(prefix + ChatColor.RESET + ChatColor.GREEN + "Your config isn't missing anything");
        }
    }

}
