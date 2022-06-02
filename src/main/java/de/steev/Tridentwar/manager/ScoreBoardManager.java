package de.steev.Tridentwar.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardManager {
    private GameManager gameManager;
    private final ScoreboardManager manager;

    /**
     * Manages Scoreboards created by the plugin
     * @param gameManager the local gamemanager instance
     */
    public ScoreBoardManager(GameManager gameManager) { this.manager = Bukkit.getScoreboardManager(); }

    /**
     * Creates and sets a scoreboard for a player
     * @param player the player
     * @param alive how many players are alive
     * @param kills how many kills the player got
     */
    public void createScoreBoard(Player player, int alive, int kills) {
        // Create board instance
        Scoreboard board = manager.getNewScoreboard();

        // Create Board Objective
        Objective obj = board.registerNewObjective("Tridentwar", "dummy", "SLPNetwork");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Create Scores
        Score splitter = obj.getScore("=-=-=-=-=-=-=-=-=-=-=");
        splitter.setScore(5);
        Score playerName = obj.getScore("Player: " + player.getDisplayName());
        playerName.setScore(4);
        Score aliveScore = obj.getScore("Alive: " + alive);
        aliveScore.setScore(3);
        Score killsScore = obj.getScore("Kills: " + kills);
        killsScore.setScore(2);
        Score blankScore = obj.getScore("");
        blankScore.setScore(1);
        Score hostScore = obj.getScore("play.slpnetwork.de");
        hostScore.setScore(0);
    }

    /**
     * updates a players scoreboard or sets one
     * @param player the player
     * @param alive how many players are alive
     * @param kills how many kills the player got
     * TODO: add time
     */
    public void updateScoreBoard(Player player, int alive, int kills){
        // Grab Scoreboard from Player
        Scoreboard board = player.getScoreboard();

        // Detect if a Scoreboard has been created
        if(board == null) {
            // If there is none, create one
            createScoreBoard(player, alive, kills);
        } else {
            // Fetch objective from existing scoreboard
            Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

            // Replace Scores present on the Screen
            Score splitter = obj.getScore("=-=-=-=-=-=-=-=-=-=-=");
            splitter.setScore(5);
            Score playerName = obj.getScore("Player: " + player.getDisplayName());
            playerName.setScore(4);
            Score aliveScore = obj.getScore("Alive: " + alive);
            aliveScore.setScore(3);
            Score killsScore = obj.getScore("Kills: " + kills);
            killsScore.setScore(2);
            Score blankScore = obj.getScore("");
            blankScore.setScore(1);
            Score hostScore = obj.getScore("play.slpnetwork.de");
            hostScore.setScore(0);
        }
    }

    /**
     * clears a player specific scoreboard
     * @param player the player
     */
    public void removeScoreBoard(Player player) {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
}
