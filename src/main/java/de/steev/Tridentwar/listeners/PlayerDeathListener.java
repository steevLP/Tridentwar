package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private GameManager gameManager;

    public PlayerDeathListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.gameManager.getPlayerManager().playerDeath();
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        event.getEntity().sendMessage("Du bist gestorben");
    }
}