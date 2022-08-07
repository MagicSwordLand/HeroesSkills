package net.brian.heroesskills.api.players;

import lombok.Getter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.api.skills.SkillManager;
import net.brian.heroesskills.api.skills.casting.ClickSequence;
import net.brian.playerdatasync.PlayerDataSync;
import net.brian.playerdatasync.data.PlayerData;
import net.brian.playerdatasync.data.gson.PostProcessable;
import net.brian.playerdatasync.data.gson.QuitProcessable;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerSkillProfile extends PlayerData implements PostProcessable, QuitProcessable {

    private HashMap<Integer,String> castingButtonSettings = new HashMap<>(
            Map.of(0,"",1,"",2,"",3,"")
    );

    private HashMap<String,SkillData> skillDataMap = new HashMap<>();


    @Getter
    private transient Player player;

    public PlayerSkillProfile(UUID uuid) {
        super(uuid);
    }



    public void addSkill(AbstractSkill skill, int levels){
        skillDataMap.compute(skill.getSkillID(),(k,v)->{
            if(v == null) v = new SkillData();
            int targetLevel = v.level + levels;
            v.level = Math.min(skill.getMaxLevel(),targetLevel);
            return v;
        });
    }

    public static Optional<PlayerSkillProfile> get(UUID uuid){
        return PlayerDataSync.getInstance().getData(uuid,PlayerSkillProfile.class);
    }


    public Optional<SkillData> getSkillData(String skillID){
        return Optional.ofNullable(skillDataMap.get(skillID));
    }

    public Set<Map.Entry<String, SkillData>> getSkills() {
        return skillDataMap.entrySet();
    }

    @Override
    public void gsonPostDeserialize(Player player) {
        this.player = player;
        SkillManager skillManager = HeroesSkills.getInstance().getSkillManager();
        skillDataMap.forEach((id,data)->{
            skillManager.get(id).ifPresent(abstractSkill -> {
                abstractSkill.onActivate(this);
            });
        });
    }

    @Override
    public void onQuit(Player player){
        SkillManager skillManager = HeroesSkills.getInstance().getSkillManager();
        skillDataMap.forEach((id,data)->{
            skillManager.get(id).ifPresent(abstractSkill -> {
                abstractSkill.onDeactivate(this);
            });
        });
    }

    public Optional<ActiveSkill> getButtonSkill(ClickSequence clickSequence){
        return HeroesSkills.getInstance().getSkillManager().getActiveSkill(castingButtonSettings.get(clickSequence.getId()));
    }

    public void setButtonSkill(ClickSequence clickSequence,@Nullable ActiveSkill activeSkill){
        String originSkill = castingButtonSettings.get(clickSequence.getId());
        HeroesSkills.getInstance().getSkillManager().get(originSkill).ifPresent(skill->{
            skill.onDeactivate(this);
        });
        if(activeSkill == null){
            castingButtonSettings.put(clickSequence.getId(),"");
            return;
        }
        if(!skillDataMap.containsKey(activeSkill.getSkillID())) return;
        castingButtonSettings.put(clickSequence.getId(),activeSkill.getSkillID());
        activeSkill.onActivate(this);
    }
}

