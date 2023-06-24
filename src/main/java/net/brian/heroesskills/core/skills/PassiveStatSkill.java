package net.brian.heroesskills.core.skills;

import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class PassiveStatSkill extends AbstractSkill {

    public static final String KEY = "HeroesSkill-";

    private final BiFunction<PlayerSkillProfile,SkillData,Icon> icon;
    private final BiFunction<PlayerSkillProfile,SkillData,Double> amountFunction;
    private final String stat;


    public PassiveStatSkill(
            String skillID,
            String display,
            int maxLevel,
            String stat,
            BiFunction<PlayerSkillProfile,SkillData,Double>  amountFunction,
            BiFunction<PlayerSkillProfile,SkillData,Icon> icon) {
        super(skillID,display);
        this.stat = stat;
        this.maxLevel = maxLevel;
        this.amountFunction = amountFunction;
        this.icon = icon;
    }

    @Override
    protected Icon getIcon(PlayerSkillProfile player, SkillData skillData) {
        return icon.apply(player,skillData);
    }

    public void onActivate(@NotNull PlayerSkillProfile playerProfile,SkillData skillData){
        StatInstance statInstance = PlayerData.get(playerProfile.getUuid()).getStats().getMap().getInstance(stat);
        StatModifier statModifier = new StatModifier(
                "HeroesSkill-"+skillID,stat,
                amountFunction.apply(playerProfile,skillData));
        statInstance.addModifier(statModifier);
    }

    public void onDeactivate (@NotNull PlayerSkillProfile playerProfile,SkillData skillData){
        if(!playerProfile.getPlayer().isOnline()) return;
        PlayerData.get(playerProfile.getUuid()).getStats().getMap().getInstance(stat).remove(KEY+skillID);
    }

}
