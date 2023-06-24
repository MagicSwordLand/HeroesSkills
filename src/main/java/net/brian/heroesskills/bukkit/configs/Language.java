package net.brian.heroesskills.bukkit.configs;

import net.brian.playerdatasync.config.SpigotConfig;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Language extends SpigotConfig {

    public static String BUTTON_GUI_TITLE;
    public static String MAIN_PATH_GUI_TITLE;
    public static String CAST_SUCCESS;
    public static String CAST_NO_MANA;
    public static String CAST_IN_COOLDOWN;
    public static String BUFF_ALLY_ENABLED;
    public static String BUFF_ALLY_DISABLED;
    public static String ATTACK_NOT_ENEMY_ENABLED;
    public static String ATTACK_NOT_ENEMY_DISABLED;
    public static String PATH_NOT_UNLOCKED;


    public Language(JavaPlugin plugin) {
        super(plugin, "language.yml");
        reload();
    }

    public void reload(){
        loadFile();
        BUTTON_GUI_TITLE = IridiumColorAPI.process(configuration.getString("BUTTON_GUI_TITLE",""));
        MAIN_PATH_GUI_TITLE = IridiumColorAPI.process(configuration.getString("MAIN_PATH_GUI_TITLE",""));
        CAST_SUCCESS = IridiumColorAPI.process(configuration.getString("CAST_SUCCESS",""));
        CAST_NO_MANA = IridiumColorAPI.process(configuration.getString("CAST_NO_MANA",""));
        CAST_IN_COOLDOWN = IridiumColorAPI.process(configuration.getString("CAST_IN_COOLDOWN",""));
        BUFF_ALLY_DISABLED = IridiumColorAPI.process(configuration.getString("BUFF_ALLY_DISABLED",""));
        BUFF_ALLY_ENABLED = IridiumColorAPI.process(configuration.getString("BUFF_ALLY_DISABLED",""));
        ATTACK_NOT_ENEMY_DISABLED = IridiumColorAPI.process(configuration.getString("ATTACK_NOT_ENEMY_DISABLED",""));
        ATTACK_NOT_ENEMY_ENABLED = IridiumColorAPI.process(configuration.getString("ATTACK_NOT_ENEMY_ENABLED",""));
        PATH_NOT_UNLOCKED = IridiumColorAPI.process(configuration.getString("PATH_NOT_UNLOCKED",""));
    }

}
