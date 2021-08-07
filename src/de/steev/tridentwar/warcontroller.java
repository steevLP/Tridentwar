package de.steev.tridentwar;

import de.steev.tridentwar.data.Items;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class warcontroller {
    /** Handles Starting the Trident War */
    public static void StartTridentWar(main plugin) {
        // TODO: set everyone in adventure
        plugin.alive = Bukkit.getOnlinePlayers().size();
        // TODO: Create Bossbar with timer in it
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().addItem(Items.trident());
            plugin.playerDataConfig.set(p.getUniqueId() + ".life", 4);
            plugin.playerDataConfig.set(p.getUniqueId() + ".name", p.getDisplayName());
        }
        plugin.saveplayerdata();
    }

    /** Handles stoping a trident war */
    public static void StopTridentWar(main plugin) {

        // TODO set everyone to default gamemode
        plugin.alive = 0;
        plugin.eventActive = false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            for(ItemStack is : p.getInventory().getContents()){
                try {
                    if(is.getItemMeta() != null){
                        if(is.getItemMeta().getDisplayName().contains("Kriegs Dreizack")){
                            p.getInventory().remove(is);
                        }
                    }
                } catch (Exception e){
                    // this wont be catched
                }
            }

            plugin.playerDataConfig.set(p.getUniqueId() + "", null);
        }
        plugin.saveplayerdata();
    }
}
