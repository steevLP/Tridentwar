package de.steev.tridentwar.events;

import de.steev.tridentwar.data.Items;
import de.steev.tridentwar.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class onProjectileLaunch implements Listener {
    private main plugin;
    public HashMap<UUID, BukkitTask> tasks = new HashMap<UUID, BukkitTask>();

    public onProjectileLaunch(main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        try {
            tasks.get(((Player)event.getEntity().getShooter()).getUniqueId()).cancel();
        } catch (Exception e){
            // eigentlich sollte hier jetzt error handling passieren, aber nö.
        }
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if(plugin.eventActive){
                    for(ItemStack is : ((Player)event.getEntity().getShooter()).getInventory()) {
                        try {
                            if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                                return;
                            }
                        } catch (Exception e){
                            // nothing happens
                        }
                    }

                    // When no trident was found give a new trident to the player
                    ((Player)event.getEntity().getShooter()).getInventory().addItem(Items.trident());
                }
            }
        },200);
        tasks.put(((Player)event.getEntity().getShooter()).getUniqueId(), task);
    }
}
