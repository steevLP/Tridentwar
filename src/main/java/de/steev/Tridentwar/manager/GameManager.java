package de.steev.Tridentwar.manager;

import de.steev.Tridentwar.Tridentwar;
import de.steev.Tridentwar.tasks.GameStartCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GameManager {
    private final Tridentwar plugin;
    private TridentManager tridentManager;
    private GameStartCountdownTask gameStartCountdownTask;
    public GameState gameState = GameState.LOBBY;
    private PlayerManager playerManager;
    private MessageManager messageManager;


    /**
     * Handles the entire Game
     * @param plugin the local plugin insance
     */
    public GameManager(Tridentwar plugin) {
        this.plugin = plugin;
        this.tridentManager = new TridentManager(this);
        this.playerManager = new PlayerManager(this);
        this.messageManager = new MessageManager(this);
    }

    /**
     * Sets the Gamestate and decied next gamestep
     * @param gameState Gamestate of the current game
     */
    public void setGameState(GameState gameState){
        if(this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        this.gameState = gameState;

        switch (gameState){
            case ACTIVE:
                Bukkit.broadcastMessage("Active!");
                this.playerManager.setAlive(Bukkit.getOnlinePlayers().size());
                this.playerManager.giveKits();
                break;
            case STARTING:
                if(Bukkit.getOnlinePlayers().size() < this.plugin.config.getInt("minplayers")) {
                    // Message about minimal player count not beeing reached
                    Bukkit.broadcastMessage("Game cannot be started with a single player");
                    return;
                }
                Bukkit.broadcastMessage("Starting!");
                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0 , 20);
                // teleport players
                playerManager.teleportPlayers(this.plugin.config.getLocation("arena"));
                break;
            case LOBBY:
                playerManager.teleportPlayers(this.plugin.config.getLocation("lobby"));
                break;
            case WON:
                Bukkit.broadcastMessage("Game has been won");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if(p.getGameMode() == GameMode.SURVIVAL) {
                        this.messageManager.broadCastTitle("Game ends", p.getDisplayName() + " has won the game");
                    }
                }
                playerManager.teleportPlayers(this.plugin.config.getLocation("lobby"));
                setGameState(GameState.STOPPING);
                break;
            case STOPPING:
                this.messageManager.broadCastTitle("Round stops", "You will be moved back to hub");
                this.playerManager.removeKits();
                // move players back to hub
                Bukkit.broadcastMessage("Stopping Game");
                setGameState(GameState.LOBBY);
                break;
            case ABORTING:
                this.messageManager.broadCastTitle("Round aborting", "You will be moved back to hub");
                this.playerManager.removeKits();
                // move players back to hub
                Bukkit.broadcastMessage("No Player Alive game aborts");
                setGameState(GameState.LOBBY);
                break;
        }
    }

    public void cleanup(){}

    /**
     * local trident manager
     * @retur tridentManager instance
     */
    public TridentManager getTridentManager() { return tridentManager; }

    /**
     * Local playermanager
     * @return playermanager instance
     */
    public PlayerManager getPlayerManager() { return playerManager; }

    /**
     * current gamestate
     * @return the current gamestate
     */
    public GameState getGameState() { return gameState; }

    /**
     * Returns plugin instance do not use if not needed!
     * @return the plugin
     */
    public Tridentwar getPlugin() { return plugin; }

    /**
     * Sets a specified location
     * @param path the path inside the config
     * @param loc the location to set
     */
    public void setLocation(String path, Location loc) {
        System.out.println("Location: " + loc);
        plugin.config.set(path, loc);
        plugin.saveConfig();
    }
}
