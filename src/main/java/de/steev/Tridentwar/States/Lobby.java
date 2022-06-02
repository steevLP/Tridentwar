package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;

public class Lobby {
    private final GameManager gameManager;

    public Lobby(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        this.gameManager.getPlayerManager().setGameModes(GameMode.SURVIVAL);
        if(this.gameManager.getPlugin().config.getLocation("arena") == null) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("error.location-not-found"), null, null)));
        } else {
            this.gameManager.getPlayerManager().teleportPlayers(this.gameManager.getPlugin().config.getLocation("lobby"));
        }
    }
}
