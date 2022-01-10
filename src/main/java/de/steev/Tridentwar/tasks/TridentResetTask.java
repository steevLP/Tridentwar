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
    private Player thrower;
    private Entity projectile;

    public TridentResetTask (GameManager gameManager, Player thrower, Entity projektile) {
        this.gameManager = gameManager;
        this.thrower = thrower;
        this.projectile = projektile;
    }

    private int timeLeft = 10;

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            projectile.remove();
            thrower.getInventory().addItem(new ItemStack(Material.TRIDENT));
        }
    }
}

