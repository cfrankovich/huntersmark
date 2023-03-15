package dev.frankovich;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class HMUtils 
{
    public static final char CHAT_COLOR_SYMBOL = '§';

    public static void log(String s) { Bukkit.getLogger().info("[huntersmark] " + s); }

    public static void chat(Entity e, String s) { e.sendMessage(colorString("&7[&bh&3m&7]&f " + s)); }

    public static String colorString(String s) { return s.replace('&', CHAT_COLOR_SYMBOL); }
}
