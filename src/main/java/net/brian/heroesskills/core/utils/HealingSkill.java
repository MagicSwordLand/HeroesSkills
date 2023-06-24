package net.brian.heroesskills.core.utils;

import net.brian.heroesskills.api.events.HealingSkillEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HealingSkill {

    public static void heal(Player caster, LivingEntity target, double amount){
        double healAmount = Math.min(target.getMaxHealth()-target.getHealth(),amount);
        Bukkit.getPluginManager().callEvent(new HealingSkillEvent(caster,target,amount));
        target.setHealth(target.getHealth()+healAmount);
    }

}
