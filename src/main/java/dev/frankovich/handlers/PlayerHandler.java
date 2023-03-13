package dev.frankovich.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerHandler implements Listener 
{
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Bukkit.getLogger().info("[huntersmark] player just died lol");

        Player deceasedPlayer = e.getEntity();
        String deathMessage = e.getDeathMessage();

        /* commiting before i go to far */ 
    } 
}
