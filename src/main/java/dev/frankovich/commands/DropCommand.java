package dev.frankovich.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("only players can send commands");
            return false;
        }

        Player player = (Player)sender;
        World world = player.getWorld();

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item.getItemMeta(); 
        itemMeta.setDisplayName("Someone's Heart");
        List<String> itemLore = new ArrayList<String>();
        itemLore.add("The heart of your enemies holds great power...");
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        Location dropLocation = player.getLocation();
        dropLocation.setY(dropLocation.getY() + 1.0); 
        world.dropItem(dropLocation, item);
        player.spawnParticle(Particle.ELECTRIC_SPARK, dropLocation, 20);

        return false;
    }
    
}
