package de.steev.Tridentwar;

import de.steev.Tridentwar.commands.TridentwarCommand;
import de.steev.Tridentwar.listeners.PlayerDeathListener;
import de.steev.Tridentwar.listeners.ProjectileHitListener;
import de.steev.Tridentwar.listeners.ProjectileLaunchListener;
import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Tridentwar extends JavaPlugin {
    private GameManager gameManager;
    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable(){
        super.onEnable();
        this.gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(this.gameManager, this), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this.gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this.gameManager), this);
        getCommand("tw").setExecutor(new TridentwarCommand(this.gameManager));
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        this.saveDefaultConfig();
        gameManager.cleanup();
    }
}
