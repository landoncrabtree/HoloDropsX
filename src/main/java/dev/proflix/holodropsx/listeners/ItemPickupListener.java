package dev.proflix.holodropsx.listeners;

import dev.proflix.holodropsx.Main;
import dev.proflix.holodropsx.util.Strings;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.jetbrains.annotations.NotNull;

public class ItemPickupListener implements Listener {

    @EventHandler
    public void itemPickup(@NotNull EntityPickupItemEvent e) {
        if (Main.getSettings().getProtectedDrops().containsKey(e.getItem())) {
            if (Main.getSettings().getProtectedDrops().get(e.getItem()) != e.getEntity()) {
                e.setCancelled(true);
                return;
            }
        }
        int stack = e.getRemaining();
        if (stack > 0) {
            Item drop = e.getItem();
            String name = Strings.makeName(drop, stack, "", 0);
            drop.setCustomName(name);
        }
    }

}
