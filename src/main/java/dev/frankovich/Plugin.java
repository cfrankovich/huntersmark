package dev.frankovich;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.frankovich.commands.DropCommand;
import dev.frankovich.commands.HeartsCommand;
import dev.frankovich.handlers.PlayerHandler;

public class Plugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        new HeartData(this);
        getCommand("drop").setExecutor(new DropCommand());
        getCommand("updatehearts").setExecutor(new HeartsCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);

        HMUtils.log("plugin enabled");
    }

    @Override
    public void onDisable()
    {
        HMUtils.log("plugin disabled");
    }
}
