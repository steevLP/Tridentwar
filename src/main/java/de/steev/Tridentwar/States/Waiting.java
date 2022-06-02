package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Tasks.LobbyWaitingTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Waiting {
    private final GameManager gameManager;

    public Waiting(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        if(!this.gameManager.isWaiting) {
            this.gameManager.setLobbyWaitingTask(new LobbyWaitingTask(this.gameManager));
            this.gameManager.getLobbyWaitingTask().runTaskTimer(this.gameManager.getPlugin(), 0, 20);
            this.gameManager.isWaiting = true;
        } else {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("error.is-already-waiting")));
        }
    }
}
