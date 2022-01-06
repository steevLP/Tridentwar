package de.steev.Tridentwar.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageManager {
    private GameManager gameManager;

    /**
     * Handles all message related functions
     * @param gameManager the local Gamemanager instance
     */
    public MessageManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Broadcasts a title message to all players online
     * @param header the Big header of the title
     * @param bottom The lower small message of the title
     */
    public void broadCastTitle(String header, String bottom) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(header, bottom, 1, 100, 1);
        }
    }
}
