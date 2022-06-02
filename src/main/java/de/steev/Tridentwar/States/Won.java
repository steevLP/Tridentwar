package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Won {
    private final GameManager gameManager;

    public Won(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.getGameMode() == GameMode.SURVIVAL) {
                this.gameManager.getMessageManager().broadCastTitle(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("titles.won.top")),
                        ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("titles.won.bottom"), p, null)));
            }
        }
        this.gameManager.getTridentManager().clearTasks();
        if(this.gameManager.getPlugin().config.getLocation("lobby") == null) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("error.location-not-found"), null, null)));
        } else {

            this.gameManager.getPlayerManager().teleportPlayers(this.gameManager.getPlugin().config.getLocation("lobby"));
        }
        new BukkitRunnable() {
            private GameManager gameManager = Won.this.gameManager;

            @Override
            public void run() {
                this.gameManager.setGameState(GameState.STOPPING);
            }
        }.runTaskLater(this.gameManager.getPlugin(), 120);
    }
}
