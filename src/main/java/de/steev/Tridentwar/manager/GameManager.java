package de.steev.Tridentwar.manager;

import de.steev.Tridentwar.Handlers.FileHandler;
import de.steev.Tridentwar.Tridentwar;
import de.steev.Tridentwar.tasks.GameStartCountdownTask;
import de.steev.Tridentwar.tasks.LobbyWaitingTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class GameManager {
    private final Tridentwar plugin;
    private TridentManager tridentManager;
    private GameStartCountdownTask gameStartCountdownTask;
    private PlayerManager playerManager;
    private MessageManager messageManager;
    private FileHandler fileHandler;
    private LobbyWaitingTask lobbyWaitingTask;
    private boolean isWaiting = false;
    private Effect record;
    private ScoreBoardManager scoreBoardManager;
    public GameState gameState = GameState.LOBBY;

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
                isWaiting = false;
                this.playerManager.setGameModes(GameMode.SURVIVAL);
                this.playerManager.setPlayersHealth(20F);
                this.playerManager.setAlive(Bukkit.getOnlinePlayers().size());
                this.playerManager.giveKits();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    this.playerManager.setKills(p, 0);
                    this.scoreBoardManager.updateScoreBoard(p, this.getPlayerManager().getAlive(), this.playerManager.getKills(p));
                }
                break;

            case STARTING:
                if(Bukkit.getOnlinePlayers().size() < this.plugin.config.getInt("minplayers")) {
                    // Message about minimal player count not beeing reached
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("error.not-enough-players"), null, null)));
                    this.setGameState(GameState.LOBBY);
                    return;
                }
                this.lobbyWaitingTask = null;
                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0 , 20);
                if(this.plugin.config.getLocation("arena") == null) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("error.location-not-found"), null, null)));
                } else {
                    playerManager.teleportPlayers(this.plugin.config.getLocation("arena"));
                }
                break;

            case WAITING:
                if(!isWaiting) {
                    lobbyWaitingTask = new LobbyWaitingTask(this);
                    lobbyWaitingTask.runTaskTimer(plugin, 0, 20);
                    isWaiting = true;
                } else {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',plugin.languageDataConfig.getString("error.is-already-waiting")));
                }
                break;

            case LOBBY:
                playerManager.setGameModes(GameMode.SURVIVAL);
                if(this.plugin.config.getLocation("arena") == null) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("error.location-not-found"), null, null)));
                } else {
                    playerManager.teleportPlayers(this.plugin.config.getLocation("lobby"));
                }
                break;

            case WON:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if(p.getGameMode() == GameMode.SURVIVAL) {
                        this.messageManager.broadCastTitle(ChatColor.translateAlternateColorCodes('&',this.plugin.languageDataConfig.getString("titles.won.top")),
                                ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("titles.won.bottom"), p, null)));
                    }
                }
                tridentManager.clearTasks();
                if(this.plugin.config.getLocation("lobby") == null) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("error.location-not-found"), null, null)));
                } else {
                    playerManager.teleportPlayers(this.plugin.config.getLocation("lobby"));
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        setGameState(GameState.STOPPING);
                    }
                }.runTaskLater(plugin, 120);
                break;

            case STOPPING:
                if(isWaiting) isWaiting = false;
                this.messageManager.broadCastTitle(ChatColor.translateAlternateColorCodes('&',this.plugin.languageDataConfig.getString("titles.stopping.top")),
                        ChatColor.translateAlternateColorCodes('&', this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("titles.stopping.bottom"), null, "Hub")));
                this.playerManager.removeKits();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Move players to Hub
                        Bukkit.getOnlinePlayers().forEach(player -> playerManager.moveFromServer(plugin.config.getString("lobby-server"), player));

                        if (plugin.config.getBoolean("autorestart")) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "restart");
                                }
                            }.runTaskLater(plugin, 120);
                        }
                    }
                }.runTaskLater(plugin, 100);


                Bukkit.getOnlinePlayers().forEach(player -> this.scoreBoardManager.removeScoreBoard(player));

                this.setGameState(GameState.LOBBY);
                break;

            case ABORTING:
                if(isWaiting) isWaiting = false;
                this.messageManager.broadCastTitle(ChatColor.translateAlternateColorCodes('&',this.plugin.languageDataConfig.getString("titles.aborted.top")),
                        ChatColor.translateAlternateColorCodes('&',this.fileHandler.replaceVars(this.plugin.languageDataConfig.getString("titles.aborted.bottom"), null, "Hub")));
                this.playerManager.removeKits();
                tridentManager.clearTasks();

                // Move players to Hub
                Bukkit.getOnlinePlayers().forEach(player -> playerManager.moveFromServer(this.plugin.config.getString("lobby-server"), player));

                if (plugin.config.getBoolean("autorestart")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "restart");
                        }
                    }.runTaskLater(plugin, 120);
                }

                Bukkit.getOnlinePlayers().forEach(player -> this.scoreBoardManager.removeScoreBoard(player));
                this.setGameState(GameState.LOBBY);
                break;

        }
    }

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
