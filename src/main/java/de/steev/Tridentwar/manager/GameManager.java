package de.steev.Tridentwar.manager;

import de.steev.Tridentwar.Tridentwar;
import de.steev.Tridentwar.tasks.GameStartCountdownTask;
import org.bukkit.Bukkit;

public class GameManager {
    private final Tridentwar plugin;
    private TridentManager tridentManager;
    private GameStartCountdownTask gameStartCountdownTask;
    public GameState gameState = GameState.LOBBY;
    private PlayerManager playerManager;


    public GameManager(Tridentwar plugin) {
        this.plugin = plugin;
        this.tridentManager = new TridentManager(this);
        this.playerManager = new PlayerManager(this);
    }

    public void setGameState(GameState gameState){
        if(this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        this.gameState = gameState;

        switch (gameState){
            case ACTIVE:
                Bukkit.broadcastMessage("Active!");
                this.playerManager.giveKits();
                break;
            case STARTING:
                Bukkit.broadcastMessage("Starting!");
                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0 , 20);
                // teleport players
                // clear inventories
                break;
        }
    }

    public void cleanup(){}

    public TridentManager getTridentManager() { return tridentManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public GameState getGameState() { return gameState; }
}
