package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {
    private GameManager gameManager;

    public PlayerDamageListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(this.gameManager.gameState == GameState.ACTIVE) {
            if(event.getEntity() instanceof Player) {
                if(event.getDamager() instanceof Trident) {
                    Player dead = ((Player)event.getEntity());
                    if((dead.getHealth() - event.getDamage()) <= 0) {
                        event.setCancelled(true);
                        dead.getInventory().clear();
                        dead.setGameMode(GameMode.SPECTATOR);

                        // proper messaging
                        dead.sendTitle( ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("titles.death.top")),
                                ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("titles.death.bottom")), 1, 100, 1);
                        dead.playSound(dead.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5,1);

                        // Confirmes Kill
                        ((Player)((Trident)event.getDamager()).getShooter()).playSound(((Player)((Trident)event.getDamager()).getShooter()).getLocation(), Sound.ENTITY_VILLAGER_YES, 10, 1);

                        this.gameManager.getPlayerManager().playerDeath();
                    }
                } else if (event.getDamager() instanceof Player) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
