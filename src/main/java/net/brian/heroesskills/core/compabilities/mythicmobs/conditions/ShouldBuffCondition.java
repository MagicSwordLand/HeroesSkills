package net.brian.heroesskills.core.compabilities.mythicmobs.conditions;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.conditions.IEntityComparisonCondition;
import net.brian.heroesskills.HeroesSkills;
import org.bukkit.entity.Player;

public class ShouldBuffCondition implements IEntityComparisonCondition {

    @Override
    public boolean check(AbstractEntity caster, AbstractEntity target) {
        if(caster.getBukkitEntity() instanceof Player player){
            return HeroesSkills.getInstance().getAllyEnemyDistinguishService().shouldBuff(player,target.getBukkitEntity());
        }
        return false;
    }
}
