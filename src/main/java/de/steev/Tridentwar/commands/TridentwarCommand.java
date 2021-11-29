package de.steev.Tridentwar.commands;

import de.steev.Tridentwar.manager.GameManager;
import de.steev.Tridentwar.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TridentwarCommand implements CommandExecutor {

    private GameManager gameManager;

    public TridentwarCommand(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args[0] == null) return false;

        switch (args[0].toLowerCase()){
            case "start":
                gameManager.setGameState(GameState.STARTING);
                break;
        }
        return false;
    }
}
