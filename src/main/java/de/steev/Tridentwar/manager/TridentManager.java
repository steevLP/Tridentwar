package de.steev.Tridentwar.manager;

import de.steev.Tridentwar.tasks.TridentResetTask;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TridentManager {
    /** collects all TridentResetTask bound to a player */
    private HashMap<Player, TridentResetTask> tasks = new HashMap<Player, TridentResetTask>();
    private GameManager gameManager;

    /**
     * Handles all trident related functions
     * @param gameManager the local Gamemanager instance
     */
    public TridentManager(GameManager gameManager) { this.gameManager = gameManager; }

    /**
     * Sets the trident reset task
     * @param thrower the throwing player
     * @param task the tridentreset tast to create
     */
    public void setTasks(Player thrower, TridentResetTask task) { this.tasks.put(thrower, task); }

    /**
     * the trident reset task of a given player
     * @param thrower the throwing player to get the task from
     * @return the TridentResetTask of given player
     */
    public TridentResetTask getTask (Player thrower) { return tasks.get(thrower); }

    /**
     * Removes a TridentResetTask of a given player
     * @param thrower the thrower of which the TridentResetTask should be removed from
     */
    public void removeTridentTast(Player thrower) { tasks.remove(thrower); }
}
