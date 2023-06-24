package net.brian.heroesskills.core.player.allyenemy;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.allyenemy.AllyEnemyDistinguishService;
import net.brian.heroesskills.api.players.allyenemy.DistinguishComponent;
import net.brian.heroesskills.api.players.allyenemy.EvaluateResult;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DistinguishServiceImpl extends AllyEnemyDistinguishService {
    
    private final HashMap<Class<?>,DistinguishComponent> componentMap = new HashMap<>();
    private List<DistinguishComponent> sortedComponents = new ArrayList<>(); 

    private final Comparator<DistinguishComponent> cmp = ((o1, o2) -> o2.getPriority()-o1.getPriority());

    public DistinguishServiceImpl(HeroesSkills plugin){
        registerComponent(new EntityTypeComponent());
    }


    @Override
    public boolean shouldBuff(Player player, Entity target) {
        if(player.equals(target)) return true;
        for (DistinguishComponent component : sortedComponents) {
            EvaluateResult result = component.shouldBuff(player,target);
            if(result.equals(EvaluateResult.TRUE)) return true;
            if(result.equals(EvaluateResult.FALSE)) return false;
        }
        return PlayerSkillProfile.get(player.getUniqueId()).map(profile->profile.buffNotAlly).orElse(false);
    }

    @Override
    public boolean shouldDamage(Player player, Entity target) {
        if(player.equals(target)) return false;
        for (DistinguishComponent component : sortedComponents) {
            EvaluateResult result = component.shouldDamage(player,target);
            if(result.equals(EvaluateResult.TRUE)) return true;
            if(result.equals(EvaluateResult.FALSE)) return false;
        }
        return PlayerSkillProfile.get(player.getUniqueId()).map(profile->profile.damageNotEnemy).orElse(false);

    }

    @Override
    public void registerComponent(DistinguishComponent component) {
        componentMap.put(component.getClass(),component);
        sortedComponents = componentMap.values().stream().sorted(cmp).collect(Collectors.toList());
        sortedComponents.forEach(c->Bukkit.getLogger().log(Level.INFO,c.getClass().getName()));
    }

}
