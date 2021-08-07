package de.steev.tridentwar.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    public static ItemStack trident(){
        ItemStack trident = new ItemStack(Material.TRIDENT);
        ItemMeta meta = trident.getItemMeta();
        meta.setDisplayName("Kriegs Dreizack");
        trident.setItemMeta(meta);
        return trident;
    }
}
