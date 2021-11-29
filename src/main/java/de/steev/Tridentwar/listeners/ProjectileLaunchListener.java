package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.Tridentwar;
import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.tasks.TridentResetTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    private GameManager gameManager;
    private TridentResetTask tridentResetTask;
    private Tridentwar plugin;

    public ProjectileLaunchListener(GameManager gameManager, Tridentwar plugin){
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void OnProjectileLaunch(ProjectileLaunchEvent event){
        this.tridentResetTask = new TridentResetTask(this.gameManager, (Player)event.getEntity().getShooter());
        this.tridentResetTask.runTaskLater(plugin, 300);
        gameManager.getTridentManager().setTasks((Player)event.getEntity().getShooter(), this.tridentResetTask);
    }
}
