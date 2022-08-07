package net.brian.heroesskills.core.compabilities.placeholder;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import org.bukkit.entity.Player;

public abstract class SubPlaceholder {

    protected final String name;
    protected final HeroesSkills plugin;

    public SubPlaceholder(HeroesSkills plugin,String name){
        this.name = name;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public abstract String onRequest(PlayerSkillProfile skillProfile, String[] args);

}
