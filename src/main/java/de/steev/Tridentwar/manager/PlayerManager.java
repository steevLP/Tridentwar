package de.steev.Tridentwar.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {
    private GameManager gameManager;
    /** The amount of players yet alife */
    private int alive = 0;
    /**
     * Handles all player related functions
     * @param gameManager The Local Gamemanager instance
     */
    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    /** Give a kit to all Players in Survivalmode */
    public void giveKits() { Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(this::giveKit); }
    /** Removes a kit from all Players in Survivalmode */
    public void removeKits() { Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(this::giveKit); }
    /** Gives a specified kit in this case hardcoded to specified players */
    public void giveKit(Player player){ player.getInventory().addItem(new ItemStack(Material.TRIDENT)); }
    /** Revmoes a specified kit in this case hardcoded from a specified player */
    public void removeKit(Player player){ player.getInventory().removeItem(new ItemStack(Material.TRIDENT)); }
    /**
     * sets all players online to a given gamemode
     * @param gamemode the gamemode all players will be set on
     */
    public void setGameModes(GameMode gamemode){
        for (Player p : Bukkit.getOnlinePlayers()) {
            setGameMode(p, gamemode);
        }
    }
    /**
     * sets a specified player on a given gamemode
     * @param player the player to change gamemode of
     * @param gamemode the gamemode wanted to be changed to
     */
    public void setGameMode(Player player, GameMode gamemode) { player.setGameMode(gamemode); }
    /**
     * teleports a player to a location
     * @param player the wanted player
     * @param loc the wanted location
     */
    public void teleportPlayer(Player player, Location loc) { player.teleport(loc); }
    /**
     * teleports all player to a given location
     * @param loc
     */
    public void teleportPlayers(Location loc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            teleportPlayer(p, loc);
        }
    }
    /** Handles Player death */
    public void playerDeath(){
        System.out.println("Before death: " + this.alive);
        this.alive--;
        System.out.println("After death: " + this.alive);
        if(this.alive == 1) {
            System.out.println("game ends with winner");
            this.gameManager.setGameState(GameState.WON);
        } else if(this.alive == 0) {
            System.out.println("game gets aborted");
            this.gameManager.setGameState(GameState.ABORTING);
        }
    }
    /**
     * Gets the amout of alive players
     * @return Integert amout of alive players
     */
    public int getAlive() { return alive; }
    /**
     * sets the yet alive players
     * @param alive amount of yet alive players
     */
    public void setAlive(int alive) { this.alive = alive; }
}
