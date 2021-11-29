package de.steev.Tridentwar.manager;

import de.steev.Tridentwar.tasks.TridentResetTask;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TridentManager {
    private HashMap<Player, TridentResetTask> tasks;
    private GameManager gameManager;

    public TridentManager(GameManager gameManager) { this.gameManager = gameManager; }
    public void setTasks(Player thrower, TridentResetTask task) { this.tasks.put(thrower, task); }
    public TridentResetTask getTask (Player thrower) { return tasks.get(thrower); }
    public void removeTridentTast(Player thrower) { tasks.remove(thrower); }
}
