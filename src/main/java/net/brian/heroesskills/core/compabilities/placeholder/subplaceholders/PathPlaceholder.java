package net.brian.heroesskills.core.compabilities.placeholder.subplaceholders;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.compabilities.placeholder.SubPlaceholder;

public class PathPlaceholder extends SubPlaceholder {

    public PathPlaceholder(HeroesSkills plugin) {
        super(plugin, "path");
    }

    // %hs_path_<id>_unlocked
    // %hs_path_<id>_max
    @Override
    public String onRequest(PlayerSkillProfile skillProfile, String[] args) {
        if(args.length < 2) return "";
        return plugin.getMainPathGui().getSubPath(args[0]).map(subPath -> {
            if(args[1].equals("unlocked")) return String.valueOf(subPath.getUnlockedAmount(skillProfile));
            return String.valueOf(subPath.getSkillAmount());
        }).orElse("");
    }
}
