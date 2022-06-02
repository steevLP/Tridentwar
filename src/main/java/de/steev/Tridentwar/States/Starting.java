package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import de.steev.Tridentwar.Tasks.GameStartCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Starting {
    private final GameManager gameManager;

    public Starting(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        if(Bukkit.getOnlinePlayers().size() < this.gameManager.getPlugin().config.getInt("minplayers")) {
            // Message about minimal player count not beeing reached
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("error.not-enough-players"), null, null)));
            this.gameManager.setGameState(GameState.LOBBY);
            return;
        }
        this.gameManager.setLobbyWaitingTask(null);
        this.gameManager.setGameStartCountdownTask(new GameStartCountdownTask(this.gameManager));
        this.gameManager.getGameStartCountdownTask().runTaskTimer(this.gameManager.getPlugin(), 0 , 20);
        if(this.gameManager.getPlugin().config.getLocation("arena") == null) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("error.location-not-found"), null, null)));
        } else {
            this.gameManager.getPlayerManager().teleportPlayers(this.gameManager.getPlugin().config.getLocation("arena"));
        }
    }
}
