package de.steev.tridentwar;

import de.steev.tridentwar.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class main extends JavaPlugin implements Listener {

    public static boolean eventActive = false;
    public FileConfiguration config = this.getConfig();
    public static int alive = 0;

    // Custom Playerdata File
    public static File playerdata;
    public static FileConfiguration playerDataConfig;
    public static final String playerdatafilename = "game.yml";

    public void onEnable(){
        this.getLogger().info(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.YELLOW + " Plugin lädt");

        // Registers Events
        this.getLogger().info(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.YELLOW + " Registriere Events");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new onHit(this), this);
        this.getServer().getPluginManager().registerEvents(new itemDrop(this), this);
        this.getServer().getPluginManager().registerEvents(new onProjectileLaunch(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerConnectionEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new playerRespawn(this), this);


        // Registers Commands
        this.getLogger().info(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.YELLOW + " Lade Befehle");
        this.getCommand("tw").setExecutor(new command(this));

        /*
         * ====================================================
         * # Deletes old game.yml from previus plugin session #
         * ====================================================
         */
        // Writes a file with playername, uuid and playermods
        playerdata = new File(this.getDataFolder(), playerdatafilename);

        try{
            // Check for already existing file
            if(playerdata.exists()){
                // Delete the file if it does exist
                playerdata.delete();
                this.getLogger().info(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.YELLOW + " game.yml wird neu erstellt");
            }
        }catch (Exception e){
            this.getLogger().warning("Error while creating File\n===================================================\n" + e);
        }

        playerDataConfig = YamlConfiguration.loadConfiguration(playerdata);
        saveplayerdata();
        saveDefaultConfig();
    }

    public void onDisable(){
        this.getLogger().info(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.YELLOW + " Plugin fährt runter");
    }

    /*
     * ================================
     * #         File Handler         #
     * ================================
     */
    /** Handles Player data */
    public static void saveplayerdata(){
        try {
            playerDataConfig.save(playerdata);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to save " + playerdatafilename); // shouldn't really happen, but save throws the exception
        }
    }
}

