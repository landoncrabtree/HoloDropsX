package dev.proflix.holodropsx.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public final class Glow {

    private Glow() {
        throw new UnsupportedOperationException();
    }

    private static final ArrayList<Team> teams = new ArrayList<>();
    private static final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

    public static void setGlowColor(@NotNull ChatColor color, @NotNull Entity entity) {
        String name = "HD" + color.getChar();
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
            teams.add(team);
        }
        team.setColor(color);
        team.addEntry(entity.getUniqueId().toString());
    }

    public static @Nullable ChatColor getColor(String string) {
        string = Strings.color(string);
        String color = ChatColor.getLastColors(string);
        if (color.length() == 0) color = ChatColor.COLOR_CHAR + "f";
        return ChatColor.getByChar(color.charAt(1));
    }

    public static void unregister() {
        for (Team team : teams) {
            team.unregister();
        }
    }

}
