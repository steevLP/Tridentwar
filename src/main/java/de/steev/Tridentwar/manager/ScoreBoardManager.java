package de.steev.Tridentwar.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardManager {
    private GameManager gameManager;
    private ScoreboardManager manager;

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
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("TridentWar","dummy", ChatColor.translateAlternateColorCodes('&',"&7&l<< &bTridentWar &7&l>>"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score splitter = objective.getScore("=-=-=-=-=-=-=-=-=-=-=");
        splitter.setScore(6);
        Score playername = objective.getScore(ChatColor.translateAlternateColorCodes('&',"&ePlayer: " + ChatColor.WHITE + ChatColor.BOLD + player.getDisplayName()));
        playername.setScore(5);
        Score empty2 = objective.getScore("");
        empty2.setScore(4);
        Score aliveScore = objective.getScore(ChatColor.translateAlternateColorCodes('&',"&eAlive: " + ChatColor.WHITE + ChatColor.BOLD + alive));
        aliveScore.setScore(3);
        Score killScore = objective.getScore(ChatColor.translateAlternateColorCodes('&',"&eKills: " + ChatColor.WHITE + ChatColor.BOLD + kills));
        killScore.setScore(2);
        Score empty = objective.getScore("");
        empty.setScore(1);
        Score address = objective.getScore("play.slpnetwork.de");
        address.setScore(0);
        player.setScoreboard(scoreboard);
    }

    /**
     * updates a players scoreboard or sets one
     * @param player the player
     * @param alive how many players are alive
     * @param kills how many kills the player got
     */
    public void updateScoreBoard(Player player, int alive, int kills){
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        createScoreBoard(player,alive,kills);
    }

    /**
     * clears a player specific scoreboard
     * @param player the player
     */
    public void removeScoreBoard(Player player) {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
}
