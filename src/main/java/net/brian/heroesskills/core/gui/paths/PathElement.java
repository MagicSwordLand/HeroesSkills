package net.brian.heroesskills.core.gui.paths;

import lombok.Getter;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.skills.AbstractSkill;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;

public class PathElement {

    @Getter
    private final AbstractSkill abstractSkill;

    @Getter
    private final Map<String,Integer> pathSlots;

    private List<Function<PlayerSkillProfile,Boolean>> conditions;

    public PathElement(AbstractSkill abstractSkill, Map<String,Integer> pathSlots, Function<PlayerSkillProfile,Boolean>... conditions){
        this.abstractSkill = abstractSkill;
        this.pathSlots = pathSlots;
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }

    public boolean valid(PlayerSkillProfile skillProfile){
        for (Function<PlayerSkillProfile, Boolean> function : conditions) {
            if(!function.apply(skillProfile)) return false;
        }
        return true;
    }

}
