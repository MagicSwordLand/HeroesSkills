package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.api.skills.casting.ClickSequence;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;

public class ButtonPlaceholder extends SubPlaceholder {
    public ButtonPlaceholder(HeroesSkills plugin) {
        super(plugin, "button");
    }

    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length==0) return "";
        try {
            ClickSequence clickSequence = ClickSequence.valueOf(args[0].toUpperCase());
            return skillProfile.getButtonSkill(clickSequence).map(AbstractSkill::getDisplayName)
                    .orElse("&c尚未綁定");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        return null;
    }
}
