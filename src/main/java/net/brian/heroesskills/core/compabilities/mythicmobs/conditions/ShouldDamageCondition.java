package net.brian.heroesskills.core.compabilities.mythicmobs.conditions;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.conditions.IEntityComparisonCondition;
import io.lumine.mythic.core.utils.annotations.MythicCondition;
import net.brian.heroesskills.HeroesSkills;
import org.bukkit.entity.Player;

@MythicCondition(
        name = "isEnemy",
        description = "Define whether a entity is enemy to the caster"
)
public class ShouldDamageCondition implements IEntityComparisonCondition {



    @Override
    public boolean check(AbstractEntity caster, AbstractEntity target) {
        if(caster.getBukkitEntity() instanceof Player player){
            return HeroesSkills.getInstance().getAllyEnemyDistinguishService().shouldDamage(player,target.getBukkitEntity());
        }
        return true;
    }

}
