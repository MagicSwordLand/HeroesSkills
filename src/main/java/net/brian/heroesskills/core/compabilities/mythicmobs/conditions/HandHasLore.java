package net.brian.heroesskills.core.compabilities.mythicmobs.conditions;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.conditions.ICasterCondition;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.skills.SkillCondition;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class HandHasLore extends SkillCondition implements ICasterCondition ,IEntityCondition{

    String loreLine;

    public HandHasLore(String line, MythicLineConfig mlc) {
        super(line);
        loreLine = IridiumColorAPI.process(mlc.getString(new String[]{"s","lore"},""));
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {
        if(abstractEntity.getBukkitEntity() instanceof LivingEntity livingEntity){
            ItemStack mainHand = livingEntity.getEquipment().getItemInMainHand();
            if(!mainHand.hasItemMeta()) return false;
            if(!mainHand.getItemMeta().hasLore()) return false;
            return mainHand.getItemMeta().getLore().contains(loreLine);
        }
        return false;
    }

    @Override
    public boolean check(SkillCaster skillCaster) {
        if(skillCaster.getEntity().getBukkitEntity() instanceof LivingEntity livingEntity){
            ItemStack mainHand = livingEntity.getEquipment().getItemInMainHand();
            if(!mainHand.hasItemMeta()) return false;

            if(!mainHand.getItemMeta().hasLore()) return false;

            return mainHand.getItemMeta().getLore().contains(loreLine);
        }
        return false;
    }
}
