package dev.proflix.holodropsx.util;

import dev.proflix.holodropsx.Main;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class Protection {

    private Protection() {
        throw new UnsupportedOperationException();
    }

    public static void dealWithProt(@NotNull Item item, @NotNull Player p) {
        if (Main.getSettings().getProtItemList().isEmpty() || Main.getSettings().getProtItemList().contains(item.getItemStack().getType().toString())) {
            Main.getSettings().getProtectedDrops().put(item, p);
            new BukkitRunnable() {
                int time = Main.getSettings().getProtTime();
                @NotNull String pName = p.getName();

                public void run() {
                    try {
                        if (time <= 0) {
                            pName = "";
                        }
                        item.setCustomName(Strings.makeName(item, item.getItemStack().getAmount(), pName, time));
                    } catch (Exception exc) {
                        this.cancel();
                    }
                    if (time <= 0) {
                        Main.getSettings().getProtectedDrops().remove(item);
                        this.cancel();
                    }
                    time--;
                }
            }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
        }
    }


}
