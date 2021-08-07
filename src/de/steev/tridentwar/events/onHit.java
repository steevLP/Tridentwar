package de.steev.tridentwar.events;

import de.steev.tridentwar.data.Items;
import de.steev.tridentwar.main;
import de.steev.tridentwar.warcontroller;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class onHit implements Listener {
    private main plugin;
    public onHit(main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onTridentHit(ProjectileHitEvent event){
        if(plugin.eventActive){
            try {
                if(event.getEntity().getShooter() instanceof Player){
                    if(event.getEntityType() == EntityType.TRIDENT){
                        if(event.getHitEntity() instanceof Player){

                            if(plugin.playerDataConfig.getString(((Player)event.getHitEntity()).getUniqueId() + ".life") != null){

                                UUID uuid = ((Player)event.getHitEntity()).getUniqueId();
                                int life = plugin.playerDataConfig.getInt(uuid + ".life") - 1;

                                // Detects when trident hits a not player block
                                event.getEntity().remove();

                                // Gives Player a Trident
                                ((Player) event.getEntity().getShooter()).getInventory().addItem(Items.trident());

                                // play a "Ding" sound on the attackers side at hit
                                if(life == 4) {
                                    ((Player)event.getEntity().getShooter()).playSound(((Player)event.getEntity().getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 0.5f);
                                } else if(life == 3) {
                                    ((Player)event.getEntity().getShooter()).playSound(((Player)event.getEntity().getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
                                } else if(life == 2) {
                                    ((Player)event.getEntity().getShooter()).playSound(((Player)event.getEntity().getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1.5f);
                                } else if(life == 1) {
                                    ((Player)event.getEntity().getShooter()).playSound(((Player)event.getEntity().getShooter()).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 2f);
                                }

                                // Runs sound when a player gets hit
                                ((Player)event.getHitEntity()).playSound(((Player)event.getHitEntity()).getLocation(), Sound.ENTITY_PLAYER_DEATH, 1f, 1f);

                                // Subtracts life and spams the chat with debug messages
                                plugin.playerDataConfig.set( uuid + ".life", life);
                                ((Player)event.getHitEntity()).sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " du wurdest getroffen und hast noch: " + ChatColor.BOLD + life + ChatColor.RESET + ChatColor.RED + " Leben");
                                if(life == 4) {
                                    ((Player)event.getHitEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Leben: " + life));
                                } else if(life < 1) {
                                    ((Player)event.getHitEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW + "Leben: " + life));
                                } else if(life <= 1) {
                                    ((Player)event.getHitEntity()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Leben: " + life));
                                }

                                plugin.saveplayerdata();

                                // Handles Death
                                if( life == 0){
                                    // Deletes player from Database
                                    plugin.playerDataConfig.set(uuid.toString(), null);
                                    plugin.saveplayerdata();

                                    // Messages Player about Death
                                    ((Player)event.getHitEntity()).playSound(((Player)event.getHitEntity()).getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                                    ((Player)event.getHitEntity()).sendTitle(ChatColor.RED + "Ausgeschieden!", ChatColor.RED + "Du bist aus dem Trident War ausgeschieden", 1, 20, 1);

                                    // Deletes trident of people who died
                                    for(Trident t : ((Player)event.getHitEntity()).getWorld().getEntitiesByClass(Trident.class)){
                                        for(String pd : plugin.playerDataConfig.getKeys(false)) {
                                            if(((Player)t.getShooter()).getDisplayName() == plugin.playerDataConfig.getString(pd + ".name")){
                                                return;
                                            }
                                        }
                                        t.remove();
                                    }

                                    // Checks if only one is alive
                                    Bukkit.getLogger().info(ChatColor.GRAY + "[" +  ChatColor.YELLOW +  ChatColor.BOLD + "DEBUG" + ChatColor.RESET + ChatColor.GRAY + "]: is alive: " + plugin.playerDataConfig.getKeys(false).size());
                                    if(plugin.playerDataConfig.getKeys(false).size() <= 1){

                                        if(plugin.playerDataConfig.getKeys(false).size() > 0){
                                            for(String pd : plugin.playerDataConfig.getKeys(false)) {
                                                for (Player p : Bukkit.getOnlinePlayers()) {

                                                    // Message players about the winner
                                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
                                                    p.sendTitle(ChatColor.GREEN + "ENDE!",ChatColor.GOLD + plugin.playerDataConfig.getString(pd  + ".name") +" Hat den Trident War gewonnen!");
                                                }

                                                // for security loop all players once more and remove all tridents with meeting criteria
                                                for(ItemStack is : Bukkit.getServer().getPlayer(plugin.playerDataConfig.getString(pd  + ".name")).getInventory()) {
                                                    try {
                                                        if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                                                            Bukkit.getServer().getPlayer(plugin.playerDataConfig.getString(pd  + ".name")).getInventory().remove(is);
                                                        }
                                                    } catch (Exception e){
                                                        // nothing happens
                                                    }
                                                }

                                                // Remove trident entity
                                                for(Trident t : ((Player)event.getHitEntity()).getWorld().getEntitiesByClass(Trident.class)){
                                                    t.remove();
                                                }
                                            }

                                            // TODO: make endlocation lobby porting

                                            warcontroller.StopTridentWar(plugin);
                                        } else {
                                            ((Player)event.getHitEntity()).sendMessage(ChatColor.AQUA + "Steev's Trident War: " + ChatColor.RED + " es gibt keinen Sieger!");
                                            warcontroller.StopTridentWar(plugin);
                                        }
                                    }

                                    // Sends Player to Spawn
                                    if(plugin.config.get("spectator") != "unset"){ ((Player)event.getHitEntity()).teleport(((Location)plugin.config.get("spectator"))); }

                                }

                            } else {
                                event.setCancelled(true);
                                return;
                            }

                        } else {
                            // Detects when trident hits a not player block
                            event.getEntity().remove();

                            // Gives Player a Trident
                            ((Player) event.getEntity().getShooter()).getInventory().addItem(Items.trident());
                        }

                    }
                }
            } catch (Exception e){
                // Nothing to print here
                // this would only happen when no entity should be an entity
            }

            // disables damage
            event.setCancelled(true);
        } else {
            return;
        }
    }
}
