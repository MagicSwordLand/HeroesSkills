package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.api.skills.casting.ClickSequence;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;
import net.brian.playerdatasync.util.time.TimeUnit;

import java.util.Optional;

public class CooldownPlaceholder extends SubPlaceholder {

    public CooldownPlaceholder(HeroesSkills plugin) {
        super(plugin, "cooldown");

    }

    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length == 0) return "";
        Optional<ActiveSkill> activeSkill = skillProfile.getButtonSkill(ClickSequence.valueOf(args[0].toUpperCase()));
        if(activeSkill.isPresent()){
            SkillData skillData = skillProfile.getSkillData(activeSkill.get().getSkillID());
            long a = ((long) activeSkill.get().getCooldown())*1000 - (System.currentTimeMillis() - skillData.lastCast);
            if(a > 0){
                return TimeUnit.getDisplayTime(a);
            }
            else return "準備就緒";
        }
        return "";
    }

}
