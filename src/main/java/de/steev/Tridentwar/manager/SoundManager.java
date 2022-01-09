package de.steev.Tridentwar.manager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
    private GameManager gameManager;

    /**
     * Manages all Sound related functions
     * @param gameManager the local Gamemanager instance
     */
    public SoundManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Refactored version of Player#playsound
     * @param player The targeted player to play the sound
     * @param sound The Sound all Players get played
     * @param volume How loud the Sound will be
     * @param pitch How pitched the sound is
     */
    public void playSound (Player player, Sound sound, float volume, float pitch){ player.playSound(player.getLocation(), sound, volume, pitch); }

    /**
     * Plays defined sounds to all players online
     * @param sound The Sound all Players get played
     * @param volume How loud the Sound will be
     * @param pitch How pitched the sound is
     */
    public void broadCastSound (Sound sound, float volume, float pitch) { for(Player p : Bukkit.getOnlinePlayers()) playSound(p,sound, volume, pitch); }
}
