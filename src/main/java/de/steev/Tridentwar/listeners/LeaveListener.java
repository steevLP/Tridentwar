package de.steev.Tridentwar.Listeners;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    private GameManager gameManager;

    public LeaveListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(this.gameManager.gameState == GameState.ACTIVE) {
            if(event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                this.gameManager.getPlayerManager().playerDeath();
            }
        }
    }
}
