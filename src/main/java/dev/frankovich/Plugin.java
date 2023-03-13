package dev.frankovich;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.frankovich.handlers.PlayerHandler;

public class Plugin extends JavaPlugin
{
    public void onEnable()
    {
        Bukkit.getLogger().info("[huntersmark] plugin enabled");

        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
    }

    public void onDisable()
    {
    }
}
