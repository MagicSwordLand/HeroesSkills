package net.brian.heroesskills.core.skills;

import io.lumine.mythic.core.skills.targeters.SelectorType;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

public class PassiveMultipleStatSkill extends AbstractSkill {

    private final BiFunction<PlayerSkillProfile, SkillData, Icon> icon;
    private final BiFunction<PlayerSkillProfile,SkillData, Map<String,Double>> amountFunction;

    private final HashMap<UUID, Set<String>> players = new HashMap<>();

    public PassiveMultipleStatSkill(
            String skillID,
            String display,
            int maxLevel,
            BiFunction<PlayerSkillProfile,SkillData,Map<String,Double>>  amountFunction,
            BiFunction<PlayerSkillProfile,SkillData,Icon> icon) {
        super(skillID,display);
        this.maxLevel = maxLevel;
        this.amountFunction = amountFunction;
        this.icon = icon;
    }

    @Override
    protected Icon getIcon(PlayerSkillProfile profile, SkillData skillData) {
        return icon.apply(profile,skillData);
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


}