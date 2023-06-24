package net.brian.heroesskills.core.compabilities.worldguard;

import com.sk89q.worldguard.protection.flags.Flags;
import net.brian.heroesskills.api.players.allyenemy.DistinguishComponent;
import net.brian.heroesskills.api.players.allyenemy.EvaluateResult;
import net.brian.heroesskills.core.compabilities.worldguard.flags.FlagUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public class PvpFlagComponent implements DistinguishComponent {

    public static final int PRIORITY = 100;

    @Override
    public EvaluateResult shouldDamage(Player caster, Entity target) {
        if(target.getType().equals(EntityType.PLAYER) && !FlagUtils.canPvp(target.getLocation())) {
            return EvaluateResult.FALSE;
        }
        return EvaluateResult.UNDEFINED;
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
