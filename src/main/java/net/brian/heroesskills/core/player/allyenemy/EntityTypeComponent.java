package net.brian.heroesskills.core.player.allyenemy;

import net.brian.heroesskills.api.players.allyenemy.DistinguishComponent;
import net.brian.heroesskills.api.players.allyenemy.EvaluateResult;
import org.bukkit.entity.*;

public class EntityTypeComponent implements DistinguishComponent {

    public static final int PRIORITY = 1;


    @Override
    public EvaluateResult shouldDamage(Player caster, Entity target) {
        EntityType type = target.getType();
        if(type.equals(EntityType.ARMOR_STAND)) return EvaluateResult.FALSE;
        if(target instanceof Damageable) return EvaluateResult.TRUE;
        else return EvaluateResult.FALSE;
    }

    @Override
    public EvaluateResult shouldBuff(Player caster, Entity target) {
        return EvaluateResult.UNDEFINED;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
