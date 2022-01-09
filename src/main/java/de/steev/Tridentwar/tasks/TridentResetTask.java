package de.steev.Tridentwar.tasks;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TridentResetTask extends BukkitRunnable {

    private GameManager gameManager;

    public TridentResetTask (GameManager gameManager, Player thrower, Entity projektile) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 30;

    @Override
    public void run() {
        timeLeft--;
        if(timeLeft >= 10) {
            switch (timeLeft) {
                case 30: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("game.countdown"))); break;
                case 25: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("game.countdown"))); break;
                case 20: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("game.countdown"))); break;
                case 15: Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("game.countdown"))); break;
            }
        } else {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("game.countdown")));

            if(Bukkit.getOnlinePlayers().size() < this.gameManager.getPlugin().config.getInt("minplayers")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("error.not-enough-players")));
            }

            if(timeLeft <= 0) {
                if(Bukkit.getOnlinePlayers().size() < this.gameManager.getPlugin().config.getInt("minplayers")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("error.not-enough-players")));
                    cancel();
                    this.gameManager.setGameState(GameState.ABORTING);
                } else {
                    cancel();
                    gameManager.setGameState(GameState.ACTIVE);
                    return;
                }
            }
        }
    }
}

