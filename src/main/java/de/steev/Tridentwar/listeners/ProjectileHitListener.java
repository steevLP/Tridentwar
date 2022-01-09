package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectileHitListener implements Listener {
    private GameManager gameManager;

    public ProjectileHitListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onTridentHit(ProjectileHitEvent event){
        if (this.gameManager.gameState == GameState.ACTIVE) {

            // Removes trident task when not needed
            this.gameManager.getTridentManager().removeTridentTask((Player) event.getEntity().getShooter());

            // Resets tridents to player
            event.getEntity().remove();
            ((Player) event.getEntity().getShooter()).getInventory().addItem(new ItemStack(Material.TRIDENT));

            // tracks if the hit target was a player
            if (event.getHitEntity() instanceof Player) {
                if(this.gameManager.getTridentManager().getTask((Player)event.getEntity().getShooter()) != null) {
                    ((Player) ((Trident)event.getEntity()).getShooter()).playSound(((Player) ((Trident)event.getEntity()).getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                }
            }
        }
    }
}
