package dev.frankovich.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.frankovich.HeartData;

public class PlayerHandler implements Listener 
{
    @EventHandler
    public void onRightClickHeart(PlayerInteractEvent e)
    {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (e.getHand() != EquipmentSlot.HAND) return;

        ItemMeta playerItemMeta = e.getItem().getItemMeta();
        List<String> itemMetaLore = playerItemMeta.getLore();

        if (itemMetaLore == null) return;

        final String loreMatch = "The heart of your enemies holds great power...";
        
        if (!itemMetaLore.get(0).equals(loreMatch)) return;

        // *sips tea ahhh java code 
        HeartData.updatePlayerEntry(e.getPlayer().getUniqueId(), 1);

        Location spawnLocation = e.getPlayer().getLocation();
        spawnLocation.setY(spawnLocation.getY() + 1);
        e.getPlayer().spawnParticle(Particle.ELECTRIC_SPARK, spawnLocation, 25);
        HeartData.updateGameHealth(e.getPlayer());

        e.getPlayer().getInventory().remove(e.getItem());
    }

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

        Bukkit.broadcastMessage("running da method");

        /* update the deceased player to one less heart */
        HeartData.updatePlayerEntry(deceasedPlayer.getUniqueId(), -1);

        /* drop a heart for the killer */
        killer.sendMessage("§7You have collected the heart of §o" + deceasedPlayer.getName() + "§r§7.");

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta(); 
        itemMeta.setDisplayName("§l" + deceasedPlayer.getName() + "§r's Heart");
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
