package de.steev.tridentwar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class command implements CommandExecutor {
    // TODO: add permissions to the commands
    private main plugin;
    public command(main plugin){ this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;

        if(args.length > 0){
            switch (args[0].toLowerCase()){
                default:
                    commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " Dieser Befehl ist nicht bekannt bitte gib /tw help ein");
                    break;
                case "help":
                    player.sendMessage("======================= " + ChatColor.AQUA + "Steev's Trident War" + " =======================\n" +
                            ChatColor.AQUA + ChatColor.BOLD + "help: " + ChatColor.RESET + "Listet dir eine Hilfe des plugins auf\n" +
                            ChatColor.AQUA + ChatColor.BOLD + "start: " + ChatColor.RESET + "Zum starten eines Trident Wars\n" +
                            ChatColor.AQUA + ChatColor.BOLD + "stop: " + ChatColor.RESET + "Zum stopen eines Trident Wars\n"+
                            ChatColor.AQUA + ChatColor.BOLD + "leave: " + ChatColor.RESET + "Verlässt den Trident War\n"+
                            ChatColor.AQUA + ChatColor.BOLD + "setSpawn: " + ChatColor.RESET + "Setzt den anfangspunkt für alle Spieler\n" +
                            ChatColor.AQUA + ChatColor.BOLD + "setSpectate: " + ChatColor.RESET + "Setzt den spawn für gestorbene Spieler\n" +
                            ChatColor.AQUA + ChatColor.BOLD + "setEndLocation: " + ChatColor.RESET + "Setzt den spawn für alle Spieler nach dem Trident War");
                    break;
                case "start":
                    if(commandSender.hasPermission("tw.admin")){
                        // checks if more then 1 person is online
                        if(Bukkit.getServer().getOnlinePlayers().size() >= 2){
                            // ensures that no war already is active
                            if(!plugin.eventActive){
                                plugin.eventActive = true; // Tells the plugin there is a war ongoing
                                for(Player p : Bukkit.getServer().getOnlinePlayers()){
                                    p.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " Ein Trident War beginnt!");
                                    p.sendTitle(ChatColor.GREEN + "Beginnt!", ChatColor.GOLD + "Der Trident War beginnt.", 1, 20, 1);

                                    // Teleports players to Spawn
                                    if(plugin.playerDataConfig.get("spectator") != null){
                                        p.teleport(((Location) plugin.playerDataConfig.get("spectator")));
                                    }
                                }
                                warcontroller.StartTridentWar(plugin);
                            } else {
                                commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " Ein Trident War ist bereits im Gange");
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + "Allein kanst du keinen Trident War starten");
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " dir fehlen die Rechte dazu");
                    }
                    break;
                case "stop":
                    if(commandSender.hasPermission("tw.admin")){
                        if(plugin.eventActive){
                            plugin.eventActive = false;
                            for(Player p : Bukkit.getOnlinePlayers()){
                                p.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " Der Trident War endet!");
                                p.sendTitle("Ende!", "Der Trident war Endet.", 1, 20, 1);
                            }
                            warcontroller.StopTridentWar(plugin);
                        } else {
                            commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " es ist kein Trident im Gange");
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " dir fehlen die Rechte dazu");
                    }
                    break;
                case "leave":
                    plugin.playerDataConfig.set(((Player) commandSender).getPlayer().getUniqueId() + "", null);
                    if(plugin.playerDataConfig.get("endLoc") != "unset"){
                        ((Player) commandSender).getPlayer().teleport(((Location)plugin.playerDataConfig.get("endLoc")));
                    }

                    // Check if enough players are in for a trident war
                    if(plugin.playerDataConfig.getKeys(false).size() < 2){
                        warcontroller.StopTridentWar(plugin);

                        // Message and teleport everyone if not
                        for(Player p : Bukkit.getServer().getOnlinePlayers()){
                            if(plugin.playerDataConfig.getString(p.getUniqueId() + ".name") != null){
                                p.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " Ein Trident War beginnt!");
                                p.sendTitle(ChatColor.RED + "Abgebrochen!", ChatColor.RED + "Zu wenig Spieler.", 1, 20, 1);

                                // Teleports players to Spawn
                                if(plugin.playerDataConfig.get("endLoc") != null){
                                    p.teleport(((Location) plugin.playerDataConfig.get("endLoc")));
                                }
                            }
                        }
                    }
                    commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + "Du hast den Trident war verlassen");
                    break;
                case "setspawn":
                    if(commandSender.hasPermission("tw.admin")){
                        if(args[1].toLowerCase() == "unset"){
                            plugin.config.set("spawn", "unset");
                            plugin.saveDefaultConfig();
                        } else {
                            plugin.config.set("spawn", player.getLocation());
                            plugin.saveDefaultConfig();
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " dir fehlen die Rechte dazu");
                    }
                    break;
                case "setspectate":
                    if(commandSender.hasPermission("tw.admin")){
                        if(args[1].toLowerCase() == "unset"){
                            plugin.config.set("spectator", "unset");
                            plugin.saveDefaultConfig();
                        } else {
                            plugin.config.set("spectator", player.getLocation());
                            plugin.saveDefaultConfig();
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " dir fehlen die Rechte dazu");
                    }
                    break;
                case "setendlocation":
                    if(commandSender.hasPermission("tw.admin")){
                        if(args[1].toLowerCase() == "unset"){
                            plugin.config.set("endLoc", "unset");
                            plugin.saveDefaultConfig();
                        } else {
                            plugin.config.set("endLoc", player.getLocation());
                            plugin.saveDefaultConfig();
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " dir fehlen die Rechte dazu");
                    }
                    break;
            }
        }
        return false;
    }
}
