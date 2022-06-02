package de.steev.Tridentwar.Listeners;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private GameManager gameManager;

    public EntityDamageListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(this.gameManager.gameState == GameState.ACTIVE) {
            if(event.getEntity() instanceof Player) {
                if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
                ) return;

                if((((Player) event.getEntity()).getHealth() - event.getDamage()) <= 0){
                    event.setCancelled(true);
                    ((Player)event.getEntity()).getInventory().clear();
                    ((Player)event.getEntity()).setGameMode(GameMode.SPECTATOR);

                    // proper messaging
                    ((Player)event.getEntity()).sendTitle( ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("titles.death.top")),
                            ChatColor.translateAlternateColorCodes('&',this.gameManager.getPlugin().languageDataConfig.getString("titles.death.bottom")), 1, 100, 1);
                    ((Player)event.getEntity()).playSound(((Player)event.getEntity()).getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 5,1);

                    // Tracks death inside of the plugin
                    this.gameManager.getPlayerManager().playerDeath();
                }
            }
        }
    }
}
