package dev.frankovich;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;

public class HeartData 
{
    private static final String HEARTDATAPATH = "./plugins/huntersmark/playerdata.txt";
    private static final String NOHEARTSBANPATH = "./plugins/huntersmark/playerheartbans.txt";
    private static Plugin plugin;

    public HeartData(Plugin plugin)
    {
        HeartData.plugin = plugin;

        try
        {
            File heartDataFile = new File(HEARTDATAPATH);
            File banFile = new File(NOHEARTSBANPATH);
            heartDataFile.createNewFile();
            banFile.createNewFile();
        } catch (IOException e)
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#HeartData()");
        }
    }

    public static void append(String str)
    {
        try
        {
            FileWriter fWriter = new FileWriter(HEARTDATAPATH, true);
            fWriter.append(str);
            fWriter.close();
        } catch (IOException e)
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#appendToHeart()");
        }
    }

    private static boolean hasEntry(String UUID)
    {
        try
        {
            FileReader fReader = new FileReader(HEARTDATAPATH);
            BufferedReader bReader = new BufferedReader(fReader);

            String line;
            while ((line = bReader.readLine()) != null) 
            {
                if (line.split(" ")[0].equals(UUID))
                {
                    return true;
                }    
            }
            bReader.close();
            fReader.close();
        } catch (IOException e) 
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#hasEntry()");
        } 

        return false;
    }

    public static void createNewPlayerEntry(String UUID)
    {
        if (hasEntry(UUID)) return;
        String defaultPlayerHearts = plugin.getConfig().getString("startinghearts");
        append(UUID + " " + defaultPlayerHearts + "\n");
    }

    public static int getPlayerHearts(String UUID)
    {
        try
        {
            FileReader fReader = new FileReader(HEARTDATAPATH);
            BufferedReader bReader = new BufferedReader(fReader);

            String line;
            while ((line = bReader.readLine()) != null) 
            {
                if (line.split(" ")[0].equals(UUID))
                {
                    bReader.close();
                    fReader.close();
                    return Integer.parseInt(line.split(" ")[1]);
                }    
            }
            bReader.close();
            fReader.close();
        } catch (IOException e) 
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#getPlayerHearts()");
        }

        Bukkit.getLogger().info("[huntersmark] UUID " + UUID + " was not found in the heart data file @ HeartData#getPlayerHearts()");
        return -1;
    } 

    public static void updatePlayerEntry(String UUID, int heartsToAdd)
    {
        // TODO: ban player if this value is 0
        String newHearts;
        if (heartsToAdd < 0)
        {
            newHearts = Integer.toString(getPlayerHearts(UUID) + heartsToAdd); 
        }
        else
        {
            newHearts = Integer.toString(heartsToAdd);
        }
        String builderString = "";

        try
        {
            FileReader fReader = new FileReader(HEARTDATAPATH);
            BufferedReader bReader = new BufferedReader(fReader);

            String line;
            while ((line = bReader.readLine()) != null) 
            {
                if (line.split(" ")[0].equals(UUID))
                {
                    builderString += (UUID + " " + newHearts + "\n");
                }    
                else
                {
                    builderString += line;
                }
            }
            bReader.close();
            fReader.close();
        } catch (IOException e)
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#updatePlayerEntry() [1]");
        }

        try
        {
            FileWriter fWriter = new FileWriter(HEARTDATAPATH);
            fWriter.write(builderString);
            fWriter.close();
        } catch (IOException e)
        {
            Bukkit.getLogger().info("[huntersmark] Exception in HeartData#updatePlayerEntry() [2]");
        }
    }
}
