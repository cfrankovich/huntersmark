package dev.frankovich.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.frankovich.HeartData;

public class HeartsCommand implements CommandExecutor 
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("only players can send commands");
            return false;
        }

        String name, heartValue;
        try
        {
            name = args[0];
            heartValue = args[1];
        } catch (IndexOutOfBoundsException e)
        {
            sender.sendMessage("Error running command. Please provide a player name and heart value.");
            return false;
        }

        String UUID = Bukkit.getPlayer(name).getUniqueId().toString();
        HeartData.updatePlayerEntry(UUID, Integer.parseInt(heartValue));
        
        return true;
    }
}
