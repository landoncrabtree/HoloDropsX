package dev.proflix.holodropsx.listeners.protection;

import dev.proflix.holodropsx.Main;
import dev.proflix.holodropsx.util.Protection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockDropListener implements Listener {

    @EventHandler
    public void blockBreak(@NotNull BlockDropItemEvent e) {
        if (!Main.getSettings().protectionEnabled()) return;
        if (!Main.getSettings().isWorldEnabled(e.getPlayer().getWorld().getName())) return;
        if (!Main.getSettings().getBlockProtection()) return;
        List<Item> drops = e.getItems();
        Player p = e.getPlayer();
        for (Item item : drops) {
            Protection.dealWithProt(item, p);
        }
    }
}
