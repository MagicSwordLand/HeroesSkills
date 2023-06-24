package net.brian.heroesskills.core.compabilities;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.core.compabilities.mythicmobs.MythicMobsLoader;
import net.brian.heroesskills.core.compabilities.worldguard.WorldGuardComp;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CompatibilityManager {

    List<CompatibilityComponent> components = new ArrayList<>();

    public CompatibilityManager(HeroesSkills plugin){
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            components.add(new WorldGuardComp(plugin));
        }
        components.add(new MythicMobsLoader());
        loadComponents();
    }

    private void loadComponents(){
        components.forEach(CompatibilityComponent::onEnable);
    }

}
