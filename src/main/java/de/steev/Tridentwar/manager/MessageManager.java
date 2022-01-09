package de.steev.Tridentwar.manager;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;

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
     * sends a player a message to the actionbar
     * @param p the player
     * @param message the message to send
     */
    public void setXPBar(Player p, String message){
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    /**
     * Broadcasts messages to the Actionbar
     * @param message the message to broadcast
     */
    public void broadCastXPBar(String message){
        for(Player p : Bukkit.getOnlinePlayers()) {
            setXPBar(p, message);
        }
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
