package net.brian.heroesskills.core.compabilities.mythicmobs.mechanic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.mana.ManaEntity;
import net.brian.heroesskills.core.utils.HealingSkill;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HeroHealMechanic implements ITargetedEntitySkill {

    PlaceholderDouble amount;

    public HeroHealMechanic(MythicLineConfig mlc){
        amount = mlc.getPlaceholderDouble(new String[]{"a","amount"},"0");
    }


    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        if(skillMetadata.getCaster().getEntity().getBukkitEntity() instanceof Player player){
            double a = amount.get(skillMetadata);
            HealingSkill.heal(player, (LivingEntity) abstractEntity.getBukkitEntity(),a);
            return SkillResult.SUCCESS;
        }
        return SkillResult.INVALID_TARGET;
    }
}
