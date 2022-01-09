package de.steev.Tridentwar;

import de.steev.Tridentwar.commands.TridentwarCommand;
import de.steev.Tridentwar.listeners.*;
import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;

public class Tridentwar extends JavaPlugin implements PluginMessageListener {
    private GameManager gameManager;
    public FileConfiguration config;
    public File languagedata;
    public FileConfiguration languageDataConfig;


    @Override
    public void onEnable(){
        super.onEnable();
        config = this.getConfig();
        this.gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(this.gameManager, this), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(this.gameManager), this);
        getCommand("tw").setExecutor(new TridentwarCommand(this.gameManager));

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        this.saveDefaultConfig();

        languagedata = new File(this.getDataFolder(), "langs/" + config.getString("language") + ".yml");
        languageDataConfig = YamlConfiguration.loadConfiguration(languagedata);
    }

    @Override
    public void onDisable(){
        super.onDisable();
        this.saveDefaultConfig();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        gameManager.cleanup();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("BungeeCord")) { return; }
    }
}
