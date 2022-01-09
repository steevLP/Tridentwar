package de.steev.Tridentwar.tasks;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyWaitingTask extends BukkitRunnable {
    private GameManager gameManager;
    public LobbyWaitingTask(GameManager gameManager) {
        this.gameManager = gameManager;
        System.out.println("DEBUG: Lobby is Waiting");
    }

    private int timeLeft = 30;

    @Override
    public void run() {
        timeLeft--;
        this.gameManager.getMessageManager().broadCastXPBar(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft));
        if (timeLeft > 10) {
            switch (timeLeft) {
                case 30: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft)); break;
                case 25: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft)); break;
                case 20: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft)); break;
                case 15: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft)); break;
            }
        } else if(timeLeft <= 10) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("game.countdown") + timeLeft));

            if(timeLeft <= 0) {
                if(Bukkit.getOnlinePlayers().size() < this.gameManager.getPlugin().config.getInt("minplayers")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("error.not-enough-players"), null, null)));
                    this.gameManager.setGameState(GameState.ABORTING);
                } else {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                            this.gameManager.getFileHandler().replaceVars(this.gameManager.getPlugin().languageDataConfig.getString("game.starting"), null, null)));
                    gameManager.setGameState(GameState.STARTING);
                }
                cancel();
                return;
            }
        }
    }
}
