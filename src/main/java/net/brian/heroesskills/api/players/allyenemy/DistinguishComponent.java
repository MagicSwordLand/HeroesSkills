package net.brian.heroesskills.api.players.allyenemy;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface DistinguishComponent {

    EvaluateResult shouldDamage(Player caster,Entity target);
    EvaluateResult shouldBuff(Player caster,Entity target);
    int getPriority();

}
