package dev.proflix.holodropsx.listeners;

import dev.proflix.holodropsx.Main;
import dev.proflix.holodropsx.util.Glow;
import dev.proflix.holodropsx.util.Strings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ItemDropListener implements Listener {


    @EventHandler
    public void itemDrop(@NotNull ItemSpawnEvent e) {
        Item drop = e.getEntity();
        ItemStack item = drop.getItemStack();
        if (item.hasItemMeta()) {
            if (checkBlacklistLore(Objects.requireNonNull(item.getItemMeta()))) return;
            if (Strings.hasWatermark(item)) Strings.removeWatermark(item);
        }
        if (!Main.getSettings().getProtectedDrops().containsKey(drop)) {
            String name = Strings.makeName(drop, item.getAmount(), "", 0);
            drop.setCustomName(name);
        }
        drop.setCustomNameVisible(true);
        if (Main.getSettings().getItemGlow()) {
            String rawName = Strings.makeItemName(drop);
            if (Main.getSettings().isGlowlisted(rawName)) { // check the raw name
                drop.setGlowing(true);
                if (Main.getSettings().getGlowColor()) {
                    ChatColor color = Glow.getColor(rawName);
                    Glow.setGlowColor(Objects.requireNonNull(color), drop);
                }
            }
        }
    }

    private boolean checkBlacklistLore(@NotNull ItemMeta meta) {
        if (meta.hasLore()) {
            for (String s : Objects.requireNonNull(meta.getLore())) {
                if (s.contains("Display Item")) {
                    return true;
                }
            }
        }
        return false;
    }
}
