package dev.frankovich.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import dev.frankovich.HMUtils;
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
            HMUtils.chat((Entity) sender, "Error running command. Usage: ");
            return false;
        }

        UUID id = Bukkit.getPlayer(name).getUniqueId();
        HeartData.updatePlayerEntry(id, Integer.parseInt(heartValue));
        HeartData.updateGameHealth(Bukkit.getPlayer(name));

        HMUtils.chat((Entity) sender, "Successfully updated &l" + name + "&r's hearts to " + heartValue + ".");
        
        return true;
    }
}
