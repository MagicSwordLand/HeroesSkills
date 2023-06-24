package net.brian.heroesskills.core.skills;

import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.core.utils.Icon;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

public class MythicActivePassiveStat extends MythicActiveSkill{


    private final HashMap<UUID, Set<String>> players = new HashMap<>();
    BiFunction<PlayerSkillProfile, SkillData, Map<String,Double>> amountFunction;

    public MythicActivePassiveStat(String mythicSkill, String display, int maxLevel, double manaConsume, double cooldown, BiFunction<PlayerSkillProfile, SkillData, Icon> icon,
                                   BiFunction<PlayerSkillProfile,SkillData, Map<String,Double>>  amountFunction) {
        super(mythicSkill, display, maxLevel, manaConsume, cooldown, icon);
        this.amountFunction = amountFunction;
    }

    @Override
    public void onActivate(@NotNull PlayerSkillProfile playerProfile, SkillData skillData){
        StatMap statMap = PlayerData.get(playerProfile.getUuid()).getStats().getMap();
        Map<String,Double> map = amountFunction.apply(playerProfile, skillData);
        players.put(playerProfile.getUuid(),map.keySet());
        map.forEach((stat,amount)->{
            StatModifier statModifier = new StatModifier("HeroesSkill-"+skillID,stat, amount);
            statMap.getInstance(stat).addModifier(statModifier);
        });
    }

    @Override
    public void onDeactivate(PlayerSkillProfile skillProfile,SkillData skillData){
        if(!skillProfile.getPlayer().isOnline()) return;
        Set<String> stats = players.get(skillProfile.getUuid());
        if(stats == null || stats.isEmpty()) return;
        StatMap statMap = PlayerData.get(skillProfile.getUuid()).getStats().getMap();
        stats.forEach(stat->{
            statMap.getInstance(stat).remove(PassiveStatSkill.KEY+skillID);
        });
    }

}
