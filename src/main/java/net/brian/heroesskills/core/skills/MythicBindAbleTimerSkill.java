package net.brian.heroesskills.core.skills;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class MythicBindAbleTimerSkill extends ActiveSkill {

    Set<Player> players = new HashSet<>();
    long period;
    String mythicSkill;
    BukkitTask bukkitTask = null;

    private final BiFunction<PlayerSkillProfile,SkillData,Icon> icon;


    public MythicBindAbleTimerSkill(
            String mythicSkill,
            String display,
            long period,
            int maxLevel,
            BiFunction<PlayerSkillProfile,SkillData,Icon> icon) {
        super(mythicSkill, display);
        this.icon = icon;
        this.period = period;
        this.mythicSkill = mythicSkill;
        this.maxLevel = maxLevel;
    }

    @Override
    protected Icon getIcon(PlayerSkillProfile player, SkillData skillData) {
        return icon.apply(player,skillData);
    }

    @Override
    public void onCast(@NotNull PlayerSkillProfile playerProfile, SkillData skillData) {
    }

    @Override
    public void onEquip(@NotNull PlayerSkillProfile playerProfile, SkillData skillData){
        players.add(playerProfile.getPlayer());

    }

    @Override
    public void onUnEquip(@NotNull PlayerSkillProfile playerProfile, SkillData skillData){
        players.remove(playerProfile.getPlayer());
    }

    @Override
    public void onDispose(){
        bukkitTask.cancel();
    }

    public void onRegister(){
        bukkitTask = Bukkit.getScheduler().runTaskTimer(HeroesSkills.getInstance(),()->{
            Iterator<Player> it =players.iterator();
            while (it.hasNext()){
                Player player = it.next();
                if(player.isOnline())
                    MythicBukkit.inst().getAPIHelper().castSkill(player,mythicSkill);
                else it.remove();
            }
        },0,period);
    }
}
