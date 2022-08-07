package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;

public class ManaPlaceholder extends SubPlaceholder {
    public ManaPlaceholder(HeroesSkills plugin) {
        super(plugin, "mana");
    }

    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length == 0) return String.valueOf(plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMana());
        if(args[0].equals("max")) return String.valueOf(plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMaxMana());
        return String.valueOf(plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMana());
    }
}
