package net.brian.heroesskills.api.players.allyenemy;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class AllyEnemyDistinguishService {

    @Getter
    private static AllyEnemyDistinguishService instance;

    protected AllyEnemyDistinguishService(){
        instance = this;
    }

    public abstract boolean shouldBuff(Player player,Entity target);

    public abstract boolean shouldDamage(Player player, Entity target);

    public abstract void registerComponent(DistinguishComponent component);

}

