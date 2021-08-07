package de.steev.tridentwar.events;

import de.steev.tridentwar.main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class itemDrop implements Listener {
    private main plugin;

    public itemDrop(main plugin){
        this.plugin = plugin;
    }

    // deletes trident from player drops
    @EventHandler
    public void onItemDrop(PlayerDeathEvent event){
        for(ItemStack item : event.getDrops()){
            try {
                if(item.getItemMeta().getDisplayName() == "Kriegs Dreizack"){
                    item.setType(Material.AIR);
                }
            } catch (Exception e){
                // Nothing gets handled here
            }
        }

    }

    // Stops players from droping the war trident
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(plugin.eventActive){
            if(plugin.playerDataConfig.get(event.getPlayer().getUniqueId() + ".name") != null){
                event.setCancelled(true);
            } else {
                return;
            }
        }
    }
}
