package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Active {
    private final GameManager gameManager;

    public Active(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        this.gameManager.isWaiting = false;
        this.gameManager.getPlayerManager().setGameModes(GameMode.SURVIVAL);
        this.gameManager.getPlayerManager().setPlayersHealth(20F);
        this.gameManager.getPlayerManager().setAlive(Bukkit.getOnlinePlayers().size());
        this.gameManager.getPlayerManager().giveKits();
        for (Player p : Bukkit.getOnlinePlayers()) {
            this.gameManager.getPlayerManager().setKills(p, 0);
            this.gameManager.getScoreBoardManager().updateScoreBoard(p, this.gameManager.getPlayerManager().getAlive(), this.gameManager.getPlayerManager().getKills(p));
        }
    }
}
