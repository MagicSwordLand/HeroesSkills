package net.brian.heroesskills.core.gui.paths.conditions;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;

import java.util.function.Function;

public class SkillLevelCondition implements Function<PlayerSkillProfile,Boolean> {

    String skill;
    int level;
    public SkillLevelCondition(String skill,int level){
        this.skill = skill;
        this.level = level;
    }

    @Override
    public Boolean apply(PlayerSkillProfile skillProfile) {
        boolean valid = skillProfile.getSkillData(skill).level >= level;
        if(valid) return true;
        HeroesSkills.getInstance().getSkillManager().get(skill).ifPresent(skill->{
            skillProfile.getPlayer().sendMessage("你的"+skill.getDisplayName()+"等級必須到達"+level+"才能升級此天賦");
        });
        return false;
    }
}
