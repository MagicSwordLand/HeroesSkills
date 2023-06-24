package net.brian.heroesskills.core.compabilities.mythicmobs;

import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.bukkit.events.MythicTargeterLoadEvent;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.core.compabilities.CompatibilityComponent;
import net.brian.heroesskills.core.compabilities.mythicmobs.conditions.HandHasLore;
import net.brian.heroesskills.core.compabilities.mythicmobs.conditions.ShouldBuffCondition;
import net.brian.heroesskills.core.compabilities.mythicmobs.conditions.ShouldDamageCondition;
import net.brian.heroesskills.core.compabilities.mythicmobs.mechanic.EditManaMechanic;
import net.brian.heroesskills.core.compabilities.mythicmobs.mechanic.HeroHealMechanic;
import net.brian.heroesskills.core.compabilities.mythicmobs.targeter.AllyInRadius;
import net.brian.heroesskills.core.compabilities.mythicmobs.targeter.EnemyInRadius;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobsLoader implements Listener , CompatibilityComponent {


    ShouldDamageCondition shouldDamageCondition = new ShouldDamageCondition();
    ShouldBuffCondition isAllyCondition = new ShouldBuffCondition();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, HeroesSkills.getInstance());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onConditionLoad(MythicConditionLoadEvent event){
        String conditionName = event.getConditionName();
        if(conditionName.equalsIgnoreCase("isEnemy") || conditionName.equalsIgnoreCase("shouldDamage")){
            event.register(shouldDamageCondition);
        }
        else if(conditionName.equalsIgnoreCase("isAlly") || conditionName.equalsIgnoreCase("shouldBuff")){
            event.register(isAllyCondition);
        }
        else if(conditionName.equalsIgnoreCase("HandHasLore")){
            event.register(new HandHasLore(event.getConfig().getLine(),event.getConfig()));
        }
    }

    @EventHandler
    public void onTargetLoad(MythicTargeterLoadEvent event){
        String name = event.getTargeterName().toLowerCase();
        switch (name){
            case "allyinradius"-> event.register(new AllyInRadius(event.getConfig()));
            case "enemyinradius"-> event.register(new EnemyInRadius(event.getConfig()));
        }
    }

    @EventHandler
    public void onMechanicLoad(MythicMechanicLoadEvent event){
        String name = event.getMechanicName();
        if(name.equalsIgnoreCase("editmana")){
            event.register(new EditManaMechanic(event.getConfig()));
            return;
        }
        else if(name.equalsIgnoreCase("HeroHeal")){
            event.register(new HeroHealMechanic(event.getConfig()));
        }
    }
}
