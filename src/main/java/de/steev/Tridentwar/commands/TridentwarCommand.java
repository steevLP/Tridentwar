package de.steev.Tridentwar.Commands;

import de.steev.Tridentwar.Manager.GameManager;
import de.steev.Tridentwar.Manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TridentwarCommand implements CommandExecutor {

    private GameManager gameManager;

    public TridentwarCommand(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player sender = Bukkit.getServer().getPlayer(commandSender.getName());
        // TODO: Replace placeholder messages with messages from language files
        if(args.length >= 1) {
            switch (args[0].toLowerCase()){
                case "getgamestate": commandSender.sendMessage("current gamestate" + this.gameManager.getGameState()); break;
                case "leave":
                    this.gameManager.getPlayerManager().moveFromServer(this.gameManager.getPlugin().config.getString("lobby-server"), sender);
                    break;
                case "spectate":
                    if(this.gameManager.gameState == GameState.ACTIVE) {
                        sender.setGameMode(GameMode.SPECTATOR);
                        this.gameManager.getPlayerManager().playerDeath();
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.gameManager.getPlugin().languageDataConfig.getString("error.no-game-running")));
                    }
                    break;
                case "start":
                    if(commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.start")) ||
                            commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.all"))) {
                        gameManager.setGameState(GameState.STARTING);
                    } else {
                        // Get permission error message from config
                        // Tell the user
                    }
                    break;
                case "stop":
                    if(commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.stop")) ||
                            commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.all"))) {
                        gameManager.setGameState(GameState.STOPPING);
                    } else {
                        // Get permission error message from config
                        // Tell the user
                    }
                    break;
                case "setlocation":
                    System.out.println("loc thing" + args[1]);
                    if(args.length <= 2){
                        switch (args[1].toLowerCase()){
                            case "lobby":
                                if(commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.setlocation.all")) ||
                                        commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.setlocation.lobby")) ||
                                        commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.all"))) {
                                    try {
                                        this.gameManager.setLocation("lobby", ((Player) commandSender).getLocation());
                                        commandSender.sendMessage("lobby set");
                                    } catch (Exception ex) {
                                        commandSender.sendMessage("Error while setting location");
                                    }
                                } else {

                                }
                                break;
                            case "arena":
                                if(commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.setlocation.all")) ||
                                        commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.setlocation.arena")) ||
                                        commandSender.hasPermission(this.gameManager.getPlugin().config.getString("admin.all"))) {
                                    try {
                                        this.gameManager.setLocation("arena", ((Player) commandSender).getLocation());
                                        commandSender.sendMessage("arena set");
                                    } catch (Exception ex) {
                                        commandSender.sendMessage("Error while setting location");
                                    }
                                } else {

                                }
                                break;
                        }
                    } else {
                        commandSender.sendMessage("Empty locations cannot be processed");
                    }
                    break;
            }
        } else {
            commandSender.sendMessage("Empty Commands are not allowed!");
        }
        return false;
    }
}
