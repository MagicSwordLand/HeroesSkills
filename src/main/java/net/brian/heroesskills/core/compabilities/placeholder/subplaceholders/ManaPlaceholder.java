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
        if(args.length == 0) {
            double mana = plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMana();
            mana = Math.round(mana*10)/10.0;
            return Double.toString(mana);
        }
        if(args[0].equals("max")) {
            double max = plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMaxMana();
            max = Math.round(max*10)/10.0;
            return String.valueOf(max);
        }
        return String.valueOf(plugin.getManaProvider().getManaEntity(skillProfile.getUuid()).getMana());
    }

}
