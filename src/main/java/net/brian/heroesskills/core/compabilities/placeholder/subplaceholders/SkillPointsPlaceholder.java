package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;

public class SkillPointsPlaceholder extends SubPlaceholder {
    public SkillPointsPlaceholder(HeroesSkills plugin) {
        super(plugin, "SkillPoint");
    }


    // %hs_skillpoint_max%
    // %hs_skillpoint_remain%
    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length == 0) return "";
        if(args[0].equals("max")){
            return Integer.toString(skillProfile.getMaxSkillPoint());
        }
        return Integer.toString(skillProfile.getSkillPoint());
    }

}
