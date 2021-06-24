package dev.proflix.holodropsx.util;

import dev.proflix.holodropsx.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Strings {
    
    public static @NotNull String color(@NotNull String string) {
        return Color.colorizeAll(string);
    }

    public static @NotNull List<String> colorList(@NotNull List<String> list) { // color the glowlist
        for (int x = 0; x < list.size(); x++) {
            list.set(x, color(list.get(x)));
        }
        return list;
    }
    
    public static @Nullable String stripColor(String string) {
        return ChatColor.stripColor(string);
    }
    
    public static String makeName(@NotNull Item drop, int count, @NotNull String playerName, int time) {
        String formatted = !playerName.equals("") ? Main.getSettings().getProtFormat() + Main.getSettings().getFormat() : Main.getSettings().getFormat();
        String itemName;
        itemName = makeItemName(drop);
        if (Main.getSettings().isBlacklisted(itemName) || isUUID(itemName)) {
            itemName = "";
        }
        formatted = rePlaceholders(formatted, itemName, count, playerName, time);
        
        return itemName.length() == 0 ? itemName : formatted;
        
    }
    
    public static String makeItemName(@NotNull Item drop) {
        String itemName;
        
        ItemMeta meta = drop.getItemStack().getItemMeta();
        
        if (drop.getItemStack().getType() == Material.WRITTEN_BOOK) {
            assert meta != null;
            itemName = bookTitle((BookMeta)meta);
        }
        else {
            assert meta != null;
            if (meta.hasDisplayName() || Main.getSettings().getCustomNamesOnly()) {
                itemName = meta.getDisplayName();
            } else {
                itemName = Main.getSettings().getNameFromMat(drop.getItemStack().getType().toString());
            }
        }
        
        return itemName;
    }
    
    private static @NotNull String bookTitle(@NotNull BookMeta meta) {
        String title = meta.getTitle() == null ? " " : meta.getTitle();
        String itemName = ConfigReader.getString("item-names.WRITTEN_BOOK");
        return Objects.requireNonNull(itemName).replace("%title%", title);
    }
    
    private static String rePlaceholders(String formatted, @NotNull String item, int count, @NotNull String playerName, int time) {
        formatted = replaceAndFixSpacing(formatted, "%P%", Main.getSettings().getPrefix());
        formatted = replaceAndFixSpacing(formatted, "%I%", item);
        formatted = replaceAndFixSpacing(formatted, "%S%", Main.getSettings().getSuffix());
        formatted = replaceAndFixSpacing(formatted, "%PLAYER%", playerName);
        formatted = replaceAndFixSpacing(formatted, "%TIME%", time != 0 ? time + "" : "");
        // single stacks
        // count != 0 is for item frames (never display the stack count)
        if (count != 0 && count != 1 || Main.getSettings().getSingleStack()) {
            formatted = formatted.replace("%C%", Main.getSettings().getStackFormat().toLowerCase().replace("%amount%", "" + count));
        } else {
            formatted = replaceAndFixSpacing(formatted, "%C%", "");
            
        }
        return formatted;
    }
    
    private static @NotNull String replaceAndFixSpacing(@NotNull String string, @NotNull String replace, @NotNull String replacement) {
        // remove spaces that would have made sense if the placeholder was there
        // %PLAYER% %ITEM%, no player would make it " %ITEM%" or " DIRT"
        //                                           ^
        if (replacement.equals("")) {
            return string.replace(" " + replace + " ", "")
                    .replace(" " + replace, "")
                    .replace(replace + " ", "")
                    .replace(replace, "");
        }
        return string.replace(replace, replacement);
    }
    
    // count is supplied to make a call to rePlaceholders
    public static void makeItemFrameName(@NotNull ItemStack item, int count) {
        ItemMeta meta = item.getItemMeta();
        String formatted = Main.getSettings().getFormat().toUpperCase();
        String itemName;
    
        if (item.getType() == Material.WRITTEN_BOOK) {
            assert meta != null;
            itemName = bookTitle((BookMeta)meta);
        } else {
            itemName = Main.getSettings().getNameFromMat(item.getType().name());
        }
        formatted = rePlaceholders(formatted, itemName, count, "", 0);

        assert meta != null;
        meta.setDisplayName(itemName.length() == 0 ? itemName : formatted);
        item.setItemMeta(meta);
        
    }
    
    @SuppressWarnings("rawtypes")
    public static void addWatermark(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        @SuppressWarnings("unchecked") List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList();
        assert lore != null;
        lore.add("HoloDropsX");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    
    public static void removeWatermark(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (hasWatermark(item)) {
            assert meta != null;
            List<String> lore = meta.getLore();
            assert lore != null;
            lore.remove(lore.size() - 1);
            meta.setLore(lore);
            meta.setDisplayName("");
            item.setItemMeta(meta);
        }
    }
    
    public static boolean hasWatermark(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            // last line has watermark
            assert lore != null;
            return lore.get(lore.size() - 1).equals("HoloDrops");
        }
        return false;
    }
    
    public static boolean isUUID(String name) {
        return (stripColor(name).matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}"));
    }
    
    
}
