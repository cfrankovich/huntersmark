package dev.frankovich.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.frankovich.HMUtils;
import dev.frankovich.HeartData;

public class PlayerHandler implements Listener 
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        HeartData.createNewPlayerEntry(e.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onPlayerAssassination(EntityDeathEvent e)
    {
        /* dont do anything if the dead entity isnt a player */
        if (e.getEntityType() != EntityType.PLAYER) return;

        /* even if the dead entity is a player, dont do anything if they weren't killed by a player */
        final LivingEntity deceasedPlayer = e.getEntity(); 
        final Player killer = deceasedPlayer.getKiller(); 
        if (killer == null) return;

        /* update the deceased player to one less heart */
        HeartData.updatePlayerEntry(deceasedPlayer.getUniqueId().toString(), -1);

        /* drop a heart for the killer */
        HMUtils.chat((Entity)killer, "You have collected the heart of &l" + deceasedPlayer.getName() + "&r.");

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta(); 
        itemMeta.setDisplayName("&l" + deceasedPlayer.getName() + "&r's Heart");
        List<String> itemLore = new ArrayList<String>();
        itemLore.add("The heart of your enemies holds great power...");
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        Location dropLocation = killer.getLocation();
        dropLocation.setY(dropLocation.getY() + 1.0); 

        killer.spawnParticle(Particle.ELECTRIC_SPARK, dropLocation, 20);
        killer.getWorld().dropItem(dropLocation, item);
    } 
}
