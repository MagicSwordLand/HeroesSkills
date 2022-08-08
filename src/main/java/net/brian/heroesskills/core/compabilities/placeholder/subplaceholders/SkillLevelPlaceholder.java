package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;

public class SkillLevelPlaceholder extends SubPlaceholder {

    public SkillLevelPlaceholder(HeroesSkills plugin) {
        super(plugin, "level");
    }

    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length == 0) return "0";
        return String.valueOf(skillProfile.getSkillData(args[0]).level);
    }

}
