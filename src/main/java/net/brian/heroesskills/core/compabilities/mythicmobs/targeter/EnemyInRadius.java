package net.brian.heroesskills.core.compabilities.mythicmobs.targeter;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.targeters.IEntityTargeter;
import io.lumine.mythic.bukkit.BukkitAdapter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.allyenemy.AllyEnemyDistinguishService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class EnemyInRadius implements IEntityTargeter {

    double r;
    public EnemyInRadius(MythicLineConfig mlc){
        this.r = mlc.getDouble("r",3);
    }


    @Override
    public Collection<AbstractEntity> getEntities(SkillMetadata skillMetadata) {
        Location loc = BukkitAdapter.adapt(skillMetadata.getOrigin());
        Entity caster = skillMetadata.getCaster().getEntity().getBukkitEntity();
        AllyEnemyDistinguishService service = HeroesSkills.getInstance().getAllyEnemyDistinguishService();
        if(caster instanceof Player player){
            return loc.getWorld()
                    .getNearbyEntities(loc,r,r,r)
                    .stream()
                    .filter(e-> service.shouldDamage(player,e))
                    .map(BukkitAdapter::adapt)
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}
