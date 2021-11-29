package de.steev.Tridentwar;

import de.steev.Tridentwar.commands.TridentwarCommand;
import de.steev.Tridentwar.listeners.ProjectileHitListener;
import de.steev.Tridentwar.listeners.ProjectileLaunchListener;
import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Tridentwar extends JavaPlugin {
    private GameManager gameManager;

    @Override
    public void onEnable(){
        super.onEnable();
        this.gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(this.gameManager, this), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitListener(this.gameManager), this);
        getCommand("tw").setExecutor(new TridentwarCommand(gameManager));
    }

    @Override
    public void onDisable(){
        super.onDisable();
        gameManager.cleanup();
    }
}
