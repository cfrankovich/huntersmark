package dev.frankovich.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.frankovich.HMUtils;
import dev.frankovich.HeartData;

public class ScoreboardHandler implements Listener 
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer(); 
        presentScoreboard();
    }

    public static void presentScoreboard()
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("title", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "BMT SMP"); // deprecated but no other docs online?
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Map<String, Integer> playerHearts = new HashMap<String, Integer>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            String name = onlinePlayer.getName();
            int hearts = HeartData.getPlayerHearts(onlinePlayer.getUniqueId().toString());
            playerHearts.put(name, hearts);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(playerHearts.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() 
        {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) 
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        int size = list.size();

        for (Map.Entry<String, Integer> entry : list)
        {
            Score score = objective.getScore(ChatColor.GRAY + entry.getKey() + " " + ChatColor.RED + HMUtils.CHAT_HEART_SYMBOL + ChatColor.WHITE + entry.getValue());
            score.setScore(size);
            size--;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            onlinePlayer.setScoreboard(scoreboard);
        }
    }
}
