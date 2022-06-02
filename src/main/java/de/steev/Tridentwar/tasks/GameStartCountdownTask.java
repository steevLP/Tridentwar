package de.steev.Tridentwar.Tasks;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {
    private GameManager gameManager;
    public GameStartCountdownTask (GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 10;

    @Override
    public void run() {
        timeLeft--;
        if(timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }
        Bukkit.broadcastMessage(timeLeft + " seconds Until game starts!");
    }
}
