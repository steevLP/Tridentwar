package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDeathListener implements Listener {
    private GameManager gameManager;

    public PlayerDeathListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(this.gameManager.gameState == GameState.ACTIVE) {
            this.gameManager.getPlayerManager().playerDeath();
            if(event.getEntity() instanceof Player) {
                Player dead = ((Player)event.getEntity());
                if(dead.getHealth() < 1) {
                    dead.getInventory().clear();
                    dead.setGameMode(GameMode.SPECTATOR);
                    // proper messaging
                    dead.sendTitle("Dead", "you Died", 1, 100, 1);
                    dead.playSound(dead.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5,1);
                    dead.sendMessage("Du bist gestorben");
                }
            }
        }
    }
}