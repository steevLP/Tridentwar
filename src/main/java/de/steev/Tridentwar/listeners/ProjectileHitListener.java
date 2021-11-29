package de.steev.Tridentwar.listeners;

import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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
        if(this.gameManager.getTridentManager().getTask((Player)event.getEntity().getShooter()) != null) {
            this.gameManager.getTridentManager().getTask((Player) event.getEntity().getShooter()).cancel();
            this.gameManager.getTridentManager().removeTridentTast((Player) event.getEntity().getShooter());
        }

        if(event.getHitEntity() instanceof Player) ((Player) event.getEntity().getShooter()).playSound(((Player) event.getEntity().getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 0.5f);

        event.getEntity().remove();
        ((Player) event.getEntity().getShooter()).getInventory().addItem(new ItemStack(Material.TRIDENT));
    }
}
