package dev.proflix.holodropsx.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public final class Color {

    private Color() {
        throw new UnsupportedOperationException();
    }

    public static String colorizeAll(String msg) {
        return colorizeStandardCodes(colorizeHexCodes(msg));
    }

    public static String colorizeHexCodes(String msg) {
        return colorizeHexCodes("&#", "", msg);
    }

    public static String colorizeHexCodes(String startTag, String endTag, String message) {
        if (!Bukkit.getVersion().contains("1.16") || !Bukkit.getVersion().contains("1.17")) return message;

        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public static String colorizeStandardCodes(String msg) {
        boolean hasMD5 = false;
        try{
            Class.forName("net.md_5.bungee.api.ChatColor");
            hasMD5 = true;
        }
        catch (ClassNotFoundException ignored){ }

        if (hasMD5)
            return ChatColor.translateAlternateColorCodes('&', msg);
        else
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }
}
