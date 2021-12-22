package de.steev.Tridentwar.tasks;

import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TridentResetTask extends BukkitRunnable {

    private GameManager gameManager;
    private Player thrower;
    private Entity projektile;

    public TridentResetTask (GameManager gameManager, Player thrower, Entity projektile) {
        this.gameManager = gameManager;
        this.thrower = thrower;
        this.projektile = projektile;
    }

    @Override
    public void run() {
        projektile.remove();
        thrower.getInventory().addItem(new ItemStack(Material.TRIDENT));
    }
}
