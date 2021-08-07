package de.steev.tridentwar.events;

import de.steev.tridentwar.data.Items;
import de.steev.tridentwar.main;
import de.steev.tridentwar.warcontroller;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerConnectionEvents implements Listener {
    private main plugin;

    public PlayerConnectionEvents(main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        if(plugin.eventActive) {

            // Scans player inventory for tridents to prevent multiple tridents
            for(ItemStack is : event.getPlayer().getInventory()) {
                try {
                    if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                        return;
                    }
                } catch (Exception e){
                    // nothing happens
                }
            }

            // Gives player needed tools for war
            event.getPlayer().getInventory().addItem(Items.trident());

            // messages the player about ongoing round
            event.getPlayer().sendMessage(ChatColor.AQUA + "Steev's Trident War: " + "Achtung hier herscht grade krieg");

            // Add player to the game
            plugin.playerDataConfig.set( event.getPlayer().getUniqueId() + ".life", 4 );
            plugin.playerDataConfig.set( event.getPlayer().getUniqueId() + ".name",  event.getPlayer().getDisplayName());
            plugin.saveplayerdata();
        } else {
            for(ItemStack is : event.getPlayer().getInventory().getContents()){
                try {
                    if(is.getItemMeta() != null){
                        if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                            // System.out.println( is.getItemMeta().getDisplayName().contains("Kriegs Dreizack"));
                            event.getPlayer().getInventory().remove(is);
                        }
                    }
                } catch (Exception e){
                    // this wont be catched
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(plugin.eventActive) {
            // stops war when less then 2 players are online
            if(Bukkit.getServer().getOnlinePlayers().size() < 2){
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
            // Removes player from game
            UUID uuid = event.getPlayer().getUniqueId();
            plugin.playerDataConfig.set( "" + uuid, null);
            plugin.saveplayerdata();
        }else{
            return;
        }
    }
}
