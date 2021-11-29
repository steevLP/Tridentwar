package de.steev.Tridentwar.tasks;

import de.steev.Tridentwar.manager.GameManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TridentResetTask extends BukkitRunnable {

    private GameManager gameManager;
    private Player thrower;

    public TridentResetTask (GameManager gameManager, Player thrower) {
        this.gameManager = gameManager;
        this.thrower = thrower;
    }

    @Override
    public void run() {
        thrower.getInventory().addItem(new ItemStack(Material.TRIDENT));
    }
}
