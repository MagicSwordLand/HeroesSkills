package net.brian.heroesskills.core.skills;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class MythicActiveSkill extends ActiveSkill {

    private final BiFunction<PlayerSkillProfile,SkillData,Icon> icon;


    public MythicActiveSkill(String mythicSkill,
                             String display,
                             int maxLevel,
                             double manaConsume,
                             double cooldown,
                             BiFunction<PlayerSkillProfile,SkillData,Icon> icon) {
        super(mythicSkill,display);
        this.icon = icon;
        this.manaConsume = manaConsume;
        this.maxLevel = maxLevel;
        this.cooldown = cooldown;
    }


    @Override
    public void onCast( @NotNull PlayerSkillProfile playerProfile,SkillData skillData) {
        MythicBukkit.inst().getAPIHelper().castSkill(playerProfile.getPlayer(),getSkillID());
    }

    @Override
    protected Icon getIcon(PlayerSkillProfile skillProfile, SkillData skillData) {
        return icon.apply(skillProfile,skillData);
    }

}
