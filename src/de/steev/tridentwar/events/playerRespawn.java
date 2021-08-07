package de.steev.tridentwar.events;

import de.steev.tridentwar.data.Items;
import de.steev.tridentwar.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class playerRespawn implements Listener {

    private main plugin;
    public playerRespawn(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player p = event.getPlayer();

        for(String pd : plugin.playerDataConfig.getKeys(false)){
            if(plugin.playerDataConfig.getString(event.getPlayer().getUniqueId() + ".name") == p.getDisplayName()){

                for(ItemStack is : p.getInventory()) {
                    try {
                        if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                            return;
                        }
                    } catch (Exception e){
                        // nothing happens
                    }
                }

                p.getInventory().addItem(Items.trident());

                return;
            }
        }
        // when a player respawns remove trident from inventory
        for(ItemStack is : p.getInventory()) {
            try {
                if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                    p.getInventory().remove(is);
                }
            } catch (Exception e){
                // nothing happens
            }
        }
    }
}
