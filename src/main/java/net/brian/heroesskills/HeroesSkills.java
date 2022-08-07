package net.brian.heroesskills;

import io.lumine.mythic.lib.api.stat.SharedStat;
import lombok.Getter;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.mana.ManaProvider;
import net.brian.heroesskills.api.skills.SkillManager;
import net.brian.heroesskills.bukkit.commands.CommandManager;
import net.brian.heroesskills.bukkit.listeners.CastingListener;
import net.brian.heroesskills.core.compabilities.mmo.MMOManaProvider;
import net.brian.heroesskills.core.compabilities.placeholder.PlaceholderManager;
import net.brian.heroesskills.core.gui.SkillSelectGui;
import net.brian.heroesskills.core.skills.SkillManagerImpl;
import net.brian.playerdatasync.PlayerDataSync;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeroesSkills extends JavaPlugin {


    @Getter
    private static HeroesSkills instance;


    @Getter
    private SkillManager skillManager;

    @Getter
    private ManaProvider manaProvider;

    @Getter
    private SkillSelectGui skillSelectGui;


    @Override
    public void onLoad(){
        MMOItems.plugin.getStats().register(new DoubleStat(SharedStat.MANA_REGENERATION, Material.NETHER_STAR,"回魔", new String[]{"額外回魔"}));
        MMOItems.plugin.getStats().register(new DoubleStat(SharedStat.HEALTH_REGENERATION, Material.NETHER_STAR,"回血",new String[]{"額外回血"}));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        PlayerDataSync.getInstance().register("heroesskills", PlayerSkillProfile.class);
        manaProvider = new MMOManaProvider(this);
        skillManager = new SkillManagerImpl(this);
        skillSelectGui = new SkillSelectGui(instance,skillManager);
        getCommand("HeroesSkill").setExecutor(new CommandManager(this));
        registerListeners();
        new PlaceholderManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(){

        Bukkit.getPluginManager().registerEvents(new CastingListener(this),this);
    }
}
