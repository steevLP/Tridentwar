package de.steev.Tridentwar.Manager;

import de.steev.Tridentwar.Handlers.FileHandler;
import de.steev.Tridentwar.States.*;
import de.steev.Tridentwar.Tridentwar;
import de.steev.Tridentwar.Tasks.GameStartCountdownTask;
import de.steev.Tridentwar.Tasks.LobbyWaitingTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.Objects;

public class GameManager {
    private final Tridentwar plugin;
    private TridentManager tridentManager;
    private GameStartCountdownTask gameStartCountdownTask;
    private PlayerManager playerManager;
    private MessageManager messageManager;
    private FileHandler fileHandler;
    private LobbyWaitingTask lobbyWaitingTask;
    public static boolean isWaiting = false;
    private Effect record;
    private ScoreBoardManager scoreBoardManager;
    public GameState gameState = GameState.LOBBY;


    /**
     * Game States
     */
    private Active activeState;
    private Starting startingState;
    private Waiting waitingState;
    private Lobby lobbyState;
    private Won wonState;
    private Stopping stoppingState;
    private Aborting abortingState;

    /**
     * Handles the entire Game
     * @param plugin the local plugin insance
     */
    public GameManager(Tridentwar plugin) {
        this.plugin = plugin;
        this.tridentManager = new TridentManager(this);
        this.playerManager = new PlayerManager(this);
        this.messageManager = new MessageManager(this);
        this.fileHandler = new FileHandler(this);
        this.scoreBoardManager = new ScoreBoardManager(this);
        this.lobbyWaitingTask = null;

        // Initialize Game States
        this.activeState = new Active(this);
        this.startingState = new Starting(this);
        this.waitingState = new Waiting(this);
        this.lobbyState = new Lobby(this);
        this.wonState = new Won(this);
        this.stoppingState = new Stopping(this);
        this.abortingState = new Aborting(this);
    }

    /**
     * Sets the Gamestate and decied next gamestep
     * @param gameState Gamestate of the current game
     */
    public void setGameState(GameState gameState){
        if(this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        this.gameState = gameState;

        /**
         * Runs run method from state reference
         * Add new case and call run method to expand on this
         */
        switch (gameState){
            case ACTIVE -> this.activeState.run();
            case STARTING -> this.startingState.run();
            case WAITING -> this.waitingState.run();
            case LOBBY -> this.lobbyState.run();
            case WON -> this.wonState.run();
            case STOPPING -> this.stoppingState.run();
            case ABORTING -> this.abortingState.run();
        }
    }


    /**
     * Clean up Routine, called when the games closes
     */
    public void cleanup(){
    String worldName = "world";
        File playerFilesDir = new File(worldName + "/playerdata");
        if(playerFilesDir.isDirectory()){
            String[] playerDats = playerFilesDir.list();
            for (int i = 0; i < playerDats.length; i++) {
                File datFile = new File(playerFilesDir, playerDats[i]);
                datFile.delete();
            }
        }
    }

    /**
     * Returns the current lobby wayiting task
     * @return LobbywaitingTask instance
     */
    public LobbyWaitingTask getLobbyWaitingTask() { return lobbyWaitingTask; }

    /**
     * gives back the current messageManager instance
     * @return the messageManager
     */
    public MessageManager getMessageManager() { return messageManager; }

    /**
     * Assigns Game Countdown Task
     * @param gameStartCountdownTask Gamecountdown Task
     */
    public void setGameStartCountdownTask(GameStartCountdownTask gameStartCountdownTask) {
        this.gameStartCountdownTask = gameStartCountdownTask;
    }

    /**
     * returns game countdown task
     * @return Gamecountdown Task
     */
    public GameStartCountdownTask getGameStartCountdownTask() {
        return gameStartCountdownTask;
    }


    /**
     * sets the active lobbywaiting task
     * @param lobbyWaitingTask the current task for waiting in the lobby
     */
    public void setLobbyWaitingTask(LobbyWaitingTask lobbyWaitingTask) { this.lobbyWaitingTask = lobbyWaitingTask; }

    /**
     * returns filehandler of running plugin instance
     * @return the filehandler instance
     */
    public FileHandler getFileHandler() {  return fileHandler; }

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
        plugin.config.set(path, loc);
        plugin.saveConfig();
    }

    /**
     * gives back the active scoreboard manager
     * @return the scorboard manager
     */
    public ScoreBoardManager getScoreBoardManager() { return scoreBoardManager; }
}
