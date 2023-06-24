package net.brian.heroesskills.core.compabilities.mythicmobs.mechanic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.mana.ManaEntity;
import net.brian.heroesskills.api.players.mana.ManaProvider;
import org.bukkit.entity.Player;

public class EditManaMechanic implements ITargetedEntitySkill {

    String action;
    PlaceholderDouble amount;

    public EditManaMechanic(MythicLineConfig mlc){
        action = mlc.getString("action","modify");
        amount = mlc.getPlaceholderDouble(new String[]{"a","amount"},"0");
    }


    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {
        if(abstractEntity.getBukkitEntity() instanceof Player player){
            ManaEntity manaEntity = HeroesSkills.getInstance().getManaProvider().getManaEntity(player.getUniqueId());
            double a = amount.get(skillMetadata);
            if(action.equals("modify")){
                manaEntity.add(a);
            }
            else if(action.equals("set")){
                manaEntity.setMana(a);
            }
            return SkillResult.SUCCESS;
        }
        return SkillResult.INVALID_TARGET;
    }
}
