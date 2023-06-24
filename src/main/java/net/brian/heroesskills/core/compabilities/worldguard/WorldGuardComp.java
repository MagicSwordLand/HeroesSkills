package net.brian.heroesskills.core.compabilities.worldguard;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.core.compabilities.CompatibilityComponent;

public class WorldGuardComp implements CompatibilityComponent {

    private final HeroesSkills plugin;
    public WorldGuardComp(HeroesSkills plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        plugin.getAllyEnemyDistinguishService().registerComponent(new PvpFlagComponent());
    }

    @Override
    public void onDisable() {

    }
}
