package de.steev.Tridentwar.Listeners;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private GameManager gameManager;

    public JoinListener(GameManager gameManager) { this.gameManager = gameManager; }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        this.gameManager.getScoreBoardManager().createScoreBoard(event.getPlayer(), 0, 0);
        if(this.gameManager.gameState == GameState.ACTIVE) {
            this.gameManager.getPlayerManager().setGameMode(event.getPlayer(), GameMode.SPECTATOR);
            this.gameManager.getPlayerManager().teleportPlayer(event.getPlayer(), this.gameManager.getPlugin().config.getLocation("arena"));
            event.getPlayer().playEffect(event.getPlayer().getLocation(), Effect.RECORD_PLAY, Material.MUSIC_DISC_STAL);
        } else if(this.gameManager.gameState == GameState.LOBBY && this.gameManager.getPlugin().config.getBoolean("lobby-auto-queue")) {
            if(this.gameManager.getPlugin().config.getBoolean("force-on-spawn")) {
                this.gameManager.getPlayerManager().setGameMode(event.getPlayer(), GameMode.SURVIVAL);
                this.gameManager.getPlayerManager().setPlayerHealth(event.getPlayer(), 20);
                this.gameManager.getPlayerManager().teleportPlayer(event.getPlayer(), this.gameManager.getPlugin().config.getLocation("lobby"));
            }
            this.gameManager.setGameState(GameState.WAITING);
        } else if (this.gameManager.gameState == GameState.WAITING) {
            if(this.gameManager.getPlugin().config.getBoolean("force-on-spawn")) {
                this.gameManager.getPlayerManager().setGameMode(event.getPlayer(), GameMode.SURVIVAL);
                this.gameManager.getPlayerManager().setPlayerHealth(event.getPlayer(), 20);
                this.gameManager.getPlayerManager().teleportPlayer(event.getPlayer(), this.gameManager.getPlugin().config.getLocation("lobby"));
            }
        }
    }
}
