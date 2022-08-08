package net.brian.heroesskills.core.compabilities.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.compabilities.placeholder.subplaceholders.*;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceholderManager extends PlaceholderExpansion {

    List<SubPlaceholder> subPlaceholders = new ArrayList<>();

    public PlaceholderManager(HeroesSkills plugin){
        subPlaceholders.add(new ManaPlaceholder(plugin));
        subPlaceholders.add(new ButtonPlaceholder(plugin));
        subPlaceholders.add(new SkillLevelPlaceholder(plugin));
        subPlaceholders.add(new SkillPointsPlaceholder(plugin));
        subPlaceholders.add(new PathPlaceholder(plugin));
        this.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hs";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Sleep_AllDay";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }


    @Nullable
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Optional<PlayerSkillProfile> optSkillProfile = PlayerSkillProfile.get(player.getUniqueId());
        if(optSkillProfile.isEmpty()) return "資料載入中";
        String[] args = params.split("_",2);
        if(args.length == 0) return "";
        SubPlaceholder subPlaceholder = getSubPlaceholder(args[0]);
        if(subPlaceholder != null){
            if(args.length == 2) return subPlaceholder.onRequest(optSkillProfile.get(),args[1].split("_"));
            return subPlaceholder.onRequest(optSkillProfile.get(),new String[0]);
        }
        return "";
    }

    public SubPlaceholder getSubPlaceholder(String name){
        for (SubPlaceholder subPlaceholder : subPlaceholders) {
            if(subPlaceholder.name.equalsIgnoreCase(name)) return subPlaceholder;
        }
        return null;
    }


}
