package de.steev.Tridentwar.Handlers;

import de.steev.Tridentwar.Manager.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

/** creates the default message files found in resources/langs */
public class FileHandler {
    private GameManager gameManager;
    public FileHandler(GameManager gameManager){
        this.gameManager = gameManager;
        File de = new File(this.gameManager.getPlugin().getDataFolder() + "/langs/de-ger.yml");
        File en = new File(this.gameManager.getPlugin().getDataFolder() + "/langs/en-us.yml");
        if(!de.exists()) { genFile("langs/de-ger.yml"); }
        if(!en.exists()) { genFile("langs/en-us.yml"); }
    }

    /**
     * generates specified language.yml
     * @param path the intended file path
     */
    public void genFile(String path){
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            System.out.println("Language file is not present or misspelled");
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                File export = new File(this.gameManager.getPlugin().getDataFolder(), path);
                FileConfiguration exportConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(this.gameManager.getPlugin().getResource(path)));
                exportConfig.save(export);
            } catch (Exception e) {
                System.out.println("Error while Generating file \nException: " + e);
            }
        }
    }

    /**
     * proccesses variables from config
     * @param message the message to work with
     * @param player if given a player to use in message
     * @param loc a given location
     * @return processed string
     */
    public String replaceVars(String message, @Nullable Player player, @Nullable String loc){
        String msg;
        String location;

        // failsafe to prevent location from beeing null
        if(loc == null) { location = "undefined"; } else { location = loc; }

        // Processes given messages
        if (player != null) {
            msg = message.replace("%winner%", player.getDisplayName())
                    .replace("%min%","" + this.gameManager.getPlugin().config.getInt("minplayers"))
                    .replace("%default%", this.gameManager.getPlugin().config.getString("lobby-server").toString())
                    .replace("%location%", location);
        } else {
            msg = message.replace("%min%","" + this.gameManager.getPlugin().config.getInt("minplayers"))
                    .replace("%default%", this.gameManager.getPlugin().config.getString("lobby-server").toString())
                    .replace("%location%", location);
        }
        return msg;
    }
}
