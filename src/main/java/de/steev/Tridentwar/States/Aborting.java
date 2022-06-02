package de.steev.Tridentwar.States;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Aborting {
    private final GameManager gameManager;

    public Aborting(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void run(){
        if(this.gameManager.isWaiting) this.gameManager.isWaiting = false;
        this.gameManager.getMessageManager().broadCastTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(this.gameManager.getPlugin().languageDataConfig.getString("titles.aborted.top"))),
                ChatColor.translateAlternateColorCodes('&',this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("titles.aborted.bottom"), null, "Hub")));
        this.gameManager.getPlayerManager().removeKits();
        this.gameManager.getTridentManager().clearTasks();

        // Move players to Hub
        Bukkit.getOnlinePlayers().forEach(player -> this.gameManager.getPlayerManager().moveFromServer(this.gameManager.getPlugin().config.getString("lobby-server"), player));

        if (this.gameManager.getPlugin().config.getBoolean("autorestart")) {
            new BukkitRunnable() {
                private GameManager gameManager = Aborting.this.gameManager;

                @Override
                public void run() {
                    Bukkit.getServer().dispatchCommand(this.gameManager.getPlugin().getServer().getConsoleSender(), "restart");
                }
            }.runTaskLater(this.gameManager.getPlugin(), 120);
        }

        Bukkit.getOnlinePlayers().forEach(player -> this.gameManager.getScoreBoardManager().removeScoreBoard(player));
        this.gameManager.setGameState(GameState.LOBBY);
    }
}
