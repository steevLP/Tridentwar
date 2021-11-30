package de.steev.Tridentwar.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {
    private GameManager gameManager;
    private int alive = 0;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void giveKits() { Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(this::giveKit); }
    public void giveKit(Player player){
        player.getInventory().addItem(new ItemStack(Material.TRIDENT));
    }

    public void playerDeath(){
        this.alive--;
        if(this.alive == 1) {
            this.gameManager.setGameState(GameState.WON);
        } else if(this.alive == 0) {
            this.gameManager.setGameState(GameState.ABORTING);
        }
    }

    public int getAlive() { return alive; }
    public void setAlive(int alive) { this.alive = alive; }
}
