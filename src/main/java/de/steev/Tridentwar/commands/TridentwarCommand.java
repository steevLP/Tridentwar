package de.steev.Tridentwar.commands;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
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

        if(args.length >= 1) {
            switch (args[0].toLowerCase()){
                default:
                    commandSender.sendMessage("use args: start");
                    break;
                case "start":
                    System.out.println("start thing: " + args[0]);
                    gameManager.setGameState(GameState.STARTING);
                    break;
                /**
                 * dev command for returning running gamestate
                 * @deprecated this command will be removed once state management works properly
                 */
                case "getstate":
                    commandSender.sendMessage("Current State: " + gameManager.gameState);
                    break;
                case "setlocation":
                    System.out.println("loc thing" + args[1]);
                    if(args.length <= 2){
                        switch (args[1].toLowerCase()){
                            default:
                                commandSender.sendMessage("wrong or undefined location type");
                                break;
                            case "lobby":
                                try {
                                    this.gameManager.setLocation("lobby", ((Player)commandSender).getLocation());
                                    commandSender.sendMessage("lobby set");
                                }catch (Exception ex) {
                                    commandSender.sendMessage("Error while setting location");
                                }
                                break;
                            case "arena":
                                try {
                                    this.gameManager.setLocation("arena", ((Player)commandSender).getLocation());
                                    commandSender.sendMessage("lobby set");
                                }catch (Exception ex) {
                                    commandSender.sendMessage("Error while setting location");
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
