package dev.frankovich.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HeartsCommand implements CommandExecutor 
{
    // TODO: command that modifies the heart data (should only be for admins) 

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        return true;
    }
}
